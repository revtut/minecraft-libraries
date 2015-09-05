package net.revtut.libraries.games;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.arena.types.ArenaSolo;
import net.revtut.libraries.games.arena.types.ArenaTeam;
import net.revtut.libraries.games.arena.types.ArenaType;
import net.revtut.libraries.utils.WorldAPI;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Controller of game per plugin
 */
public class GameController {

    /**
     * Plugin owner of the controller
     */
    private Plugin plugin;

    /**
     * File where worlds are located
     */
    private File worldsFolder;

    /**
     * List with all arenas
     */
    private List<Arena> arenas;

    /**
     * Constructor of GameController
     * @param plugin plugin owner of the controller
     */
    public GameController(Plugin plugin, File worldsFolder) {
        this.plugin = plugin;
        this.worldsFolder = worldsFolder;
        this.arenas = new ArrayList<>();
    }

    /**
     * Get the plugin owner of the controller
     * @return plugin owner of the controller
     */
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * Get the folder where worlds are located
     * @return folder where worlds are located
     */
    public File getWorldsFolder() {
        return worldsFolder;
    }

    /**
     * Get all the arenas
     * @return all the arenas
     */
    public List<Arena> getArenas() {
        return arenas;
    }

    /**
     * Get the arena of a given player
     * @param uuid uuid of the player to get the arena
     * @return arena of the player
     */
    public Arena getPlayerArena(UUID uuid) {
        for(Arena arena : arenas)
            if(arena.containsPlayer(uuid))
                return arena;
        return null;
    }

    /**
     * Check if a arena is contained on this controller
     * @param arena arena to be checked
     * @return true if contains, false otherwise
     */
    public boolean hasArena(Arena arena) {
        return arenas.contains(arena);
    }

    /**
     * Create a arena
     * @param type type of the arena
     * @return created arena
     */
    public Arena createArena(ArenaType type) {
        Arena arena;
        switch (type) {
            case SOLO:
                arena = new ArenaSolo(plugin.getName());
                break;
            case TEAM:
                arena = new ArenaTeam(plugin.getName());
                break;
            default:
                return null;
        }

        arenas.add(arena);
        return arena;
    }

    /**
     * Remove a arena
     * @param arena arena to be removed
     */
    public void removeArena(Arena arena) {
        arena.close();
        arenas.remove(arena);
    }

    /**
     * Load a random world
     * @param prefix prefix of the world name
     * @return loaded world
     */
    public World loadRandomWorld(String prefix) {
        // Copy world folder
        String[] listWorlds = getWorldsFolder().list();
        if (listWorlds == null)
            throw new IllegalStateException("List of worlds is null.");

        int posWorld = (int) (Math.random() * listWorlds.length);
        final String sourcePath = new File(getWorldsFolder() + File.separator + listWorlds[posWorld]).getAbsolutePath();
        final String mapName = prefix + "_" + listWorlds[posWorld];
        final String targetPath = new File(System.getProperty("user.dir") + File.separator + mapName).getAbsolutePath();

        WorldAPI.copyDirectory(new File(sourcePath), new File(targetPath));

        // Load World
        World world = WorldAPI.loadWorldAsync(mapName);
        if(world == null)
            throw new IllegalStateException("Loaded world is null.");
        world.setAutoSave(false);
        return world;
    }
}