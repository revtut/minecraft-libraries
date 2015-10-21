package net.revtut.libraries.minecraft.games.events.player;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.player.GamePlayer;

/**
 * Player Hunger Event
 */
public class PlayerHungerEvent extends PlayerEvent {

    /**
     * Hunger level of the event
     */
    private int hunger;

    /**
     * Constructor of PlayerHungerEvent
     * @param player player that has its food level changed in the arena
     * @param arena arena where the event occurred
     * @param hunger hunger level of the event
     */
    public PlayerHungerEvent(final GamePlayer player, final Arena arena, final int hunger) {
        super(player, arena);
        this.hunger = hunger;
    }

    /**
     * Get the hunger level of the event
     * @return hunger level of the event
     */
    public int getHunger() {
        return hunger;
    }

    /**
     * Set the hunger level of the event
     * @param hunger new value of event hunger level
     */
    public void setHunger(final int hunger) {
        this.hunger = hunger;
    }
}
