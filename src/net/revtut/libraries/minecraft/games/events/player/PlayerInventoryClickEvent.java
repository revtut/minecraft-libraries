package net.revtut.libraries.minecraft.games.events.player;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.player.GamePlayer;
import org.bukkit.inventory.Inventory;

/**
 * Player Inventory Click Event
 */
public class PlayerInventoryClickEvent extends PlayerEvent {

    /**
     * Inventory where the click occurred
     */
    private final Inventory inventory;

    /**
     * Slot of the click
     */
    private final int slot;

    /**
     * Constructor of PlayerInventoryClickEvent
     * @param player player that clicked on the inventory in the arena
     * @param arena arena where the event occurred
     * @param inventory inventory where the click occurred
     * @param slot slot of the click on the event
     */
    public PlayerInventoryClickEvent(final GamePlayer player, final Arena arena, final Inventory inventory, final int slot) {
        super(player, arena);
        this.inventory = inventory;
        this.slot = slot;
    }

    /**
     * Get the inventory where the click occurred
     * @return inventory where the click occurred
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Get the slot of the click on the event
     * @return slot of the click on the event
     */
    public int getSlot() {
        return slot;
    }
}
