package net.revtut.libraries.minecraft.bukkit.games;

import net.revtut.libraries.Libraries;
import net.revtut.libraries.minecraft.bukkit.games.arena.Arena;
import net.revtut.libraries.minecraft.bukkit.games.arena.ArenaPreference;
import net.revtut.libraries.minecraft.bukkit.games.arena.session.GameState;
import net.revtut.libraries.minecraft.bukkit.games.player.GamePlayer;
import net.revtut.libraries.minecraft.bukkit.games.player.PlayerState;
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
    private final List<GamePlayer> players;

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
                new ArrayList<>(gameController.getArenas()).stream()
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
     * Get the game controller of a arena
     * @param arena arena to get the game controller
     * @return game controller of the arena
     */
    public GameController getGameController(final Arena arena) {
        for(final GameController gameController : games)
            if(gameController.hasArena(arena))
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
    public List<GamePlayer> getPlayers() {
        return players;
    }

    /**
     * Get the player data of a given player
     * @param uuid uuid of the player to get the player data
     * @return player data of the player
     */
    public GamePlayer getPlayer(final UUID uuid) {
        for(final GamePlayer player : players)
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
     * Get the arena by its world name
     * @param worldName world name of the arena world
     * @return arena with that world name
     */
    public Arena getArena(final String worldName) {
        Arena arena = null;
        for(final GameController gameController : games) {
            arena = gameController.getArena(worldName);
            if(arena != null)
                break;
        }

        return arena;
    }

    /**
     * Add a player to the list
     * @param player player to be added
     */
    public void addPlayer(final GamePlayer player) {
        players.add(player);
    }

    /**
     * Remove a player from the list
     * @param player player to be removed
     */
    public void removePlayer(final GamePlayer player) {
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

    /**
     * Join a random game
     * @param player player to join a random game
     */
    public void joinRandomGame(final GamePlayer player) {
        // Hide every player
        final Player bukkitPlayer = Bukkit.getPlayer(player.getUuid());
        if(bukkitPlayer != null)
            hideServer(bukkitPlayer);

        // Join random game
        final GameController gameController = getRandomGame();
        joinGame(player, gameController);

    }

    /**
     * Join a game
     * @param player player to join the game
     * @param gameController game to be joined
     */
    public void joinGame(final GamePlayer player, final GameController gameController) {
        final Arena arena = gameController.getAvailableArena(ArenaPreference.MORE_PLAYERS);

        // No arena available or not allowed to join the arena
        if(arena == null || !arena.join(player)) {
            final Player bukkitPlayer = Bukkit.getPlayer(player.getUuid());
            if(bukkitPlayer != null)
                Libraries.getInstance().getNetwork().connectPlayer(bukkitPlayer, "hub");
            return;
        }

        // Create more arenas if needed
        if(gameController.getAvailableArenas().size() <= 1)
            gameController.createArena(arena.getType());
    }

    /**
     * Leave a game arena
     * @param player player to leave
     * @param arena arena to be left
     */
    public void leaveGame(final GamePlayer player, final Arena arena) {
        arena.leave(player);

        // Delete arena if needed and join randomly all the remaining players
        if(arena.getSession() != null && arena.getSession().getState() != GameState.LOBBY) {
            if(arena.getPlayers(PlayerState.ALIVE).size() <= 1) {
                for(final GamePlayer target : new ArrayList<>(arena.getAllPlayers())) {
                    if(target == player)
                        continue;
                    final Player targetBukkitPlayer = Bukkit.getPlayer(target.getUuid());
                    if(targetBukkitPlayer == null)
                        continue;
                    arena.leave(target);

                    // Join game
                    joinRandomGame(target);
                }
                final GameController arenaController = GameAPI.getInstance().getGameController(arena);
                if(arenaController == null)
                    return;
                arenaController.removeArena(arena);
            }
        }
    }
}
