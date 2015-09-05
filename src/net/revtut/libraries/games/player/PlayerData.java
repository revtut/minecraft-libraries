package net.revtut.libraries.games.player;

import net.revtut.libraries.games.GameAPI;
import net.revtut.libraries.games.achievement.Achievement;
import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.statistics.Statistic;
import net.revtut.libraries.games.utils.Winner;
import net.revtut.libraries.text.Language;
import net.revtut.libraries.text.LanguageAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * PlayerData Object
 */
public class PlayerData implements Winner {

    /**
     * Player owner of this data
     */
    private UUID uuid;

    /**
     * Date of the last login
     */
    private Date lastLogin;

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
    private Map<Statistic, Long> statistics;

    /**
     * Achievements of the the player
     */
    private List<Achievement> achievements;

    /**
     * Constructor of PlayerData
     * @param uuid uuid of the owner player
     */
    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.lastLogin = new Date();
        this.currentArena = null;
        this.state = PlayerState.NOT_ASSIGNED;
        this.statistics = new HashMap<>();
        this.achievements = new ArrayList<>();

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
     * Get the last login date
     * @return last login date
     */
    public Date getLastLogin() {
        return lastLogin;
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
     * Get a statistic from player
     * @param statistic statistic to get
     * @return value of the statistic
     */
    public long getStatistic(Statistic statistic) {
        if(!statistics.containsKey(statistic))
            return 0;
        return statistics.get(statistic);
    }

    /**
     * Get all the achievements of the player
     * @return achievements of the player
     */
    public List<Achievement> getAchievements() {
        return achievements;
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
     * Add a statistic to the player
     * @param statistic statistic to add
     * @param value value of the statistic
     */
    public void addStatistic(Statistic statistic, long value) {
        statistics.put(statistic, value);
    }

    /**
     * Add a achievement to the player
     * @param achievement achievement to be added
     */
    public void addAchievement(Achievement achievement) {
        achievements.add(achievement);
    }

    /**
     * Increment a statistic of the player
     * @param statistic statistic to be incremented
     */
    public void incrementStatistic(Statistic statistic) {
        incrementStatistic(statistic, 1);
    }

    /**
     * Decrement a statistic of the player
     * @param statistic statistic to be decremented
     * @param value value to be decremented
     */
    public void incrementStatistic(Statistic statistic, long value) {
        statistics.put(statistic, getStatistic(statistic) + value);
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