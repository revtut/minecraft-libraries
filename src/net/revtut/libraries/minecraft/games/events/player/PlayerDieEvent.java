package net.revtut.libraries.minecraft.games.events.player;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.player.GamePlayer;

/**
 * Player Die Event
 */
public class PlayerDieEvent extends PlayerEvent {

    /**
     * Killer of the player
     */
    private final GamePlayer killer;

    /**
     * Message on death
     */
    private String deathMessage;

    /**
     * Constructor of PlayerDieEvent
     * @param player player that died in the arena
     * @param killer killer of the player
     * @param arena arena where the event occurred
     * @param deathMessage message on death
     */
    public PlayerDieEvent(final GamePlayer player, final GamePlayer killer, final Arena arena, final String deathMessage) {
        super(player, arena);
        this.killer = killer;
        this.deathMessage = deathMessage;
    }

    /**
     * Get the killer of the player
     * @return killer of the player
     */
    public GamePlayer getKiller() {
        return killer;
    }

    /**
     * Get the death message
     * @return death message
     */
    public String getDeathMessage() {
        return deathMessage;
    }

    /**
     * Set the death message
     * @param deathMessage new message on death
     */
    public void setDeathMessage(final String deathMessage) {
        this.deathMessage = deathMessage;
    }

}
