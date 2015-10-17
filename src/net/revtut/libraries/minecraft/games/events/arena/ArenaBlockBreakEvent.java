package net.revtut.libraries.minecraft.games.events.arena;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.player.PlayerData;

/**
 * Arena Block Break Event
 */
public class ArenaBlockBreakEvent extends ArenaEvent {

    /**
     * Player that broke the block
     */
    private final PlayerData player;

    /**
     * Constructor of ArenaBlockBreakEvent
     *
     * @param arena arena where the event occurred
     * @param player player that broke the block
     */
    public ArenaBlockBreakEvent(final Arena arena, final PlayerData player) {
        super(arena);

        this.player = player;
    }

    /**
     * Get the player that broke the block
     * @return player that broke the block
     */
    public PlayerData getPlayer() {
        return player;
    }
}