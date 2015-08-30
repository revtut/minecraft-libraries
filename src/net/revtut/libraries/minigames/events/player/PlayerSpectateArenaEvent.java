package net.revtut.libraries.minigames.events.player;

import net.revtut.libraries.minigames.arena.Arena;
import net.revtut.libraries.minigames.player.PlayerData;
import org.bukkit.event.HandlerList;

/**
 * Player Spectate Arena Event
 */
public class PlayerSpectateArenaEvent extends PlayerEvent {

    /**
     * Message on joining the arena
     */
    private String joinMessage;

    /**
     * Constructor of PlayerSpectateArenaEvent
     * @param player player that spectated the arena
     * @param arena arena that was spectated
     * @param joinMessage join message
     */
    public PlayerSpectateArenaEvent(PlayerData player, Arena arena, String joinMessage) {
        super(player, arena);
        this.joinMessage = joinMessage;
    }

    /**
     * Get the join message
     * @return join message
     */
    public String getJoinMessage() {
        return this.joinMessage;
    }

    /**
     * Set the join message
     * @param joinMessage new join message
     */
    public void setJoinMessage(String joinMessage) {
        this.joinMessage = joinMessage;
    }

    /**
     * Get the handlers of the event
     * @return handlers of the event
     */
    public HandlerList getHandlers() {
        return super.getHandlers();
    }
}
