package net.revtut.libraries.minigames.arena;

import net.revtut.libraries.minigames.player.PlayerData;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

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
     * Configuration of the arena
     */
    private Configuration configuration;

    /**
     * State of the arena
     */
    private ArenaState state;

    /**
     * Timer of the arena (used for countdown, game time, etc)
     */
    private int timer;

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
        this(plugin.getName() + currentID);
    }

    /**
     * Constructor of the Arena
     * @param name name of the arena
     */
    public Arena(String name) {
        this.name = name;
        this.id = currentID++;
        this.configuration = new Configuration();
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
     * Get the configuration of the arena
     * @return configuration of the arena
     */
    public Configuration getConfiguration() {
        return configuration;
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
    public int getTimer() {
        return timer;
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
     * Update the arena state
     * @param state new value for the state
     */
    public void updateState(ArenaState state) {
        this.state = state;
    }

    /**
     * Update the arena timer
     * @param timer new value for the timer
     */
    public void updateTimer(int timer) {
        this.timer = timer;
    }

    /**
     * Tick the arena
     */
    public void tick() {
        timer--;

        if(timer > 0) {
            switch (state) {
                case BUILD:
                    tickBuild();
                    break;
                case JOIN:
                    tickJoin();
                    break;
                case WARMUP:
                    tickWarmUp();
                    break;
                case START:
                    tickStart();
                    break;
                case FINISH:
                    tickFinish();
                    break;
                case STOP:
                    tickStop();
                    break;
            }
        } else {
            switch (state) {
                case BUILD:
                    build();
                    break;
                case JOIN:
                    join();
                    break;
                case WARMUP:
                    warmUp();
                    break;
                case START:
                    start();
                    break;
                case FINISH:
                    finish();
                    break;
                case STOP:
                    stop();
                    break;
            }
        }

        // XP timer
        if(getConfiguration().enabledXPTimer())
            getAllPlayers().stream().filter(player -> player.getBukkitPlayer() != null).forEach(player -> player.getBukkitPlayer().setLevel(getTimer()));
    }

    /**
     * Get all the players on the arena
     * @return players on the arena
     */
    public abstract List<PlayerData> getAllPlayers();

    /**
     * Get the number of players on the arena
     * @return number of players on the arena
     */
    public abstract int numberPlayers();

    /**
     * Make a player join the arena
     * @param player player to join
     */
    public abstract void join(PlayerData player);

    /**
     * Make a player leave the arena
     * @param player player to leave
     */
    public abstract void leave(PlayerData player);

    /**
     * Make a player spectate the arena
     * @param player player to spectate
     */
    public abstract void spectate(PlayerData player);

    /**
     * Check if a player can join a arena
     * @param player player to be joined
     * @return true if can, false otherwise
     */
    public abstract boolean canJoin(PlayerData player);

    /**
     * Start building the arena
     */
    public abstract void build();

    /**
     * Tick the arena when it is building
     */
    public abstract void tickBuild();

    /**
     * Start waiting for players to join
     */
    public abstract void join();

    /**
     * Tick the arena when it is waiting for players
     */
    public abstract void tickJoin();

    /**
     * Start the warm up
     */
    public abstract void warmUp();

    /**
     * Tick the arena when it is on warm up
     */
    public abstract void tickWarmUp();

    /**
     * Start the game
     */
    public abstract void start();

    /**
     * Tick the arena when game has started
     */
    public abstract void tickStart();

    /**
     * Finish the game
     */
    public abstract void finish();

    /**
     * Tick the arena when game has finished
     */
    public abstract void tickFinish();

    /**
     * Stop the arena
     */
    public abstract void stop();

    /**
     * Tick the arena when stopped
     */
    public abstract void tickStop();

    /**
     * Update the arena game in the database
     */
    public abstract void updateDatabase();
}