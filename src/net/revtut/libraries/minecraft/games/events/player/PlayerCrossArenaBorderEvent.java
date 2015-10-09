package net.revtut.libraries.minecraft.games.events.player;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.player.PlayerData;

/**
 * Player Cross Arena Border Event
 */
public class PlayerCrossArenaBorderEvent extends PlayerEvent {

    /**
     * Constructor of PlayerJoinArenaEvent
     * @param player player that crossed the arena border
     * @param arena arena that was crossed
     */
    public PlayerCrossArenaBorderEvent(final PlayerData player, final Arena arena) {
        super(player, arena);
    }

}