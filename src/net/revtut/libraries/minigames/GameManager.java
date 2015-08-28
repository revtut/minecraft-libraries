package net.revtut.libraries.minigames;

import net.revtut.libraries.Libraries;
import net.revtut.libraries.minigames.arena.Arena;
import net.revtut.libraries.minigames.arena.ArenaType;
import net.revtut.libraries.minigames.arena.types.ArenaTeam;
import net.revtut.libraries.minigames.player.PlayerData;
import net.revtut.libraries.minigames.player.PlayerState;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by User on 27/08/2015.
 */
public class GameManager {

    /**
     * Instance of GameManager
     */
    private static GameManager instance = new GameManager();

    /**
     * List with all arenas on the server
     */
    private List<Arena> arenas;

    /**
     * List with all players on the server
     */
    private List<PlayerData> players;

    /**
     * Constructor of GameManager
     */
    private GameManager() {
        this.arenas = new ArrayList<>();
        this.players = new ArrayList<>();
    }

    /**
     * Get the GameManager instance
     * @return instance of GameManager
     */
    public static GameManager getInstance() {
        return instance;
    }

    /**
     * Get all arenas on the server
     * @return all arenas
     */
    public List<Arena> getArenas() {
        return new ArrayList<>(arenas);
    }

    /**
     * Get a arena by its id
     * @param id id of the arena
     * @return arena with the given id
     */
    public Arena getArena(int id) {
        for(Arena arena : arenas)
            if(id == arena.getId())
                return arena;
        return null;
    }

    /**
     * Get the available arenas to be joined
     * @return available arenas to be joined
     */
    public List<Arena> getAvailableArenas() {
        List<Arena> availableArenas = new ArrayList<>();
        for(Arena arena : arenas)
            if(arena.canJoin(null))
                availableArenas.add(arena);
        return availableArenas;
    }

    /**
     * Get all the players on the server
     * @return players on the server
     */
    public List<PlayerData> getOnlinePlayers() {
        return new ArrayList<>(players);
    }

    /**
     * Get a player by its UUID
     * @param player UUID of the player
     * @return player with that UUID
     */
    public PlayerData getOnlinePlayer(UUID player) {
        for(PlayerData target : players)
            if(player.equals(target.getUuid()))
                return target;
        return null;
    }

    /**
     * Count the number of available arenas to be joined
     * @return number of available arenas
     */
    public int numberAvailableArenas() {
        return getAvailableArenas().size();
    }

    /**
     * Create a new arena
     * @param arenaType type of the arena
     * @param worldsFolder folder where worlds are located
     */
    private void createArena(ArenaType arenaType, File worldsFolder) {
        Arena arena = new ArenaTeam(Libraries.getInstance());
        arena.stop();
    }

    /**
     * Join a random arena
     * @param player player to join
     * @return true if successfully joined, false otherwise
     */
    public boolean join(PlayerData player) {
        Validate.notNull(player, "Player cannot be null on join.");

        // Find a arena to the player
        Arena targetArena = null;
        for(Arena arena : arenas) {
            if (!arena.canJoin(player)) {
                continue;
            } else {
                targetArena = arena;
                break;
            }
        }
        Validate.notNull(targetArena, "Target arena cannot be null on join.");

        return join(player, targetArena);
    }

    /**
     * Join a given arena
     * @param player player to join
     * @param arena arena to be joined
     * @return true if successfully joined, false otherwise
     */
    public boolean join(PlayerData player, Arena arena) {

        // Get the Bukkit player
        Player bukkitPlayer = Bukkit.getPlayer(player.getUuid());
        if(bukkitPlayer == null)
            return false;

        player.updateState(PlayerState.ALIVE);

        bukkitPlayer.teleport(arena.getLobbyLocation());

        // Add to the arena
        arena.join(player);
        return true;
    }

    /**
     * Leave the arena
     * @param player player to leave
     * @return true if successfully left, false otherwise
     */
    public boolean leave(PlayerData player) {
        Validate.notNull(player, "Player cannot be null on leave.");

        Arena arena = player.getCurrentArena();
        if(arena == null)
            return false;

        player.updateState(PlayerState.ALIVE);
        arena.leave(player);

        return true;
    }

    /**
     * Spectate a arena
     * @param player player to spectate
     * @param arena arena to spectate
     * @return
     */
    public boolean spectate(PlayerData player, Arena arena) {
        Validate.notNull(player, "Player cannot be null on spectate.");
        Validate.notNull(arena, "Arena to spectate cannot be null.");

        // Get the Bukkit player
        Player bukkitPlayer = Bukkit.getPlayer(player.getUuid());
        if(bukkitPlayer == null)
            return false;

        player.updateState(PlayerState.SPECTATOR);

        bukkitPlayer.teleport(arena.getSpectatorLocation());

        arena.spectate(player);

        return true;
    }
}
