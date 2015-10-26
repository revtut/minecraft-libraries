package net.revtut.libraries.minecraft.common.player;

import java.util.ArrayList;
import java.util.List;

/**
 * Achievement Object
 */
public abstract class Achievement {

    /**
     * List with created achievements
     */
    private static final List<Achievement> achievements = new ArrayList<>();

    /**
     * Name of the achievement
     */
    private final String name;

    /**
     * Constructor of Achievement
     * @param name name of the achievement
     */
    public Achievement(final String name) {
        this.name = name;

        achievements.add(this);
    }

    /**
     * Get the name of the achievement
     * @return name of the achievement
     */
    public String getName() {
        return name;
    }

    /**
     * Get a achievement by its name
     * @param name name of the achievement
     * @return name of the achievement
     */
    public Achievement getAchievement(final String name) {
        for(final Achievement achievement : achievements)
            if(achievement.getName().equalsIgnoreCase(name))
                return achievement;
        return null;
    }

    /**
     * Convert the achievement to a string
     * @return converted string
     */
    @Override
    public String toString() {
        return getName();
    }
}