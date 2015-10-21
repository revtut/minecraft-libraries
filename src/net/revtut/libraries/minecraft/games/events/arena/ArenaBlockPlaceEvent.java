package net.revtut.libraries.minecraft.games.events.arena;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.player.GamePlayer;

/**
 * Arena Block Place Event
 */
public class ArenaBlockPlaceEvent extends ArenaEvent {

    /**
     * Player that place the block
     */
    private final GamePlayer player;

    /**
     * Constructor of ArenaBlockPlaceEvent
     *
     * @param arena arena where the event occurred
     * @param player player that place the block
     */
    public ArenaBlockPlaceEvent(final Arena arena, final GamePlayer player) {
        super(arena);

        this.player = player;
    }

    /**
     * Get the player that place the block
     * @return player that place the block
     */
    public GamePlayer getPlayer() {
        return player;
    }
}