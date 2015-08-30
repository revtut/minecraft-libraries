package net.revtut.libraries.minigames.arena.types;

import net.revtut.libraries.Libraries;
import net.revtut.libraries.minigames.Team;
import net.revtut.libraries.minigames.arena.Arena;
import net.revtut.libraries.minigames.arena.ArenaState;
import net.revtut.libraries.minigames.player.PlayerData;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Arena Team Object
 */
public abstract class ArenaTeam extends Arena {

    /**
     * List with all teams of the arena
     */
    private List<Team> teams;

    /**
     * Map with spawn locations per team
     */
    private Map<Team, List<Location>> spawnLocations;

    /**
     * Map with death location per team
     */
    private Map<Team, Location> deathLocations;

    /**
     * Map with death match locations per team
     */
    private Map<Team, List<Location>> deathMatchLocations;

    /**
     * Constructor of ArenaTeam
     * @param plugin plugin owner of the arena
     */
    public ArenaTeam(JavaPlugin plugin) {
        super(plugin);

        this.teams = new ArrayList<>();
        this.spawnLocations = new HashMap<>();
        this.deathLocations = new HashMap<>();
        this.deathMatchLocations = new HashMap<>();
    }

    /**
     * Initialize the arena
     * @param worldsFolder folder where worlds are located
     * @param minPlayers minimum players to start the game
     * @param maxPlayers maximum players on the arena
     * @param arenaWorld world of the arena
     * @param lobbyLocation location of the lobby
     * @param spectatorLocation location of the spectator's spawn
     * @param spawnLocations locations of the spawn
     * @param deathLocations locations to spawn dead players
     * @param deathMatchLocations locations for the death match
     */
    public void init(File worldsFolder, int minPlayers, int maxPlayers, World arenaWorld, Location lobbyLocation, Location spectatorLocation, Map<Team, List<Location>> spawnLocations, Map<Team, Location> deathLocations, Map<Team, List<Location>> deathMatchLocations) {
        super.init(worldsFolder, minPlayers, maxPlayers, arenaWorld, lobbyLocation, spectatorLocation);
        this.spawnLocations = spawnLocations;
        this.deathLocations = deathLocations;
        this.deathMatchLocations = deathMatchLocations;
    }

    /**
     * Get all the teams of the arena
     * @return teams of the arena
     */
    public List<Team> getTeams() {
        return teams;
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
     * Make a player join the arena
     * @param player player to join
     */
    @Override
    public void join(PlayerData player) {
        if(teams.size() <= 0)
            throw new IllegalStateException("Player is trying to join a arena without any team!");

        join(player, getEmptierTeam());
    }

    /**
     * Make a player join the arena to a certain team
     * @param player player to join
     * @param team team to be joined
     */
    public void join(PlayerData player, Team team) {
        super.join(player);

        team.join(player);
    }

    /**
     * Make a player leave the arena
     * @param player player to leave
     */
    @Override
    public void leave(PlayerData player) {
        super.leave(player);

        for(Team team : teams) {
            if (!team.isOnTeam(player))
                continue;

            team.leave(player);
            break;
        }
    }

    /**
     * Make a player spectate the arena
     * @param player player to spectate
     */
    public void spectate(PlayerData player) {
        super.spectate(player);

        if(teams.size() <= 0)
            throw new IllegalStateException("Player is trying to spectate a arena without any team!");

        teams.get(0).spectate(player);
    }

    /**
     * Building the arena
     */
    @Override
    public void build() {
        // First time calling this method
        if(getState() != ArenaState.BUILD) {
            // Build the arena
        }

        Libraries.getInstance().getLogger().log(Level.INFO, "[" + getName() + "] Started building!");
    }

    /**
     * Waiting for players to join
     */
    @Override
    public void lobby() {
        // First time calling this method
        if(getState() != ArenaState.LOBBY) {

        }

        Libraries.getInstance().getLogger().log(Level.INFO, "[" + getName() + "] Started waiting for players!");
    }

    /**
     * Warming up the game
     */
    @Override
    public void warmUp() {
        // First time calling this method
        if(getState() != ArenaState.WARMUP) {

        }

        Libraries.getInstance().getLogger().log(Level.INFO, "[" + getName() + "] Started the warm up!");
    }

    /**
     * Game is running
     */
    @Override
    public void start() {
        // First time calling this method
        if(getState() != ArenaState.START) {

        }

        Libraries.getInstance().getLogger().log(Level.INFO, "[" + getName() + "] Started the game!");
    }

    /**
     * Death match is running
     */
    @Override
    public void deathMatch() {
        // First time calling this method
        if(getState() != ArenaState.DEATHMATCH) {

        }

        Libraries.getInstance().getLogger().log(Level.INFO, "[" + getName() + "] Started the death match!");
    }

    /**
     * Game has finished
     */
    @Override
    public void finish() {
        // First time calling this method
        if(getState() != ArenaState.FINISH) {

        }

        Libraries.getInstance().getLogger().log(Level.INFO, "[" + getName() + "] Finished the game!");
    }

    /**
     * Stop the arena
     */
    @Override
    public void stop() {
        // First time calling this method
        if(getState() != ArenaState.STOP) {

        }

        Libraries.getInstance().getLogger().log(Level.INFO, "[" + getName() + "] Started stopping!");
    }
    /**
     * Update the arena game in the database
     */
    public void updateDatabase() {

    }
}
