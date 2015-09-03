package net.revtut.libraries.games.events.player;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.player.PlayerData;
import org.bukkit.event.HandlerList;

/**
 * Player Damage By Player Event
 */
public class PlayerDamageByPlayerEvent extends PlayerEvent {

    /**
     * Damager of the target
     */
    private PlayerData damager;

    /**
     * Constructor of PlayerDamageByPlayerEvent
     * @param player player that was damaged in the arena
     * @param damager player that damaged the target
     * @param arena arena where the event occurred
     */
    public PlayerDamageByPlayerEvent(PlayerData player, PlayerData damager, Arena arena) {
        super(player, arena);
        this.damager = damager;
    }

    /**
     * Get the damager of the target
     * @return damager of the target
     */
    public PlayerData getDamager() {
        return damager;
    }

    /**
     * Get the handlers of the event
     * @return handlers of the event
     */
    public HandlerList getHandlers() {
        return super.getHandlers();
    }
}
