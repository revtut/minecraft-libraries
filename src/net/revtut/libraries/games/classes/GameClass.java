package net.revtut.libraries.games.classes;

import net.revtut.libraries.Libraries;
import net.revtut.libraries.games.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

/**
 * Game class
 */
public abstract class GameClass implements Listener {

    /**
     * Name of the class
     */
    private final String name;


    /**
     * Price of the class
     */
    private final int price;

    /**
     * Constructor of GameClass
     * @param name name of the class
     * @param price price of the class
     */
    public GameClass(final String name, final int price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Get the name of the class
     * @return name of the class
     */
    public String getName() {
        return name;
    }

    /**
     * Get the price of the class
     * @return price of the class
     */
    public int getPrice() {
        return price;
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

    /**
     * Equip a player with this class
     * @param player player to be equipped
     */
    public abstract void equip(PlayerData player);
}
