package net.revtut.libraries.games.events.player;

import net.revtut.libraries.games.Team;
import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.player.PlayerData;
import org.bukkit.event.HandlerList;

/**
 * Player Join Team Event
 */
public class PlayerJoinTeamEvent extends PlayerEvent {

    /**
     * Message on joining the team
     */
    private String joinMessage;

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
        super(player, arena);
        this.joinMessage = joinMessage;
        this.team = team;
    }

    /**
     * Get the join message
     * @return join message
     */
    public String getJoinMessage() {
        return this.joinMessage;
    }

    /**
     * Get the team that was joined
     * @return team that was joined
     */
    public Team getTeam() {
        return team;
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
