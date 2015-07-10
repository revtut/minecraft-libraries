package net.revtut.libraries.items;

import net.minecraft.server.v1_8_R1.NBTTagCompound;
import net.minecraft.server.v1_8_R1.NBTTagList;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Item API.
 *
 * <P>Help make items with simple methods</P>
 *
 * @author Jo�o Silva
 * @version 1.0
 */
public class ItemAPI {

    /**
     * Create a ItemStack
     * @param material material of the item stack
     * @param name name of the item stack
     * @param lines lines of the item stack
     * @return
     */
    public static ItemStack createItemStack(Material material, String name, String[] lines) {
        org.bukkit.inventory.ItemStack itemStack = new org.bukkit.inventory.ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        List<String> lore = new ArrayList<>();
        for (String line : lines) {
            lore.add(line);
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * Create a ItemStack
     * @param material material of the item stack
     * @param value value of the item stack
     * @param name name of the item stack
     * @param lines lines of the item stack
     * @return
     */
    public static ItemStack createItemStack(Material material, short value, String name, String[] lines) {
        org.bukkit.inventory.ItemStack itemStack = new org.bukkit.inventory.ItemStack(material, 1, value);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        List<String> lore = new ArrayList<>();
        for (String line : lines) {
            lore.add(line);
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * Create a ItemStack
     * @param materialID material ID of the item stack
     * @param value value of the item stack
     * @param name name of the item stack
     * @param lines lines of the item stack
     * @return
     */
    public static ItemStack createItemStack(int materialID, short value, String name, String[] lines) {
        org.bukkit.inventory.ItemStack itemStack = new org.bukkit.inventory.ItemStack(materialID, 1, value);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        List<String> lore = new ArrayList<>();
        for (String line : lines) {
            lore.add(line);
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * Apply glow effect to a item
     * @param item item to be applied the glow effect
     * @return item wth glow effect
     */
    public static ItemStack applyGlowEffect(ItemStack item) {
        net.minecraft.server.v1_8_R1.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = null;
        if (!itemStack.hasTag()) {
            tag = new NBTTagCompound();
            itemStack.setTag(tag);
        }
        if (tag == null)
            tag = itemStack.getTag();
        NBTTagList ench = new NBTTagList();
        tag.set("ench", ench);
        itemStack.setTag(tag);
        return CraftItemStack.asCraftMirror(itemStack);
    }

}