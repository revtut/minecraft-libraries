package net.revtut.libraries.games.events.player;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.player.PlayerData;
import net.revtut.libraries.games.player.Team;
import org.bukkit.event.HandlerList;

/**
 * Player Join Team Event
 */
public class PlayerJoinTeamEvent extends PlayerJoinArenaEvent {

    /**
     * Team that was joined
     */
    private Team team;

    /**
     * Constructor of PlayerJoinTeamEvent
     * @param player player that joined the team
     * @param arena arena that was joined
     * @param team team that was joined
     * @param joinMessage join message
     */
    public PlayerJoinTeamEvent(PlayerData player, Arena arena, Team team, String joinMessage) {
        super(player, arena, joinMessage);
        this.team = team;
    }

    /**
     * Get the team that was joined
     * @return team that was joined
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
