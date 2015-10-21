package net.revtut.libraries.minecraft.games.events.player;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.player.GamePlayer;

/**
 * Player Jump Event
 */
public class PlayerJumpEvent extends PlayerEvent {

    /**
     * Height of the jump
     */
    private final double height;

    /**
     * Constructor of PlayerJumpEvent
     * @param player player that jumped in the arena
     * @param arena arena where the event occurred
     * @param height height of the jump
     */
    public PlayerJumpEvent(final GamePlayer player, final Arena arena, final double height) {
        super(player, arena);
        this.height = height;
    }

    /**
     * Get the height of the jump
     * @return height of the jump
     */
    public double getHeight() {
        return height;
    }
}
