package net.revtut.libraries.minecraft.bukkit.games.events.player;

import net.revtut.libraries.minecraft.bukkit.games.arena.Arena;
import net.revtut.libraries.minecraft.bukkit.games.player.GamePlayer;

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
    public PlayerLeaveArenaEvent(final GamePlayer player, final Arena arena, final String joinMessage) {
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
    public void setLeaveMessage(final String leaveMessage) {
        this.leaveMessage = leaveMessage;
    }

}
