package net.revtut.libraries.minigames.arena;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Locations of the Arena
 */
public class ArenaLocation {

    /**
     * World where the game is being played (we assume lobby world is different that the game world)
     */
    private World gameWorld;

    /**
     * Lobby location
     */
    private Location lobbyLocation;

    /**
     * Death spawn location
     */
    private Location deathLocation;

    /**
     * Spectator spawn location
     */
    private Location spectatorLocation;

    /**
     * Player spawn locations
     */
    private List<Location> spawnLocations;

    /**
     * Constructor of ArenaLocation
     * @param lobbyLocation lobby location of the arena
     * @param deathLocation death location of the arena
     * @param spectatorLocation spectator location of the arena
     * @param spawnLocations spawn locations of the arena
     */
    public ArenaLocation(Location lobbyLocation, Location deathLocation, Location spectatorLocation, List<Location> spawnLocations) {
        this.lobbyLocation = lobbyLocation;
        this.deathLocation = deathLocation;
        this.spectatorLocation = spectatorLocation;
        this.spawnLocations = spawnLocations;
    }

    /**
     * Get the game world of the arena location
     * @return game world
     */
    public World getGameWorld() {
        return gameWorld;
    }

    /**
     * Get the lobby location of the arena
     * @return lobby location of the arena
     */
    public Location getLobbyLocation() {
        return lobbyLocation.clone();
    }

    /**
     * Get the death location of the arena
     * @return death location of the arena
     */
    public Location getDeathLocation() {
        return deathLocation.clone();
    }

    /**
     * Get the spectator location of the arena
     * @return spectator location of the arena
     */
    public Location getSpectatorLocation() {
        return spectatorLocation;
    }

    /**
     * Get the spawn locations of the arena
     * @return spawn locations of the arena
     */
    public List<Location> getSpawnLocations() {
        return new ArrayList<>(spawnLocations);
    }

    /**
     * Set the game world of the arena
     * @param gameWorld game world of the arena
     */
    public void setGameWorld(World gameWorld) {
        this.gameWorld = gameWorld;
    }

    /**
     * Set the lobby location of the arena
     * @param lobbyLocation lobby location of the arena
     */
    public void setLobbyLocation(Location lobbyLocation) {
        this.lobbyLocation = lobbyLocation;
    }

    /**
     * Set the death location of the arena
     * @param deathLocation death location of the arena
     */
    public void setDeathLocation(Location deathLocation) {
        this.deathLocation = deathLocation;
    }

    /**
     * Set the spectator location of the arena
     * @param spectatorLocation spectator location of the arena
     */
    public void setSpectatorLocation(Location spectatorLocation) {
        this.spectatorLocation = spectatorLocation;
    }

    /**
     * Set the spawn locations of the arena
     * @param spawnLocations spawn locations of the arena
     */
    public void setSpawnLocations(List<Location> spawnLocations) {
        this.spawnLocations = spawnLocations;
    }
}