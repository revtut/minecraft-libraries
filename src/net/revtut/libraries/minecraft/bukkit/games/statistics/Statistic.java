package net.revtut.libraries.minecraft.bukkit.games.statistics;

import java.util.ArrayList;
import java.util.List;

/**
 * Statistic Object
 */
public abstract class Statistic {

    /**
     * List with created statistics
     */
    private static final List<Statistic> statistics = new ArrayList<>();

    /**
     * Name of the statistic
     */
    private final String name;

    /**
     * Constructor of Statistic
     * @param name name of the statistic
     */
    public Statistic(final String name) {
        this.name = name;

        statistics.add(this);
    }

    /**
     * Get the name of the statistic
     * @return name of the statistic
     */
    public String getName() {
        return name;
    }

    /**
     * Get a statistic by its name
     * @param name name of the statistic
     * @return name of the statistic
     */
    public Statistic getStatistic(final String name) {
        for(final Statistic statistic : statistics)
            if(statistic.getName().equalsIgnoreCase(name))
                return statistic;
        return null;
    }

    /**
     * Convert the statistic to a string
     * @return converted string
     */
    @Override
    public String toString() {
        return getName();
    }
}
