package net.revtut.libraries.minecraft.games.events.arena;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.player.PlayerData;

/**
 * Arena Bucket Empty Event
 */
public class ArenaBucketEmptyEvent extends ArenaEvent {

    /**
     * Player that emptied the bucket
     */
    private final PlayerData player;

    /**
     * Constructor of ArenaBucketEmptyEvent
     *
     * @param arena arena where the event occurred
     * @param player player that emptied the bucket
     */
    public ArenaBucketEmptyEvent(final Arena arena, final PlayerData player) {
        super(arena);

        this.player = player;
    }

    /**
     * Get the player that emptied the bucket
     * @return player that emptied the bucket
     */
    public PlayerData getPlayer() {
        return player;
    }
}