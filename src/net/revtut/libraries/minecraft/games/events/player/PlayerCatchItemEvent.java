package net.revtut.libraries.minecraft.games.events.player;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.player.GamePlayer;
import org.bukkit.entity.Item;

/**
 * Player Catch Item Event
 */
public class PlayerCatchItemEvent extends PlayerEvent {

    /**
     * Item that was caught
     */
    private final Item item;

    /**
     * Constructor of PlayerCatchItemEvent
     * @param player player that thrown the item in the arena
     * @param arena arena where the event occurred
     * @param item item that was caught
     */
    public PlayerCatchItemEvent(final GamePlayer player, final Arena arena, final Item item) {
        super(player, arena);
        this.item = item;
    }

    /**
     * Get the item that was caught
     * @return item that was caught
     */
    public Item getItem() {
        return item;
    }
}
