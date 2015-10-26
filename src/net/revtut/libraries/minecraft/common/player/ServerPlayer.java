package net.revtut.libraries.minecraft.common.player;

import net.revtut.libraries.minecraft.bukkit.games.achievements.Achievement;
import net.revtut.libraries.minecraft.bukkit.games.statistics.Statistic;
import net.revtut.libraries.minecraft.common.text.Language;

import java.util.*;

/**
 * Server Player.
 * Object with functions related to a server player
 */
public class ServerPlayer {

    /**
     * Player owner of this data
     */
    private final UUID uuid;

    /**
     * Name of the player
     */
    private final String name;

    /**
     * Locale of the player
     */
    private final Language.Locale locale;

    /**
     * Date of the last login
     */
    private final Date lastLogin;

    /**
     * Statistics of the player
     */
    private final Map<Statistic, Long> statistics;

    /**
     * Achievements of the the player
     */
    private final List<Achievement> achievements;

    /**
     * Constructor of ServerPlayer
     * @param uuid uuid of the player
     * @param name name of the player
     * @param locale locale of the player
     */
    public ServerPlayer(final UUID uuid, final String name, final Language.Locale locale) {
        this.uuid = uuid;
        this.name = name;
        this.locale = locale;
        this.lastLogin = new Date();
        this.statistics = new HashMap<>();
        this.achievements = new ArrayList<>();
    }

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
        return name;
    }

    /**
     * Get the language of the player
     * @return language of the player
     */
    public Language.Locale getLanguage() {
        return locale;
    }

    /**
     * Get the last login date
     * @return last login date
     */
    public Date getLastLogin() {
        return lastLogin;
    }

    /**
     * Get a statistic from player
     * @param statistic statistic to get
     * @return value of the statistic
     */
    public long getStatistic(final Statistic statistic) {
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
     * Add a statistic to the player
     * @param statistic statistic to add
     * @param value value of the statistic
     */
    public void addStatistic(final Statistic statistic, final long value) {
        statistics.put(statistic, value);
    }

    /**
     * Add a achievements to the player
     * @param achievement achievements to be added
     */
    public void addAchievement(final Achievement achievement) {
        achievements.add(achievement);
    }

    /**
     * Increment a statistic of the player
     * @param statistic statistic to be incremented
     */
    public void incrementStatistic(final Statistic statistic) {
        incrementStatistic(statistic, 1);
    }

    /**
     * Decrement a statistic of the player
     * @param statistic statistic to be decremented
     * @param value value to be decremented
     */
    public void incrementStatistic(final Statistic statistic, final long value) {
        addStatistic(statistic, getStatistic(statistic) + value);
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
