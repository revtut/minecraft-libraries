package net.revtut.libraries.games.events.player;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.player.PlayerData;
import org.bukkit.event.HandlerList;

/**
 * Player Damage Event
 */
public class PlayerDamageEvent extends PlayerEvent {

    /**
     * Constructor of PlayerDamageEvent
     * @param player player that was damages in the arena
     * @param arena arena where the event occurred
     */
    public PlayerDamageEvent(PlayerData player, Arena arena) {
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
