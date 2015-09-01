package net.revtut.libraries.games.arena;

import net.revtut.libraries.games.player.PlayerData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Arena Manager
 */
public class ArenaManager {

    /**
     * Instance of ArenaManager
     */
    private static ArenaManager instance;

    /**
     * List with all arenas
     */
    private List<Arena> arenas;

    /**
     * Constructor of ArenaManager
     */
    private ArenaManager() {
        arenas = new ArrayList<>();
    }

    /**
     * Get the instance of arena manager
     * @return instance of the arena manager
     */
    public static ArenaManager getInstance() {
        if(instance == null)
            instance = new ArenaManager();
        return instance;
    }

    /**
     * Get the arena of a given player
     * @param player player to get the arena
     * @return arena of the player
     */
    public Arena getArena(PlayerData player) {
        return getArena(player.getUuid());
    }

    /**
     * Get the arena of a given player
     * @param uuid uuid of the player to get the arena
     * @return arena of the player
     */
    public Arena getArena(UUID uuid) {
        for(Arena arena : arenas)
            if(arena.containsPlayer(uuid))
                return arena;
        return null;
    }

    /**
     * Add a arena to the list
     * @param arena arena to be added
     */
    public void addArena(Arena arena) {
        arenas.add(arena);
    }

    /**
     * Remove a arena from the list
     * @param arena arena to be removed
     */
    public void removeArena(Arena arena) {
        arenas.remove(arena);
    }
}