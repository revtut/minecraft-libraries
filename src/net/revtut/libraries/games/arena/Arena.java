package net.revtut.libraries.games.arena;

import net.revtut.libraries.Libraries;
import net.revtut.libraries.games.events.arena.ArenaLoadEvent;
import net.revtut.libraries.games.events.arena.ArenaSwitchStateEvent;
import net.revtut.libraries.games.events.player.PlayerJoinArenaEvent;
import net.revtut.libraries.games.events.player.PlayerLeaveArenaEvent;
import net.revtut.libraries.games.events.player.PlayerSpectateArenaEvent;
import net.revtut.libraries.games.player.PlayerData;
import net.revtut.libraries.games.player.PlayerState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Arena Object
 */
public abstract class Arena {

    /**
     * Current arena ID
     */
    private static int currentID = 0;

    /**
     * ID of the arena
     */
    private int id;

    /**
     * Name of the arena
     */
    private String name;

    /**
     * State of the arena
     */
    private ArenaState state;

    /**
     * Initial value of the timer
     */
    private int initialTimer;

    /**
     * Timer of the arena (used for countdown, game time, etc)
     */
    private int currentTimer;

    /**
     * File where worlds are located
     */
    private File worldsFolder;

    /**
     * Minimum players on the arena
     */
    private int minPlayers;

    /**
     * Maximum players on the arena
     */
    private int maxPlayers;

    /**
     * World of the arena
     */
    private World arenaWorld;

    /**
     * Lobby location of the arena
     */
    private Location lobbyLocation;

    /**
     * Spectator location of the arena
     */
    private Location spectatorLocation;

    /**
     * Constructor of the Arena
     * @param plugin plugin owner of the arena
     */
    public Arena(JavaPlugin plugin) {
        // Call event
        ArenaLoadEvent event = new ArenaLoadEvent(this);
        Libraries.getInstance().getServer().getPluginManager().callEvent(event);

        if(event.isCancelled())
            throw new IllegalStateException("Arena creation was cancelled!");

        this.id = currentID++;
        this.name = plugin.getName() + this.id;
        this.state = ArenaState.NONE;
    }

    /**
     * Initialize the arena
     * @param worldsFolder folder where worlds are located
     * @param minPlayers minimum players to start the game
     * @param maxPlayers maximum players on the arena
     * @param arenaWorld world of the arena
     * @param lobbyLocation location of the lobby
     * @param spectatorLocation location of the spectator's spawn
     */
    public void init(File worldsFolder, int minPlayers, int maxPlayers, World arenaWorld, Location lobbyLocation, Location spectatorLocation) {
        this.worldsFolder = worldsFolder;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.arenaWorld = arenaWorld;
        this.lobbyLocation = lobbyLocation;
        this.spectatorLocation = spectatorLocation;
    }

    /**
     * Get the ID of the arena
     * @return ID of the arena
     */
    public int getId() {
        return id;
    }

    /**
     * Get the name of the arena
     * @return name of the arena
     */
    public String getName() {
        return name;
    }

    /**
     * Get the state of the arena
     * @return state of the arena
     */
    public ArenaState getState() {
        return state;
    }

    /**
     * Get the timer of the arena
     * @return timer of the arena
     */
    public int getCurrentTimer() {
        return currentTimer;
    }

    /**
     * Get the worlds folder of the arena
     * @return worlds folder of the arena
     */
    public File getWorldsFolder() {
        return worldsFolder;
    }

    /**
     * Get the minimum players
     * @return minimum players
     */
    public int getMinPlayers() {
        return minPlayers;
    }

    /**
     * Get the maximum players
     * @return maximum players
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Get the world of the arena
     * @return world of the arena
     */
    public World getWorld() {
        return arenaWorld;
    }

    /**
     * Get the lobby location of the arena
     * @return lobby location of the arena
     */
    public Location getLobbyLocation() {
        return lobbyLocation;
    }

    /**
     * Get the spectator location of the arena
     * @return spectator location of the arena
     */
    public Location getSpectatorLocation() {
        return spectatorLocation;
    }

    /**
     * Get players that are currently on a state
     * @param state state to filter players
     * @return players that correspond to that state
     */
    public List<PlayerData> getPlayers(PlayerState state) {
        return getAllPlayers().stream().filter(player -> player.getState() == state).collect(Collectors.toList());
    }

    /**
     * Update the arena state
     * @param state new value for the state
     * @param duration duration of the new state
     */
    public void updateState(ArenaState state, int duration) {
        // Call event
        ArenaSwitchStateEvent event = new ArenaSwitchStateEvent(this, this.state, state, duration);
        Libraries.getInstance().getServer().getPluginManager().callEvent(event);

        if(event.isCancelled()) {
            resetTimer();
            return;
        }

        // Update state
        this.state = state;
        this.currentTimer = duration;
        this.initialTimer = duration;
    }

    /**
     * Update the arena timer
     */
    public void resetTimer() {
        this.currentTimer = this.initialTimer;
    }

