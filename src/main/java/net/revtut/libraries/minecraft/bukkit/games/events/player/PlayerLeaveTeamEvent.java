package net.revtut.libraries.minecraft.bukkit.games.events.player;

import net.revtut.libraries.minecraft.bukkit.games.arena.Arena;
import net.revtut.libraries.minecraft.bukkit.games.player.GamePlayer;
import net.revtut.libraries.minecraft.bukkit.games.team.Team;

/**
 * Player Leave Team Event
 */
public class PlayerLeaveTeamEvent extends PlayerLeaveArenaEvent {

    /**
     * Team that was joined
     */
    private final Team team;

    /**
     * Constructor of PlayerLeaveTeamEvent
     * @param player player that left the team
     * @param arena arena that was left
     * @param team team that was left
     * @param leaveMessage leave message
     */
    public PlayerLeaveTeamEvent(final GamePlayer player, final Arena arena, final Team team, final String leaveMessage) {
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

}
