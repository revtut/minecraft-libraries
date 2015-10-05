package net.revtut.libraries.games;

import net.revtut.libraries.Libraries;
import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Game API.
 */
public class GameAPI {

    /**
     * Instance of GameAPI
     */
    private static GameAPI instance;

    /**
     * List with all players
     */
    private final List<PlayerData> players;

    /**
     * List with all game controllers
     */
    private final List<GameController> games;

    /**
     * Constructor of GameAPI
     */
    private GameAPI() {
        this.players = new ArrayList<>();
        this.games = new ArrayList<>();

        Bukkit.getPluginManager().registerEvents(new GameListener(this), Libraries.getInstance());

        // Run the tick task
        Bukkit.getScheduler().runTaskTimer(Libraries.getInstance(), () -> {
            for(GameController gameController : games)
                gameController.getArenas().stream()
                        .filter(arena -> arena.getSession() != null)
                        .forEach(arena -> arena.getSession().tick());
        }, 20L, 20L);
    }

    /**
     * Get the instance of game api
     * @return instance of game api
     */
    public static GameAPI getInstance() {
        if(instance == null)
            instance = new GameAPI();
        return instance;
    }

    /**
     * Register a game
     * @param plugin plugin of the game
     * @param worldsFolder folder where worlds are located
     * @return controller of the game
     */
    public GameController registerGame(final Plugin plugin, final File worldsFolder) {
        if(getGameController(plugin) != null) // Avoid registering multiple times the same game
            return null;

        final GameController gameController = new GameController(plugin, worldsFolder);
        games.add(gameController);
        return gameController;
    }

    /**
     * Unregister a game
     * @param plugin plugin of the game
     */
    public void unregisterGame(final Plugin plugin) {
        final GameController gameController = getGameController(plugin);
        if(gameController != null)
            games.remove(gameController);
    }

    /**
     * Get all the game controllers
     * @return game controllers
     */
    public List<GameController> getGameControllers() {
        return games;
    }

    /**
     * Get the game controller of a plugin
     * @param plugin plugin to get the game controller
     * @return game controller of the plugin
     */
    public GameController getGameController(final Plugin plugin) {
        for(final GameController gameController : games)
            if(gameController.getPlugin().equals(plugin))
                return gameController;
        return null;
    }

    /**
     * Get a random game controller
     * @return game controller
     */
    public GameController getRandomGame() {
        return games.get((int) (Math.random() * (games.size() - 1)));
    }

    /**
     * Get all the players on the server
     * @return players on the server
     */
    public List<PlayerData> getPlayers() {
        return players;
    }

    /**
     * Get the player data of a given player
     * @param uuid uuid of the player to get the player data
     * @return player data of the player
     */
    public PlayerData getPlayer(final UUID uuid) {
        for(final PlayerData player : players)
            if(player.getUuid().equals(uuid))
                return player;
        return null;
    }

    /**
     * Get the arena of a given player
     * @param uuid uuid of the player
     * @return arena of the player
     */
    public Arena getPlayerArena(final UUID uuid) {
        Arena arena = null;
        for(final GameController gameController : games) {
            arena = gameController.getPlayerArena(uuid);
            if(arena != null)
                break;
        }

        return arena;
    }

    /**
     * Add a player to the list
     * @param player player to be added
     */
    public void addPlayer(final PlayerData player) {
        players.add(player);
    }

    /**
     * Remove a player from the list
     * @param player player to be removed
     */
    public void removePlayer(final PlayerData player) {
        players.remove(player);
    }

    /**
     * Hide all server to a player and hide that player to the server
     * @param player player to execute that action
     */
    public void hideServer(final Player player) {
        for(final Player target : Bukkit.getOnlinePlayers()) {
            target.hidePlayer(player);
            player.hidePlayer(target);
        }
    }
}
