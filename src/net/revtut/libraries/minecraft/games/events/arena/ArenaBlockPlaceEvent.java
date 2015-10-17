package net.revtut.libraries.minecraft.games.events.arena;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.player.PlayerData;

/**
 * Arena Block Place Event
 */
public class ArenaBlockPlaceEvent extends ArenaEvent {

    /**
     * Player that place the block
     */
    private final PlayerData player;

    /**
     * Constructor of ArenaBlockPlaceEvent
     *
     * @param arena arena where the event occurred
     * @param player player that place the block
     */
    public ArenaBlockPlaceEvent(final Arena arena, final PlayerData player) {
        super(arena);

        this.player = player;
    }

    /**
     * Get the player that place the block
     * @return player that place the block
     */
    public PlayerData getPlayer() {
        return player;
    }
}