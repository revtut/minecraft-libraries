package net.revtut.libraries.games.arena.types;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.arena.session.GameSession;
import net.revtut.libraries.games.player.PlayerData;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Arena Solo Object.
 * Arena where you don't have any teams free for all style.
 */
public class ArenaSolo extends Arena {

    /**
     * List with players in the arena
     */
    private List<PlayerData> players;

    /**
     * List with all spawn locations and death match locations
     */
    private List<Location> spawnLocations, deathMatchLocations;

    /**
     * Death location of the arena
     */
    private Location deathLocation;

    /**
     * Constructor of ArenaTeam
     * @param plugin plugin owner of the arena
     * @param worldsFolder folder where worlds are located
     */
    public ArenaSolo(JavaPlugin plugin, File worldsFolder) {
        super(plugin, worldsFolder);

        this.players = new ArrayList<>();
    }

    /**
     * Initialize the arena
     * @param arenaWorld world of the arena
     * @param lobbyLocation location of the lobby
     * @param spectatorLocation location of the spectator's spawn
     * @param corners corners of the arena
     * @param spawnLocations locations of the spawn
     * @param deathLocation location to spawn dead players
     * @param deathMatchLocations locations for the death match
     * @param gameSession session of the arena
     */
    public void init(World arenaWorld, Location lobbyLocation, Location spectatorLocation, Location[] corners, List<Location> spawnLocations, Location deathLocation, List<Location> deathMatchLocations, GameSession gameSession) {
        super.init(arenaWorld, lobbyLocation, spectatorLocation, corners, gameSession);
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
     * Check if the arena contains a given player by its UUID
     * @param uuid uuid of the player to be checked
     * @return true if contains, false otherwise
     */
    public boolean containsPlayer(UUID uuid) {
        for(PlayerData target : players)
            if(target.getUuid().equals(uuid))
                return true;
        return false;
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