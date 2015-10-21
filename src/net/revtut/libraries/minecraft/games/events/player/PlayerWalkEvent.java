package net.revtut.libraries.minecraft.games.events.player;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.player.GamePlayer;

/**
 * Player Walk Event
 */
public class PlayerWalkEvent extends PlayerEvent {

    /**
     * Walked distance
     */
    private final double distance;

    /**
     * Constructor of PlayerWalkEvent
     * @param player player that walked in the arena
     * @param arena arena where the event occurred
     * @param distance walked distance
     */
    public PlayerWalkEvent(final GamePlayer player, final Arena arena, final double distance) {
        super(player, arena);
        this.distance = distance;
    }

    /**
     * Get the walked distance
     * @return walked distance
     */
    public double getDistance() {
        return distance;
    }
}
