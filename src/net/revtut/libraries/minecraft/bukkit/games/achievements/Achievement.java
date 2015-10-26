package net.revtut.libraries.minecraft.bukkit.games.achievements;

import net.revtut.libraries.Libraries;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

/**
 * Achievement Object
 */
public abstract class Achievement implements Listener {

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
    }

    /**
     * Get the name of the achievement
     * @return name of the achievement
     */
    public String getName() {
        return name;
    }

    /**
     * Register events of the class
     */
    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(this, Libraries.getInstance());
    }

    /**
     * Unregister all the events of the class
     */
    public void unregisterEvents() {
        HandlerList.unregisterAll(this);
    }
}