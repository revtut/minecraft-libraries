package net.revtut.libraries.minecraft.bukkit.games.events.player;

import net.revtut.libraries.minecraft.bukkit.games.arena.Arena;
import net.revtut.libraries.minecraft.bukkit.games.player.GamePlayer;
import net.revtut.libraries.minecraft.bukkit.games.team.Team;

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
    public PlayerJoinTeamEvent(final GamePlayer player, final Arena arena, final Team team, final String joinMessage) {
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
