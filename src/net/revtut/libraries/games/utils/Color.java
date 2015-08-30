package net.revtut.libraries.games.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by User on 30/08/2015.
 */
public enum Color {

    /**
     * White color
     */
    WHITE("White", org.bukkit.Color.WHITE, new ItemStack(Material.WOOL, 1, (short) 0)),

    /**
     * Silver color
     */
    SILVER("Silver", org.bukkit.Color.SILVER, new ItemStack(Material.WOOL, 1, (short) 8)),

    /**
     * Gray color
     */
    GRAY("Gray", org.bukkit.Color.GRAY, new ItemStack(Material.WOOL, 1, (short) 7)),

    /**
     * Black color
     */
    BLACK("Black", org.bukkit.Color.BLACK, new ItemStack(Material.WOOL, 1, (short) 15)),

    /**
     * Red color
     */
    RED("Red", org.bukkit.Color.RED, new ItemStack(Material.WOOL, 1, (short) 14)),

    /**
     * Maroon color
     */
    MAROON("Maroon", org.bukkit.Color.MAROON, new ItemStack(Material.WOOL, 1, (short) 12)),

    /**
     * Yellow color
     */
    YELLOW("Yellow", org.bukkit.Color.YELLOW, new ItemStack(Material.WOOL, 1, (short) 4)),

    /**
     * Olive color
     */
    OLIVE("Olive", org.bukkit.Color.OLIVE, new ItemStack(Material.WOOL, 1, (short) 13)),

    /**
     * Lime color
     */
    LIME("Lime", org.bukkit.Color.LIME, new ItemStack(Material.WOOL, 1, (short) 5)),

    /**
     * Green color
     */
    GREEN("Green", org.bukkit.Color.GREEN, new ItemStack(Material.WOOL, 1, (short) 13)),

    /**
     * Aqua color
     */
    AQUA("Aqua", org.bukkit.Color.AQUA, new ItemStack(Material.WOOL, 1, (short) 3)),

    /**
     * Teal color
     */
    TEAL("Teal", org.bukkit.Color.TEAL, new ItemStack(Material.WOOL, 1, (short) 9)),

    /**
     * Blue color
     */
    BLUE("Blue", org.bukkit.Color.BLUE, new ItemStack(Material.WOOL, 1, (short) 11)),

    /**
     * Navy color
     */
    NAVY("Navy", org.bukkit.Color.NAVY, new ItemStack(Material.WOOL, 1, (short) 11)),

    /**
     * Fuchsia color
     */
    FUCHSIA("Fuchsia", org.bukkit.Color.FUCHSIA, new ItemStack(Material.WOOL, 1, (short) 2)),

    /**
     * Purple color
     */
    PURPLE("Purple", org.bukkit.Color.PURPLE, new ItemStack(Material.WOOL, 1, (short) 10)),

    /**
     * Orange color
     */
    ORANGE("Orange", org.bukkit.Color.ORANGE, new ItemStack(Material.WOOL, 1, (short) 1));

    /**
     * Name of the color
     */
    private String name;

    /**
     * Bukkit color
     */
    private org.bukkit.Color bukkitColor;

    /**
     * Wool with that color
     */
    private ItemStack item;

    /**
     * Constructor of Color
     * @param name name of the color
     * @param bukkitColor bukkit color
     * @param item wool item with that color
     */
    Color(String name, org.bukkit.Color bukkitColor, ItemStack item) {
        this.name = name;
        this.bukkitColor = bukkitColor;
        this.item = item;
    }

    /**
     * Get the name of the color
     * @return name of the color
     */
    public String getName() {
        return name;
    }

    /**
     * Get the bukkit color
     * @return bukkit color
     */
    public org.bukkit.Color getBukkitColor() {
        return bukkitColor;
    }

    /**
     * Get the wool item that represents the color
     * @return wool item that represents the color
     */
    public ItemStack getItem() {
        return item;
    }
}
