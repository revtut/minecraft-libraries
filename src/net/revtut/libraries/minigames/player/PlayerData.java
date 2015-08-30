package net.revtut.libraries.minigames.player;

import net.revtut.libraries.language.Language;
import net.revtut.libraries.minigames.arena.Arena;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * PlayerData Object
 */
public class PlayerData {

    /**
     * Player owner of this data
     */
    private Player bukkitPlayer;

    /**
     * Language of the player
     */
    private Language language;

    /**
     * Current arena of the player
     */
    private Arena currentArena;

    /**
     * State of the player
     */
    private PlayerState state;

    /**
     * Statistics of the player
     */
    private PlayerStatistics statistics;

    /**
     * Constructor of PlayerData
     * @param player player owner of the data
     * @param language language of the player
     * @param statistics statistics of the player
     */
    public PlayerData(Player player, Language language, PlayerStatistics statistics) {
        this.bukkitPlayer = player;
        this.language = language;
        this.state = PlayerState.ALIVE;
        this.statistics = statistics;
    }

    /**
     * Get the Bukkit player of the player data
     * @return Bukkit player of the player data
     */
    public Player getBukkitPlayer() { return bukkitPlayer; }

    /**
     * Get the UUID of the player
     * @return UUID of the player
     */
    public UUID getUuid() {
        return bukkitPlayer.getUniqueId();
    }

    /**
     * Get the name of the player
     * @return name of the player
     */
    public String getName() {
        return bukkitPlayer.getName();
    }

    /**
     * Get the language of the player
     * @return language of the player
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * Get the current arena of the player
     * @return current arena of the player
     */
    public Arena getCurrentArena() {
        return currentArena;
    }

    /**
     * Get the state of the player
     * @return state of the player
     */
    public PlayerState getState() {
        return state;
    }

    /**
     * Get the statistics of the player
     * @return statistics of the player
     */
    public PlayerStatistics getStatistics() {
        return statistics;
    }

    /**
     * Set the current arena of the player
     * @param arena current arena of the player
     */
    public void setCurrentArena(Arena arena) {
        this.currentArena = arena;
    }

    /**
     * Update the state of the player
     * @param state new state of the player
     */
    public void updateState(PlayerState state) {
        this.state = state;
    }
}