package net.revtut.libraries.minecraft.games.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Color Enum
 */
public enum Color {

    /**
     * Black color
     */
    BLACK("Black", org.bukkit.ChatColor.BLACK, new ItemStack(Material.WOOL, 1, (short) 15)),

    /**
     * Dark blue color
     */
    DARK_BLUE("Dark Blue", ChatColor.DARK_BLUE, new ItemStack(Material.WOOL, 1, (short) 11)),

    /**
     * Dark green color
     */
    DARK_GREEN("Dark Green", ChatColor.DARK_GREEN, new ItemStack(Material.WOOL, 1, (short) 13)),

    /**
     * Dark aqua color
     */
    DARK_AQUA("Dark Aqua", ChatColor.DARK_AQUA, new ItemStack(Material.WOOL, 1, (short) 9)),

    /**
     * Dark red color
     */
    DARK_RED("Dark Red", ChatColor.DARK_RED, new ItemStack(Material.WOOL, 1, (short) 14)),

    /**
     * Dark purple color
     */
    DARK_PURPLE("Dark Purple", ChatColor.DARK_PURPLE, new ItemStack(Material.WOOL, 1, (short) 10)),

    /**
     * Gold color
     */
    GOLD("Gold", ChatColor.GOLD, new ItemStack(Material.WOOL, 1, (short) 1)),

    /**
     * Gray color
     */
    GRAY("Gray", ChatColor.GRAY, new ItemStack(Material.WOOL, 1, (short) 8)),

    /**
     * Dark gray color
     */
    DARK_GRAY("Dark Gray", ChatColor.DARK_GRAY, new ItemStack(Material.WOOL, 1, (short) 7)),

    /**
     * Blue color
     */
    BLUE("Blue", ChatColor.BLUE, new ItemStack(Material.WOOL, 1, (short) 11)),

    /**
     * Green color
     */
    GREEN("Green", ChatColor.GREEN, new ItemStack(Material.WOOL, 1, (short) 5)),

    /**
     * Aqua color
     */
    AQUA("Aqua", ChatColor.AQUA, new ItemStack(Material.WOOL, 1, (short) 3)),

    /**
     * Red color
     */
    RED("Red", ChatColor.RED, new ItemStack(Material.WOOL, 1, (short) 6)),

    /**
     * Light purple color
     */
    LIGHT_PURPLE("Light Purple", ChatColor.LIGHT_PURPLE, new ItemStack(Material.WOOL, 1, (short) 2)),

    /**
     * Yellow color
     */
    YELLOW("Yellow", ChatColor.YELLOW, new ItemStack(Material.WOOL, 1, (short) 4)),

    /**
     * White color
     */
    WHITE("White", ChatColor.WHITE, new ItemStack(Material.WOOL, 1, (short) 0));

    /**
     * Name of the color
     */
    private final String name;

    /**
     * Bukkit color
     */
    private final org.bukkit.ChatColor bukkitColor;

    /**
     * Wool with that color
     */
    private final ItemStack item;

    /**
     * Constructor of Color
     * @param name name of the color
     * @param bukkitColor bukkit color
     * @param item wool item with that color
     */
    Color(final String name, final org.bukkit.ChatColor bukkitColor, final ItemStack item) {
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
    public org.bukkit.ChatColor getBukkitColor() {
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