    /**
     * Tick the arena
     */
    public void tick() {
        currentTimer--;

        // Update arena state
        if(currentTimer == 0) {
            ArenaState nextState;
            int duration;
            switch (state) {
                case BUILD:
                    nextState = ArenaState.LOBBY;
                    duration = 500;
                    break;
                case LOBBY:
                    nextState = ArenaState.WARMUP;
                    duration = 30;
                    break;
                case WARMUP:
                    nextState = ArenaState.START;
                    duration = 900;
                    break;
                case START:
                    nextState = ArenaState.DEATHMATCH;
                    duration = 20;
                    break;
                case DEATHMATCH:
                    nextState = ArenaState.FINISH;
                    duration = 500;
                    break;
                case FINISH:
                    nextState = ArenaState.BUILD;
                    duration = Integer.MAX_VALUE;
                    break;
                case STOP:
                    resetTimer(); // On stop reset timer only
                    return;
                default:
                    return;
            }

            updateState(nextState, duration);
        }

        // Tick states
        switch (state) {
            case BUILD:
                build();
                break;
            case LOBBY:
                lobby();
                break;
            case WARMUP:
                warmUp();
                break;
            case START:
                start();
                break;
            case DEATHMATCH:
                deathMatch();
                break;
            case FINISH:
                finish();
                break;
            case STOP:
                stop();
                break;
            default:
                return;
        }
    }

    /**
     * Get the number of players on the arena
     * @return number of players on the arena
     */
    public int numberPlayers() {
        return getAllPlayers().size();
    }

    /**
     * Make a player join the arena
     * @param player player to join
     */
    public void join(PlayerData player) {
        if(!canJoin(player))
            return;

        // Call event
        PlayerJoinArenaEvent event = new PlayerJoinArenaEvent(player, this, player.getName() + " has joined the arena " + name);
        Libraries.getInstance().getServer().getPluginManager().callEvent(event);

        if(event.isCancelled())
            return;

        broadcastMessage(event.getJoinMessage());

        // Update player
        player.updateState(PlayerState.ALIVE);
        player.setCurrentArena(this);
        player.getBukkitPlayer().teleport(lobbyLocation);

        // Visibility configuration
        for(PlayerData target : getAllPlayers()) {
            target.getBukkitPlayer().showPlayer(player.getBukkitPlayer());

            if(target.getState() == PlayerState.SPECTATOR)
                player.getBukkitPlayer().hidePlayer(target.getBukkitPlayer());
            else if(target.getState() == PlayerState.ALIVE)
                player.getBukkitPlayer().showPlayer(target.getBukkitPlayer());
        }
    }

    /**
     * Make a player leave the arena
     * @param player player to leave
     */
    public void leave(PlayerData player) {
        // Call event
        PlayerLeaveArenaEvent event = new PlayerLeaveArenaEvent(player, this, player.getName() + " has left the arena " + name);
        Libraries.getInstance().getServer().getPluginManager().callEvent(event);

        if(event.isCancelled())
            return;

        broadcastMessage(event.getLeaveMessage());

        // Update player
        player.updateState(PlayerState.NOT_ASSIGNED);
        player.setCurrentArena(null);
        player.getBukkitPlayer().teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
    }

    /**
     * Make a player spectate the arena
     * @param player player to spectate
     */
    public void spectate(PlayerData player) {
        // Call event
        PlayerSpectateArenaEvent event = new PlayerSpectateArenaEvent(player, this, player.getName() + " is spectating the arena " + name);
        Libraries.getInstance().getServer().getPluginManager().callEvent(event);

        if(event.isCancelled())
            return;

        broadcastMessage(event.getJoinMessage());

        // Update player
        player.updateState(PlayerState.SPECTATOR);
        player.setCurrentArena(this);
        player.getBukkitPlayer().teleport(spectatorLocation);

        // Hide to players ingame except spectators
        for(PlayerData target : getAllPlayers())
            if(target.getState() != PlayerState.SPECTATOR)
                player.getBukkitPlayer().hidePlayer(target.getBukkitPlayer());
    }

    /**
     * Check if a player can join a arena
     * @param player player to be joined
     * @return true if can, false otherwise
     */
    public boolean canJoin(PlayerData player) {
        // Maximum players already achieved
        if(numberPlayers() >= getMaxPlayers())
            return false;

        // Arena is already ingame
        if(getState() != ArenaState.LOBBY)
            return false;

        return true;
    }

    /**
     * Broadcast a message to the arena
     * @param message message to be broadcast
     */
    public void broadcastMessage(String message) {
        for(PlayerData player : getAllPlayers())
            player.getBukkitPlayer().sendMessage(message);
    }

    /**
     * Get all the players on the arena
     * @return players on the arena
     */
    public abstract List<PlayerData> getAllPlayers();

    /**
     * Building the arena
     */
    public abstract void build();

    /**
     * Waiting for players to join
     */
    public abstract void lobby();

    /**
     * Warming up the game
     */
    public abstract void warmUp();

    /**
     * Game is running
     */
    public abstract void start();

    /**
     * Death match is running
     */
    public abstract void deathMatch();

    /**
     * Game has finished
     */
    public abstract void finish();

    /**
     * Stop the arena
     */
    public abstract void stop();

    /**
     * Update the arena game in the database
     */
    public abstract void updateDatabase();
}