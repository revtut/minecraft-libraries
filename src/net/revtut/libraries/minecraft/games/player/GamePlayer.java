package net.revtut.libraries.minecraft.games.player;

import net.revtut.libraries.minecraft.games.GameAPI;
import net.revtut.libraries.minecraft.games.achievements.Achievement;
import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.classes.GameClass;
import net.revtut.libraries.minecraft.games.statistics.Statistic;
import net.revtut.libraries.minecraft.games.utils.Winner;
import net.revtut.libraries.minecraft.text.Language;
import net.revtut.libraries.minecraft.text.LanguageAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * GamePlayer Object
 */
public class GamePlayer implements Winner {

    /**
     * Player owner of this data
     */
    private final UUID uuid;

    /**
     * Date of the last login
     */
    private final Date lastLogin;

    /**
     * Current arena of the player
     */
    private Arena currentArena;

    /**
     * State of the player
     */
    private PlayerState state;

    /**
     * Game class of the player
     */
    private GameClass gameClass;

    /**
     * Statistics of the player
     */
    private final Map<Statistic, Long> statistics;

    /**
     * Achievements of the the player
     */
    private final List<Achievement> achievements;

    /**
     * Constructor of GamePlayer
     * @param uuid uuid of the owner player
     */
    public GamePlayer(final UUID uuid) {
        this.uuid = uuid;
        this.lastLogin = new Date();
        this.currentArena = null;
        this.state = PlayerState.NOT_ASSIGNED;
        this.gameClass = null;
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
     * Get the game class of the player
     * @return game class of the player
     */
    public GameClass getGameClass() {
        return gameClass;
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
     * Set the current arena of the player
     * @param arena current arena of the player
     */
    public void setCurrentArena(final Arena arena) {
        this.currentArena = arena;
    }

    /**
     * Set the game class of the player
     * @param gameClass game class of the player
     */
    public void setGameClass(final GameClass gameClass) {
        this.gameClass = gameClass;
    }

    /**
     * Update the state of the player
     * @param state new state of the player
     */
    public void updateState(final PlayerState state) {
        this.state = state;
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