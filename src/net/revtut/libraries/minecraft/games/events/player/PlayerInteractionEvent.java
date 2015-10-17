package net.revtut.libraries.minecraft.games.events.player;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.player.PlayerData;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

/**
 * Player Interaction Event
 */
public class PlayerInteractionEvent extends PlayerEvent {

    /**
     * Action type of the event
     */
    private final Action action;

    /**
     * Clicked block on the event
     */
    private final Block clickedBlock;

    /**
     * Item stack used in the event
     */
    private final ItemStack itemStack;

    /**
     * Constructor of PlayerInteractionEvent
     * @param player player that interacted in the arena
     * @param arena arena where the event occurred
     * @param action action type of the event
     * @param clickedBlock clicked block on the event
     * @param itemStack item stack used in the event
     */
    public PlayerInteractionEvent(final PlayerData player, final Arena arena, final Action action, final Block clickedBlock, final ItemStack itemStack) {
        super(player, arena);
        this.action = action;
        this.clickedBlock = clickedBlock;
        this.itemStack = itemStack;
    }

    /**
     * Get the action type of the event
     * @return action type of the event
     */
    public Action getAction() {
        return action;
    }

    /**
     * Get the clicked block on the event
     * @return clicked block on the event
     */
    public Block getClickedBlock() {
        return clickedBlock;
    }

    /**
     * Get the item stack used on the event
     * @return item stack used on the event
     */
    public ItemStack getItemStack() {
        return itemStack;
    }
}
