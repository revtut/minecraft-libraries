package net.revtut.libraries.minecraft.games.events.arena;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.player.GamePlayer;

/**
 * Arena Block Break Event
 */
public class ArenaBlockBreakEvent extends ArenaEvent {

    /**
     * Player that broke the block
     */
    private final GamePlayer player;

    /**
     * Constructor of ArenaBlockBreakEvent
     *
     * @param arena arena where the event occurred
     * @param player player that broke the block
     */
    public ArenaBlockBreakEvent(final Arena arena, final GamePlayer player) {
        super(arena);

        this.player = player;
    }

    /**
     * Get the player that broke the block
     * @return player that broke the block
     */
    public GamePlayer getPlayer() {
        return player;
    }
}