package net.revtut.libraries.minigames.events.player;

import net.revtut.libraries.minigames.arena.Arena;
import net.revtut.libraries.minigames.player.PlayerData;
import org.bukkit.event.HandlerList;

/**
 * Player Leave Arena Event
 */
public class PlayerLeaveArenaEvent extends PlayerEvent {

    /**
     * Message on leaving the arena
     */
    private String leaveMessage;

    /**
     * Constructor of PlayerLeaveArenaEvent
     * @param player player that left the arena
     * @param arena arena that was left
     * @param joinMessage leave message
     */
    public PlayerLeaveArenaEvent(PlayerData player, Arena arena, String joinMessage) {
        super(player, arena);
        this.leaveMessage = joinMessage;
    }

    /**
     * Get the leave message
     * @return leave message
     */
    public String getLeaveMessage() {
        return this.leaveMessage;
    }

    /**
     * Set the leave message
     * @param leaveMessage new leave message
     */
    public void setLeaveMessage(String leaveMessage) {
        this.leaveMessage = leaveMessage;
    }

    /**
     * Get the handlers of the event
     * @return handlers of the event
     */
    public HandlerList getHandlers() {
        return super.getHandlers();
    }
}
