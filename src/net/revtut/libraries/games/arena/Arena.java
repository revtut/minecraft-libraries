package net.revtut.libraries.games.arena;

import net.revtut.libraries.Libraries;
import net.revtut.libraries.games.arena.session.GameSession;
import net.revtut.libraries.games.arena.session.GameState;
import net.revtut.libraries.games.events.arena.ArenaLoadEvent;
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
     * File where worlds are located
     */
    private File worldsFolder;

    /**
     * World of the arena
     */
    private World arenaWorld;

    /**
     * Lobby and spectator location of the arena
     */
    private Location lobbyLocation, spectatorLocation;

    /**
     * Corners of the arena
     */
    private Location[] corners;

    /**
     * Current session of the arena
     */
    private GameSession currentSession;

    /**
     * Constructor of the Arena
     * @param plugin plugin owner of the arena
     * @param worldsFolder folder where worlds are located
     */
    public Arena(JavaPlugin plugin, File worldsFolder) {
        // Call event
        ArenaLoadEvent event = new ArenaLoadEvent(this);
        Libraries.getInstance().getServer().getPluginManager().callEvent(event);

        if(event.isCancelled())
            throw new IllegalStateException("Arena creation was cancelled!");

        this.id = currentID++;
        this.name = plugin.getName() + this.id;
        this.worldsFolder = worldsFolder;
    }

    /**
     * Initialize the arena
     * @param arenaWorld world of the arena
     * @param lobbyLocation location of the lobby
     * @param spectatorLocation location of the spectator's spawn
     * @param corners corners of the arena
     * @param gameSession session of the arena
     */
    public void init(World arenaWorld, Location lobbyLocation, Location spectatorLocation, Location[] corners, GameSession gameSession) {
        this.arenaWorld = arenaWorld;
        this.lobbyLocation = lobbyLocation;
        this.spectatorLocation = spectatorLocation;
        this.corners = corners;
        this.currentSession = gameSession;
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
     * Get the worlds folder of the arena
     * @return worlds folder of the arena
     */
    public File getWorldsFolder() {
        return worldsFolder;
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
     * Get the number of players on the arena
     * @return number of players on the arena
     */
    public int getSize() {
        return getAllPlayers().size();
    }

    /**
     * Get the current session of the arena
     * @return current session of the arena
     */
    public GameSession getSession() {
        return currentSession;
    }

    /**
     * Update the current session of the game
     * @param session session of the game
     */
    public void setSession(GameSession session) {
        this.currentSession = session;
    }

    /**
     * Make a player join the arena
     * @param player player to join
     * @return true if has joined, false otherwise
     */
    public boolean join(PlayerData player) {
        if(!canJoin(player))
            return false;

        // Call event
        PlayerJoinArenaEvent event = new PlayerJoinArenaEvent(player, this, player.getName() + " has joined the arena " + name);
        Libraries.getInstance().getServer().getPluginManager().callEvent(event);

        if(event.isCancelled())
            return false;

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

        return true;
    }

    /**
     * Make a player leave the arena
     * @param player player to leave
     * @return true if has left, false otherwise
     */
    public boolean leave(PlayerData player) {
        // Call event
        PlayerLeaveArenaEvent event = new PlayerLeaveArenaEvent(player, this, player.getName() + " has left the arena " + name);
        Libraries.getInstance().getServer().getPluginManager().callEvent(event);

        if(event.isCancelled())
            return false;

        broadcastMessage(event.getLeaveMessage());

        // Update player
        player.updateState(PlayerState.NOT_ASSIGNED);
        player.setCurrentArena(null);
        player.getBukkitPlayer().teleport(Bukkit.getWorlds().get(0).getSpawnLocation());

        return true;
    }

    /**
     * Make a player spectate the arena
     * @param player player to spectate
     * @return true if is spectating, false otherwise
     */
    public boolean spectate(PlayerData player) {
        // Call event
        PlayerSpectateArenaEvent event = new PlayerSpectateArenaEvent(player, this, player.getName() + " is spectating the arena " + name);
        Libraries.getInstance().getServer().getPluginManager().callEvent(event);

        if(event.isCancelled())
            return false;

        broadcastMessage(event.getJoinMessage());

        // Update player
        player.updateState(PlayerState.SPECTATOR);
        player.setCurrentArena(this);
        player.getBukkitPlayer().teleport(spectatorLocation);

        // Hide to players ingame except spectators
        for(PlayerData target : getAllPlayers())
            if(target.getState() != PlayerState.SPECTATOR)
                player.getBukkitPlayer().hidePlayer(target.getBukkitPlayer());

        return true;
    }

    /**
     * Check if a player can join a arena
     * @param player player to be joined
     * @return true if can, false otherwise
     */
    public boolean canJoin(PlayerData player) {
        // Avoid joining when no session is created
        if(currentSession == null)
            return false;

        // Maximum players already achieved
        if(getSize() >= currentSession.getMaxPlayers())
            return false;

        // Arena is already ingame
        if(currentSession.getState() != GameState.LOBBY)
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
}