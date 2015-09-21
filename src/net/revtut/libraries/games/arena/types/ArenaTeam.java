package net.revtut.libraries.games.arena.types;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.arena.session.GameSession;
import net.revtut.libraries.games.player.PlayerData;
import net.revtut.libraries.games.team.Team;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Arena Team Object.
 * Arena where you have teams, each team has several players.
 */
public class ArenaTeam extends Arena {

    /**
     * List with all teams of the arena
     */
    private List<Team> teams;

    /**
     * Map with spawn locations per team and death match locations per team
     */
    private Map<Team, List<Location>> spawnLocations, deathMatchLocations;

    /**
     * Map with death location per team
     */
    private Map<Team, Location> deathLocations, deathDeathMatchLocation;

    /**
     * Constructor of ArenaTeam
     * @param name name of the arena
     */
    public ArenaTeam(String name) {
        super(name);
    }

    /**
     * Initialize the arena
     * @param arenaWorld world of the arena
     * @param lobbyLocation location of the lobby
     * @param spectatorLocation location of the spectator's spawn
     * @param spectatorDeathMatchLocation location of the spectator's spawn on death match
     * @param corners corners of the arena
     * @param cornersDeathMatch corners of the death match arena
     * @param spawnLocations locations of the spawn
     * @param deathLocations locations to spawn dead players
     * @param deathMatchLocations locations for the death match
     * @param teams teams of the arena
     * @param gameSession session of the arena
     */
    public void initialize(World arenaWorld, Location lobbyLocation, Location spectatorLocation, Location spectatorDeathMatchLocation,
                           Location[] corners, Location[] cornersDeathMatch, Map<Team, List<Location>> spawnLocations, Map<Team, Location> deathLocations,
                           Map<Team, Location> deathDeathMatchLocation, Map<Team, List<Location>> deathMatchLocations, List<Team> teams, GameSession gameSession) {
        super.initialize(arenaWorld, lobbyLocation, spectatorLocation, spectatorDeathMatchLocation, corners, cornersDeathMatch, gameSession);
        this.spawnLocations = spawnLocations;
        this.deathLocations = deathLocations;
        this.deathDeathMatchLocation = deathDeathMatchLocation;
        this.deathMatchLocations = deathMatchLocations;
        this.teams = teams;
    }

    /**
     * Get the type of the arena
     * @return type of the arena
     */
    public ArenaType getType() {
        return ArenaType.TEAM;
    }

    /**
     * Get all the teams of the arena
     * @return teams of the arena
     */
    public List<Team> getTeams() {
        return teams;
    }

    /**
     * Get the team of a player
     * @param player player to get its team
     * @return team of the player
     */
    public Team getTeam(PlayerData player) {
        for(Team team : teams)
            if(team.containsPlayer(player))
                return team;
        return null;
    }

    /**
     * Get the emptier team
     * @return emptier team
     */
    public Team getEmptierTeam() {
        Team emptierTeam = null;
        int minimumPlayers = Integer.MAX_VALUE;
        for(Team team : teams) {
            int numberPlayers = team.getAllPlayers().size();
            if (numberPlayers < minimumPlayers) {
                emptierTeam = team;
                minimumPlayers = numberPlayers;
            }
        }

        return emptierTeam;
    }

    /**
     * Get the spawn locations of a team
     * @param team team to get spawn locations
     * @return spawn locations of the team
     */
    public List<Location> getSpawnLocations(Team team) {
        return spawnLocations.get(team);
    }

    /**
     * Get the death location of a team
     * @param team team to get death location
     * @return death location of the team
     */
    public Location getDeathLocation(Team team) {
        return deathLocations.get(team);
    }

    /**
     * Get the death location of a team on death match
     * @param team team to get death location on death match
     * @return death location of the team on death match
     */
    public Location getDeathDeathMatchLocation(Team team) {
        return deathDeathMatchLocation.get(team);
    }

    /**
     * Get the death match locations of a team
     * @param team team to get death match locations
     * @return death match locations of the team
     */
    public List<Location> getDeathMatchLocations(Team team) {
        return deathMatchLocations.get(team);
    }

    /**
     * Get all the players on the arena
     * @return players on the arena
     */
    public List<PlayerData> getAllPlayers() {
        List<PlayerData> players = new ArrayList<>();
        for(Team team : teams)
            players.addAll(team.getAllPlayers());
        return players;
    }

    /**
     * Check if the arena contains a given player by its UUID
     * @param uuid uuid of the player to be checked
     * @return true if contains, false otherwise
     */
    public boolean containsPlayer(UUID uuid) {
        for(Team team : teams)
            if(team.containsPlayer(uuid))
                return true;
        return false;
    }

    /**
     * Check if two players are on the same team
     * @param player player one
     * @param target player two
     * @return true if they are on the same team, false otherwise
     */
    public boolean sameTeam(PlayerData player, PlayerData target) {
        return getTeam(player).equals(getTeam(target));
    }

    /**
     * Make a player join the arena
     * @param player player to join
     */
    @Override
    public boolean join(PlayerData player) {
        if(teams.size() <= 0)
            throw new IllegalStateException("Player is trying to join a arena without any team!");

        return join(player, getEmptierTeam());
    }

    /**
     * Make a player join the arena to a certain team
     * @param player player to join
     * @param team team to be joined
     * @return true if has joined, false otherwise
     */
    public boolean join(PlayerData player, Team team) {
        if(!super.join(player))
            return false;

        team.join(player);

        return true;
    }

    /**
     * Make a player leave the arena
     * @param player player to leave
     * @return true if has left, false otherwise
     */
    @Override
    public boolean leave(PlayerData player) {
        if(!super.leave(player))
            return false;

        for(Team team : teams) {
            if (!team.containsPlayer(player))
                continue;

            team.leave(player);
            break;
        }

        return true;
    }

    /**
     * Make a player spectate the arena
     * @param player player to spectate
     * @return true if is spectating, false otherwise
     */
    public boolean spectate(PlayerData player) {
        if(!super.spectate(player))
            return false;

        if(teams.size() <= 0)
            throw new IllegalStateException("Player is trying to spectate a arena without any team!");

        teams.get(0).spectate(player);

        return true;
    }
}
