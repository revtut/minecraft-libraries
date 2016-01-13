package net.revtut.libraries.minecraft.bukkit.games.events.player;

import net.revtut.libraries.minecraft.bukkit.games.arena.Arena;
import net.revtut.libraries.minecraft.bukkit.games.player.GamePlayer;

/**
 * Player Cross Arena Border Event
 */
public class PlayerCrossArenaBorderEvent extends PlayerEvent {

    /**
     * Constructor of PlayerJoinArenaEvent
     * @param player player that crossed the arena border
     * @param arena arena that was crossed
     */
    public PlayerCrossArenaBorderEvent(final GamePlayer player, final Arena arena) {
        super(player, arena);
    }

}
