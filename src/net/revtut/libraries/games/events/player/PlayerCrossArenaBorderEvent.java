package net.revtut.libraries.games.events.player;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.player.PlayerData;
import org.bukkit.event.HandlerList;

/**
 * Player Cross Arena Border Event
 */
public class PlayerCrossArenaBorderEvent extends PlayerEvent {

    /**
     * Constructor of PlayerJoinArenaEvent
     * @param player player that crossed the arena border
     * @param arena arena that was crossed
     */
    public PlayerCrossArenaBorderEvent(PlayerData player, Arena arena) {
        super(player, arena);
    }

    /**
     * Get the handlers of the event
     * @return handlers of the event
     */
    public HandlerList getHandlers() {
        return super.getHandlers();
    }
}
