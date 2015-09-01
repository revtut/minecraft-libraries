package net.revtut.libraries.games.arena.types;

import net.revtut.libraries.Libraries;
import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.arena.session.GameSession;
import net.revtut.libraries.games.arena.session.GameState;
import net.revtut.libraries.games.player.PlayerData;
import net.revtut.libraries.games.player.Team;
import net.revtut.libraries.utils.WorldAPI;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

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
    private Map<Team, Location> deathLocations;

    /**
     * Constructor of ArenaTeam
     * @param plugin plugin owner of the arena
     * @param worldsFolder folder where worlds are located
     */
    public ArenaTeam(JavaPlugin plugin, File worldsFolder) {
        super(plugin, worldsFolder);
    }

    /**
     * Initialize the arena
     * @param arenaWorld world of the arena
     * @param lobbyLocation location of the lobby
     * @param spectatorLocation location of the spectator's spawn
     * @param corners corners of the arena
     * @param spawnLocations locations of the spawn
     * @param deathLocations locations to spawn dead players
     * @param deathMatchLocations locations for the death match
     * @param teams teams of the arena
     * @param gameSession session of the arena
     */
    public void init(World arenaWorld, Location lobbyLocation, Location spectatorLocation, Location[] corners, Map<Team, List<Location>> spawnLocations, Map<Team, Location> deathLocations, Map<Team, List<Location>> deathMatchLocations, List<Team> teams, GameSession gameSession) {
        super.init(arenaWorld, lobbyLocation, spectatorLocation, corners, gameSession);
        this.spawnLocations = spawnLocations;
        this.deathLocations = deathLocations;
        this.deathMatchLocations = deathMatchLocations;
        this.teams = teams;
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

    /**
     * Building the arena
     */
    public void build() {
        // First time calling this method
        if(getSession().getState() != GameState.BUILD) {
            Libraries.getInstance().getLogger().log(Level.INFO, "[" + getName() + "] Started building!");

            // Remove previous world
            if(getWorld() != null) {
                String worldName = getWorld().getName();

                WorldAPI.unloadWorld(worldName);

                File worldFolder = new File(System.getProperty("user.dir") + File.separator + worldName);
                WorldAPI.removeDirectory(worldFolder);
            }

            // Copy world folder
            String[] listWorlds = getWorldsFolder().list();
            if (listWorlds == null)
                throw new IllegalStateException("List of worlds is null.");

            int posWorld = (int) (Math.random() * listWorlds.length);
            final String sourcePath = new File(getWorldsFolder() + File.separator + listWorlds[posWorld]).getAbsolutePath();
            final String mapName = getId() + "_" + listWorlds[posWorld];
            final String targetPath = new File(System.getProperty("user.dir") + File.separator + mapName).getAbsolutePath();

            WorldAPI.copyDirectory(new File(sourcePath), new File(targetPath));

            // Load World
            World world = WorldAPI.loadWorldAsync(mapName);
            if(world == null)
                throw new IllegalStateException("Loaded world is null.");
            world.setAutoSave(false);
        }
    }
}
