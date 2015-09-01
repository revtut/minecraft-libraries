package net.revtut.libraries.games.events.player;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.player.PlayerData;
import net.revtut.libraries.games.player.Team;
import org.bukkit.event.HandlerList;

/**
 * Player Leave Team Event
 */
public class PlayerLeaveTeamEvent extends PlayerLeaveArenaEvent {

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
        super(player, arena, leaveMessage);
        this.team = team;
    }

    /**
     * Get the team that was left
     * @return team that was left
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Get the handlers of the event
     * @return handlers of the event
     */
    public HandlerList getHandlers() {
        return super.getHandlers();
    }
}
