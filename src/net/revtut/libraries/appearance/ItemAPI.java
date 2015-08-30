package net.revtut.libraries.appearance;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Item API.
 *
 * <P>Help make items with simple methods</P>
 *
 * @author João Silva
 * @version 1.0
 */
public final class ItemAPI {

    /**
     * Create a ItemStack
     * @param material material of the item stack
     * @param name name of the item stack
     * @param lines lines of the item stack
     * @return
     */
    public static ItemStack createItemStack(Material material, String name, String[] lines) {
        return createItemStack(material.getId(), (short) 0, name, lines);
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
        return createItemStack(material.getId(), value, name, lines);
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
        ItemStack itemStack = new ItemStack(materialID, 1, value);
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
        net.minecraft.server.v1_8_R3.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
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

    /**
     * Open an anvil menu to player
     * @param player player to open the anvil menu
     */
    public static void openAnvil(Player player) {
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();

        AnvilContainer container = new AnvilContainer(entityPlayer);

        int containerCounter = entityPlayer.nextContainerCounter();
        entityPlayer.playerConnection.sendPacket(new PacketPlayOutOpenWindow(containerCounter, "minecraft:anvil", new ChatMessage("Repairing"), 0));
        entityPlayer.activeContainer = container;
        entityPlayer.activeContainer.windowId = containerCounter;
        entityPlayer.activeContainer.addSlotListener(entityPlayer);
    }
}
