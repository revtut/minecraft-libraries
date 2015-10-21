package net.revtut.libraries.minecraft.appearance;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Item API.
 *
 * <P>Help make items with simple methods</P>
 *
 * @author Jo�o Silva
 * @version 1.0
 */
public final class ItemAPI {

    /**
     * Create a ItemStack
     * @param material material of the item stack
     * @param name name of the item stack
     * @param lines lines of the item stack
     * @return created item stack
     */
    public static ItemStack createItemStack(final Material material, final String name, final String[] lines) {
        return createItemStack(material.getId(), (short) 0, name, lines);
    }

    /**
     * Create a ItemStack
     * @param material material of the item stack
     * @param value value of the item stack
     * @param name name of the item stack
     * @param lines lines of the item stack
     * @return created item stack
     */
    public static ItemStack createItemStack(final Material material, final short value, final String name, final String[] lines) {
        return createItemStack(material.getId(), value, name, lines);
    }

    /**
     * Create a ItemStack
     * @param materialID material ID of the item stack
     * @param value value of the item stack
     * @param name name of the item stack
     * @param lines lines of the item stack
     * @return created item stack
     */
    public static ItemStack createItemStack(final int materialID, final short value, final String name, final String[] lines) {
        final ItemStack itemStack = new ItemStack(materialID, 1, value);

        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);

        final List<String> lore = new ArrayList<>();
        Collections.addAll(lore, lines);

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * Apply glow effect to a item
     * @param item item to be applied the glow effect
     * @return item wth glow effect
     */
    public static ItemStack applyGlowEffect(final ItemStack item) {
        final net.minecraft.server.v1_8_R3.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = null;
        if (!itemStack.hasTag()) {
            tag = new NBTTagCompound();
            itemStack.setTag(tag);
        }
        if (tag == null)
            tag = itemStack.getTag();
        final NBTTagList ench = new NBTTagList();
        tag.set("ench", ench);
        itemStack.setTag(tag);
        return CraftItemStack.asCraftMirror(itemStack);
    }

    /**
     * Randomize a item stack
     * @param materials possible materials for the item
     * @return randomized item stack
     */
    public static ItemStack randomizeItem(final Material[] materials) {
        return randomizeItem(materials, null, -1, -1);
    }

    /**
     * Randomize a item stack
     * @param materials possible materials for the item
     * @param enchants possible enchantments for the item
     * @param enchantProbability probability of an enchantment be applied to an item
     * @param maxEnchantLevel maximum level of the enchantment
     * @return randomized item stack
     */
    public static ItemStack randomizeItem(final Material[] materials, final org.bukkit.enchantments.Enchantment[] enchants, final double enchantProbability, final double maxEnchantLevel) {
        // ItemStack
        final ItemStack itemStack = new ItemStack(materials[(int) (Math.random() * materials.length)]);

        // Enchantment
        if (Math.random() < enchantProbability) {
            final org.bukkit.enchantments.Enchantment enchant = enchants[(int) (Math.random() * enchants.length)]; // Enchantment
            if (enchant.canEnchantItem(itemStack))
                itemStack.addUnsafeEnchantment(enchant, (int) (Math.random() * maxEnchantLevel));
        }

        return itemStack;
    }

    /**
     * Open an anvil menu to player
     * @param player player to open the anvil menu
     */
    public static void openAnvil(final Player player) {
        final EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();

        final AnvilContainer container = new AnvilContainer(entityPlayer);

        final int containerCounter = entityPlayer.nextContainerCounter();
        entityPlayer.playerConnection.sendPacket(new PacketPlayOutOpenWindow(containerCounter, "minecraft:anvil", new ChatMessage("Repairing"), 0));
        entityPlayer.activeContainer = container;
        entityPlayer.activeContainer.windowId = containerCounter;
        entityPlayer.activeContainer.addSlotListener(entityPlayer);
    }
}
