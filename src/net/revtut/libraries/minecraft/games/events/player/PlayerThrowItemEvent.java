package net.revtut.libraries.minecraft.games.events.player;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.player.GamePlayer;
import org.bukkit.entity.Item;

/**
 * Player Throw Item Event
 */
public class PlayerThrowItemEvent extends PlayerEvent {

    /**
     * Item that was thrown
     */
    private final Item item;

    /**
     * Constructor of PlayerThrowItemEvent
     * @param player player that thrown the item in the arena
     * @param arena arena where the event occurred
     * @param item item that was thrown
     */
    public PlayerThrowItemEvent(final GamePlayer player, final Arena arena, final Item item) {
        super(player, arena);
        this.item = item;
    }

    /**
     * Get the item that was thrown
     * @return item that was thrown
     */
    public Item getItem() {
        return item;
    }
}
