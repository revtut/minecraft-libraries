package net.revtut.libraries.games;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.arena.ArenaPreference;
import net.revtut.libraries.games.arena.types.ArenaSolo;
import net.revtut.libraries.games.arena.types.ArenaTeam;
import net.revtut.libraries.games.arena.types.ArenaType;
import net.revtut.libraries.games.player.PlayerData;
import net.revtut.libraries.utils.FilesAPI;
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
    private final Plugin plugin;

    /**
     * File where worlds are located
     */
    private final File worldsFolder;

    /**
     * List with all arenas
     */
    private final List<Arena> arenas;

    /**
     * Constructor of GameController
     * @param plugin plugin owner of the controller
     */
    public GameController(final Plugin plugin, final File worldsFolder) {
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
     * Get all the online players on the game
     * @return online players on the game
     */
    public List<PlayerData> getOnlinePlayers() {
        final List<PlayerData> players = new ArrayList<>();
        for(final Arena arena : arenas)
            players.addAll(arena.getAllPlayers());
        return players;
    }

    /**
     * Get a list with all available arenas
     * @return list with all available arenas
     */
    public List<Arena> getAvailableArenas() {
        final List<Arena> availableArenas = new ArrayList<>();
        for(final Arena arena : arenas) {
            if(!arena.canJoin(null))
                continue;

            availableArenas.add(arena);
        }

        return availableArenas;
    }

    /**
     * Get the best arena given the preference
     * @param preference preference for the arena
     * @return arena that best suits the preference
     */
    public Arena getAvailableArena(final ArenaPreference preference) {
        Arena arena = null;
        int comparator;

        // Initialize comparator variable
        switch (preference) {
            case MORE_PLAYERS:
            case MORE_REAMINING_TIME:
                comparator = Integer.MIN_VALUE;
                break;
            case LESS_PLAYERS:
            case LESS_REMAINING_TIME:
                comparator = Integer.MAX_VALUE;
                break;
            default:
                comparator = 0;
        }

        // Check all arenas
        for(final Arena targetArena : arenas) {
            if(!targetArena.canJoin(null))
                continue;

            switch (preference) {
                case MORE_PLAYERS:
                    if(targetArena.getAllPlayers().size() < comparator)
                        continue;

                    arena = targetArena;
                    comparator = targetArena.getAllPlayers().size();
                    break;
                case MORE_REAMINING_TIME:
                    if(targetArena.getSession().getTimer() < comparator)
                        continue;

                    arena = targetArena;
                    comparator = targetArena.getSession().getTimer();
                    break;
                case LESS_PLAYERS:
                    if(targetArena.getAllPlayers().size() > comparator)
                        continue;

                    arena = targetArena;
                    comparator = targetArena.getAllPlayers().size();
                case LESS_REMAINING_TIME:
                    if(targetArena.getSession().getTimer() > comparator)
                        continue;

                    arena = targetArena;
                    comparator = targetArena.getSession().getTimer();
                    break;
            }
        }

        return arena;
    }

    /**
     * Get the arena of a given player
     * @param uuid uuid of the player to get the arena
     * @return arena of the player
     */
    public Arena getPlayerArena(final UUID uuid) {
        for(final Arena arena : arenas)
            if(arena.containsPlayer(uuid))
                return arena;
        return null;
    }

    /**
     * Check if a arena is contained on this controller
     * @param arena arena to be checked
     * @return true if contains, false otherwise
     */
    public boolean hasArena(final Arena arena) {
        return arenas.contains(arena);
    }

    /**
     * Create a arena
     * @param type type of the arena
     * @return created arena
     */
    public Arena createArena(final ArenaType type) {
        final Arena arena;
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
    public void removeArena(final Arena arena) {
        arena.close();
        arenas.remove(arena);
    }

    /**
     * Load a random world
     * @param prefix prefix of the world name
     * @return loaded world
     */
    public World loadRandomWorld(final String prefix) {
        // Copy world folder
        final String[] listWorlds = getWorldsFolder().list();
        if (listWorlds == null)
            throw new IllegalStateException("List of worlds is null.");

        final int posWorld = (int) (Math.random() * (listWorlds.length - 1));
        final String sourcePath = new File(getWorldsFolder() + File.separator + listWorlds[posWorld]).getAbsolutePath();
        final String mapName = prefix + "_" + listWorlds[posWorld];
        final String targetPath = new File(System.getProperty("user.dir") + File.separator + mapName).getAbsolutePath();

        FilesAPI.copyDirectory(new File(sourcePath), new File(targetPath));

        // Load World
        final World world = WorldAPI.loadWorld(mapName);
        if(world == null)
            throw new IllegalStateException("Loaded world is null.");
        world.setAutoSave(false);
        return world;
    }
}