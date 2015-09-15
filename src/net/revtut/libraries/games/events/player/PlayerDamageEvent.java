package net.revtut.libraries.games.events.player;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.player.PlayerData;
import org.bukkit.event.HandlerList;

/**
 * Player Damage Event
 */
public class PlayerDamageEvent extends PlayerEvent {

    /**
     * Damage of the event
     */
    private double damage;

    /**
     * Constructor of PlayerDamageEvent
     * @param player player that was damages in the arena
     * @param arena arena where the event occurred
     * @param damage damage of the event
     */
    public PlayerDamageEvent(PlayerData player, Arena arena, double damage) {
        super(player, arena);
        this.damage = damage;
    }

    /**
     * Get the damage of the event
     * @return damage of the event
     */
    public double getDamage() {
        return damage;
    }

    /**
     * Set the damage of the event
     * @param damage new value of event damage
     */
    public void setDamage(double damage) {
        this.damage = damage;
    }

    /**
     * Get the handlers of the event
     * @return handlers of the event
     */
    public HandlerList getHandlers() {
        return super.getHandlers();
    }
}
