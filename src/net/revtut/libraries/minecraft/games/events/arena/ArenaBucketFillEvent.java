package net.revtut.libraries.minecraft.games.events.arena;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.player.PlayerData;

/**
 * Arena Bucket Fill Event
 */
public class ArenaBucketFillEvent extends ArenaEvent {

    /**
     * Player that filled the bucket
     */
    private final PlayerData player;

    /**
     * Constructor of ArenaBucketFillEvent
     *
     * @param arena arena where the event occurred
     * @param player player that filled the bucket
     */
    public ArenaBucketFillEvent(final Arena arena, final PlayerData player) {
        super(arena);

        this.player = player;
    }

    /**
     * Get the player that filled the bucket
     * @return player that filled the bucket
     */
    public PlayerData getPlayer() {
        return player;
    }
}