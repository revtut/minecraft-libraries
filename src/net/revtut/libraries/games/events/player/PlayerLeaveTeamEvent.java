package net.revtut.libraries.games.events.player;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.player.PlayerData;
import net.revtut.libraries.games.player.Team;
import org.bukkit.event.HandlerList;

/**
 * Player Leave Team Event
 */
public class PlayerLeaveTeamEvent extends PlayerEvent {

    /**
     * Message on leaving the team
     */
    private String leaveMessage;

    /**
     * Team that was joined
     */
    private Team team;

    /**
     * Constructor of PlayerLeaveTeamEvent
     * @param player player that left the team
     * @param arena arena that was left
     * @param team team that was left
     * @param leaveMessage leave message
     */
    public PlayerLeaveTeamEvent(PlayerData player, Arena arena, Team team, String leaveMessage) {
        super(player, arena);
        this.leaveMessage = leaveMessage;
        this.team = team;
    }

    /**
     * Get the leave message
     * @return leave message
     */
    public String getLeaveMessage() {
        return this.leaveMessage;
    }

    /**
     * Get the team that was left
     * @return team that was left
     */
    public Team getTeam() {
        return team;
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
