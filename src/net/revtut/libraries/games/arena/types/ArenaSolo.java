package net.revtut.libraries.games.arena.types;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.player.PlayerData;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Arena Solo Object.
 * Arena where you don't have any teams free for all style.
 */
public abstract class ArenaSolo extends Arena {

    /**
     * List with players in the arena
     */
    private List<PlayerData> players;

    /**
     * List with all spawn locations
     */
    private List<Location> spawnLocations;

    /**
     * Death location of the arena
     */
    private Location deathLocation;

    /**
     * List with all death match spawn locations
     */
    private List<Location> deathMatchLocations;

    /**
     * Constructor of ArenaTeam
     * @param plugin plugin owner of the arena
     */
    public ArenaSolo(JavaPlugin plugin) {
        super(plugin);

        this.players = new ArrayList<>();
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
     * @param deathLocation location to spawn dead players
     * @param deathMatchLocations locations for the death match
     */
    public void init(File worldsFolder, int minPlayers, int maxPlayers, World arenaWorld, Location lobbyLocation, Location spectatorLocation, List<Location> spawnLocations, Location deathLocation, List<Location> deathMatchLocations) {
        super.init(worldsFolder, minPlayers, maxPlayers, arenaWorld, lobbyLocation, spectatorLocation);
        this.spawnLocations = spawnLocations;
        this.deathLocation = deathLocation;
        this.deathMatchLocations = deathMatchLocations;
    }

    /**
     * Get the spawn locations
     * @return spawn locations
     */
    public List<Location> getSpawnLocations() {
        return spawnLocations;
    }

    /**
     * Get the death location
     * @return death location
     */
    public Location getDeathLocation() {
        return deathLocation;
    }

    /**
     * Get the death match locations
     * @return death match locations
     */
    public List<Location> getDeathMatchLocations() {
        return deathMatchLocations;
    }

    /**
     * Get all the players on the arena
     * @return players on the arena
     */
    public List<PlayerData> getAllPlayers() {
        return players;
    }

    /**
     * Make a player join the arena
     * @param player player to join
     * @return true if has joined, false otherwise
     */
    @Override
    public boolean join(PlayerData player) {
        if(!super.join(player))
            return false;

        players.add(player);

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

        players.remove(player);

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

        players.add(player);

        return true;
    }
}