package net.revtut.libraries.games.player;

import net.revtut.libraries.games.GameAPI;
import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.utils.Winner;
import net.revtut.libraries.text.Language;
import net.revtut.libraries.text.LanguageAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * PlayerData Object
 */
public class PlayerData implements Winner {

    /**
     * Player owner of this data
     */
    private UUID uuid;

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
     * @param uuid uuid of the owner player
     * @param statistics statistics of the player
     */
    public PlayerData(UUID uuid, PlayerStatistics statistics) {
        this.uuid = uuid;
        this.state = PlayerState.ALIVE;
        this.statistics = statistics;

        GameAPI.getInstance().addPlayer(this);
    }

    /**
     * Get the Bukkit player of the player data
     * @return Bukkit player of the player data
     */
    public Player getBukkitPlayer() { return Bukkit.getPlayer(uuid); }

    /**
     * Get the UUID of the player
     * @return UUID of the player
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Get the name of the player
     * @return name of the player
     */
    public String getName() {
        return Bukkit.getPlayer(uuid).getName();
    }

    /**
     * Get the language of the player
     * @return language of the player
     */
    public Language getLanguage() {
        return LanguageAPI.getByCode(getBukkitPlayer().spigot().getLocale());
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

    /**
     * Convert a player data to string
     * @return converted string
     */
    @Override
    public String toString() {
        return uuid.toString();
    }
}