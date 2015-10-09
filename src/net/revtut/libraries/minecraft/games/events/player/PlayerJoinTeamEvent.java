package net.revtut.libraries.minecraft.games.events.player;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.player.PlayerData;
import net.revtut.libraries.minecraft.games.team.Team;

/**
 * Player Join Team Event
 */
public class PlayerJoinTeamEvent extends PlayerJoinArenaEvent {

    /**
     * Team that was joined
     */
    private final Team team;

    /**
     * Constructor of PlayerJoinTeamEvent
     * @param player player that joined the team
     * @param arena arena that was joined
     * @param team team that was joined
     * @param joinMessage join message
     */
    public PlayerJoinTeamEvent(final PlayerData player, final Arena arena, final Team team, final String joinMessage) {
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

}
