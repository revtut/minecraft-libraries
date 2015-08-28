package net.revtut.libraries.minigames.arena.types;

import net.revtut.libraries.Libraries;
import net.revtut.libraries.minigames.Team;
import net.revtut.libraries.minigames.arena.Arena;
import net.revtut.libraries.minigames.arena.ArenaState;
import net.revtut.libraries.minigames.player.PlayerData;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Arena Team Object
 */
public class ArenaTeam extends Arena {

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
     * Constructor of ArenaTeam
     * @param plugin plugin owner of the arena
     */
    public ArenaTeam(JavaPlugin plugin) {
        super(plugin);

        this.teams = new ArrayList<>();
        this.spawnLocations = new HashMap<>();
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
     * Get the number of players on the arena
     * @return number of players on the arena
     */
    @Override
    public int numberPlayers() {
        return getAllPlayers().size();
    }

    /**
     * Make a player join the arena
     * @param player player to join
     */
    @Override
    public void join(PlayerData player) {

    }

    /**
     * Make a player join the arena to a certain team
     * @param player player to join
     * @param team team to be joined
     */
    public void join(PlayerData player, Team team) {

    }

    /**
     * Make a player leave the arena
     * @param player player to leave
     */
    @Override
    public void leave(PlayerData player) {

    }

    /**
     * Make a player spectate the arena
     * @param player player to spectate
     */
    public void spectate(PlayerData player) {

    }

    /**
     * Check if a player can join a arena
     * @param player player to be joined
     * @return true if can, false otherwise
     */
    @Override
    public boolean canJoin(PlayerData player) {
        // Arena is already ingame
        if(getState() != ArenaState.JOIN)
            return false;

        // Maximum players already achieved
        if(numberPlayers() >= getConfiguration().getMaxPlayers())
            return false;

        return true;
    }

    /**
     * Start building the arena
     */
    @Override
    public void build() {
        updateState(ArenaState.BUILD);
        updateTimer(Integer.MAX_VALUE);

        Libraries.getInstance().getLogger().log(Level.INFO, "[" + getName() + "] Started building!");

        // Build the arena, after that update timer
    }

    /**
     * Tick the arena when it is building
     */
    @Override
    public void tickBuild() {
        Libraries.getInstance().getLogger().log(Level.INFO, "[" + getName() + "] Building!");
    }

    /**
     * Start waiting for players to join
     */
    @Override
    public void join() {
        updateState(ArenaState.JOIN);
        updateTimer(getConfiguration().getLobbyDuration());

        Libraries.getInstance().getLogger().log(Level.INFO, "[" + getName() + "] Started waiting for players!");
    }

    /**
     * Tick the arena when it is waiting for players
     */
    public void tickJoin() {
        Libraries.getInstance().getLogger().log(Level.INFO, "[" + getName() + "] Waiting players!");
    }

    /**
     * Start the warm up
     */
    @Override
    public void warmUp() {
        updateState(ArenaState.WARMUP);
        updateTimer(getConfiguration().getWarmUpDuration());

        Libraries.getInstance().getLogger().log(Level.INFO, "[" + getName() + "] Started the warm up!");
    }

    /**
     * Tick the arena when it is on warm up
     */
    public void tickWarmUp() {
        Libraries.getInstance().getLogger().log(Level.INFO, "[" + getName() + "] Warming up!");
    }

    /**
     * Start the game
     */
    @Override
    public void start() {
        updateState(ArenaState.START);
        updateTimer(getConfiguration().getGameDuration());

        Libraries.getInstance().getLogger().log(Level.INFO, "[" + getName() + "] Started the game!");
    }

    /**
     * Tick the arena when game has started
     */
    public void tickStart() {
        Libraries.getInstance().getLogger().log(Level.INFO, "[" + getName() + "] Game is running!");
    }

    /**
     * Finish the game
     */
    @Override
    public void finish() {
        updateState(ArenaState.FINISH);
        updateTimer(getConfiguration().getEndGameDuration());

        Libraries.getInstance().getLogger().log(Level.INFO, "[" + getName() + "] Finished the game!");
    }

    /**
     * Tick the arena when game has finished
     */
    public void tickFinish() {
        Libraries.getInstance().getLogger().log(Level.INFO, "[" + getName() + "] Game is finishing!");
    }

    /**
     * Stop the arena
     */
    @Override
    public void stop() {
        updateState(ArenaState.STOP);
        updateTimer(Integer.MAX_VALUE);

        Libraries.getInstance().getLogger().log(Level.INFO, "[" + getName() + "] Started stopping!");
    }

    /**
     * Tick the arena when stopped
     */
    public void tickStop() {
        Libraries.getInstance().getLogger().log(Level.INFO, "[" + getName() + "] Arena is stopping!");
    }

    /**
     * Update the arena game in the database
     */
    public void updateDatabase() {

    }
}
