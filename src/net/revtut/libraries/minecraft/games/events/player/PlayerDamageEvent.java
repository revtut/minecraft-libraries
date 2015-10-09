package net.revtut.libraries.minecraft.games.events.player;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.player.PlayerData;

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
    public PlayerDamageEvent(final PlayerData player, final Arena arena, final double damage) {
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
    public void setDamage(final double damage) {
        this.damage = damage;
    }

}
