package net.revtut.libraries.games.arena.types;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.arena.session.GameSession;
import net.revtut.libraries.games.player.PlayerData;
import org.bukkit.Location;
import org.bukkit.World;

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
    private final List<PlayerData> players;

    /**
     * List with all spawn locations and death match locations
     */
    private List<Location> spawnLocations, deathMatchLocations;

    /**
     * Death location of the arena
     */
    private Location deadLocations, deadDeathMatchLocation;

    /**
     * Constructor of ArenaTeam
     * @param name name of the arena
     */
    public ArenaSolo(final String name) {
        super(name);

        this.players = new ArrayList<>();
    }

    /**
     * Initialize the arena
     * @param lobbyLocation location of the lobby
     * @param gameSession session of the arena
     */
    public void initArena(final Location lobbyLocation, final GameSession gameSession) {
        super.initArena(lobbyLocation, gameSession);
    }

    /**
     * Initialize the arena world
     * @param arenaWorld world of the arena
     * @param spectatorLocation location of the spectator's spawn
     * @param spectatorDeathMatchLocation location of the spectator's spawn on death match
     * @param corners corners of the arena
     * @param cornersDeathMatch corners of the death match arena
     * @param spawnLocations locations of the spawn
     * @param deadLocations location to spawn dead players
     * @param deadDeathMatchLocation locations to spawn dead players on death match
     * @param deathMatchLocations locations for the death match
     */
    public void initWorld(final World arenaWorld, final Location spectatorLocation, final Location spectatorDeathMatchLocation,
                           final Location[] corners, final Location[] cornersDeathMatch, final List<Location> spawnLocations, final Location deadLocations,
                          final Location deadDeathMatchLocation, final List<Location> deathMatchLocations) {
        super.initWorld(arenaWorld, spectatorLocation, spectatorDeathMatchLocation, corners, cornersDeathMatch);
        this.spawnLocations = spawnLocations;
        this.deadLocations = deadLocations;
        this.deadDeathMatchLocation = deadDeathMatchLocation;
        this.deathMatchLocations = deathMatchLocations;
    }

    /**
     * Get the type of the arena
     * @return type of the arena
     */
    public ArenaType getType() {
        return ArenaType.SOLO;
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
    public Location getDeadLocation() {
        return deadLocations;
    }

    /**
     * Get the death location of death match
     * @return death location of death match
     */
    public Location getDeadDeathMatchLocation() {
        return deadDeathMatchLocation;
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
    public boolean containsPlayer(final UUID uuid) {
        for(final PlayerData target : players)
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
    public boolean join(final PlayerData player) {
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
    public boolean leave(final PlayerData player) {
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
    public boolean spectate(final PlayerData player) {
        if(!super.spectate(player))
            return false;

        players.add(player);

        return true;
    }
}