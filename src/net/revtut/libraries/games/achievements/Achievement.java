package net.revtut.libraries.games.achievements;

import net.revtut.libraries.games.player.PlayerData;

/**
 * Achievement Object
 */
public abstract class Achievement {

    /**
     * Name of the achievement
     */
    private String name;

    /**
     * Constructor of Achievement
     * @param name name of the achievement
     */
    public Achievement(String name) {
        this.name = name;
    }

    /**
     * Get the name of the achievement
     * @return name of the achievement
     */
    public String getName() {
        return name;
    }

    /**
     * Check if a player has achieved this
     * @param player player to check if achieved
     * @param object objects that may be needed to check if achieved
     * @return true if achieved, false otherwise
     */
    public abstract boolean hasAchieved(PlayerData player, Object... object);
}