package net.revtut.libraries.minecraft.bukkit.games.events.arena;

import net.revtut.libraries.minecraft.bukkit.games.arena.Arena;
import net.revtut.libraries.minecraft.bukkit.games.player.GamePlayer;

/**
 * Arena Bucket Empty Event
 */
public class ArenaBucketEmptyEvent extends ArenaEvent {

    /**
     * Player that emptied the bucket
     */
    private final GamePlayer player;

    /**
     * Constructor of ArenaBucketEmptyEvent
     *
     * @param arena arena where the event occurred
     * @param player player that emptied the bucket
     */
    public ArenaBucketEmptyEvent(final Arena arena, final GamePlayer player) {
        super(arena);

        this.player = player;
    }

    /**
     * Get the player that emptied the bucket
     * @return player that emptied the bucket
     */
    public GamePlayer getPlayer() {
        return player;
    }
}