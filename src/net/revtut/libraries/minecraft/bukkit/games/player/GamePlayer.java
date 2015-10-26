package net.revtut.libraries.minecraft.bukkit.games.player;

import net.revtut.libraries.minecraft.bukkit.games.GameAPI;
import net.revtut.libraries.minecraft.bukkit.games.arena.Arena;
import net.revtut.libraries.minecraft.bukkit.games.classes.GameClass;
import net.revtut.libraries.minecraft.bukkit.games.utils.Winner;
import net.revtut.libraries.minecraft.common.player.ServerPlayer;
import net.revtut.libraries.minecraft.common.text.Language;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * GamePlayer Object
 */
public class GamePlayer extends ServerPlayer implements Winner {

    /**
     * Current arena of the player
     */
    private Arena currentArena;

    /**
     * State of the player
     */
    private PlayerState state;

    /**
     * Game class of the player
     */
    private GameClass gameClass;

    /**
     * Constructor of GamePlayer
     * @param uuid uuid of the owner player
     * @param name name of the player
     * @param locale locale of the player
     */
    public GamePlayer(final UUID uuid, final String name, final Language.Locale locale) {
        super(uuid, name, locale);
        this.currentArena = null;
        this.state = PlayerState.NOT_ASSIGNED;
        this.gameClass = null;

        GameAPI.getInstance().addPlayer(this);
    }

    /**
     * Get the current arena of the player
     * @return current arena of the player
     */
    public Arena getCurrentArena() {
        return currentArena;
    }

    /**
     * Get the state of the player
     * @return state of the player
     */
    public PlayerState getState() {
        return state;
    }

    /**
     * Get the game class of the player
     * @return game class of the player
     */
    public GameClass getGameClass() {
        return gameClass;
    }

    /**
     * Get the location of the player
     * @return location of the player
     */
    public Location getLocation() {
        final Player bukkitPlayer = Bukkit.getPlayer(getUuid());
        if(bukkitPlayer != null)
            return bukkitPlayer.getLocation();
        return null;
    }

    /**
     * Set the current arena of the player
     * @param arena current arena of the player
     */
    public void setCurrentArena(final Arena arena) {
        this.currentArena = arena;
    }

    /**
     * Set the game class of the player
     * @param gameClass game class of the player
     */
    public void setGameClass(final GameClass gameClass) {
        this.gameClass = gameClass;
    }

    /**
     * Set the game mode of the player
     * @param gameMode new game mode
     */
    public void setGameMode(final GameMode gameMode) {
        final Player bukkitPlayer = Bukkit.getPlayer(getUuid());
        if(bukkitPlayer != null)
            bukkitPlayer.setGameMode(gameMode);
    }

    /**
     * Update the state of the player
     * @param state new state of the player
     */
    public void updateState(final PlayerState state) {
        this.state = state;
    }

    /**
     * Teleport the game player to a location
     * @param location location to teleport to
     */
    public void teleport(final Location location) {
        final Player bukkitPlayer = Bukkit.getPlayer(getUuid());
        if(bukkitPlayer != null)
            bukkitPlayer.teleport(location);
    }

    /**
     * Send a message to the player
     * @param message message to be sent
     */
    public void sendMessage(final String message) {
        final Player bukkitPlayer = Bukkit.getPlayer(getUuid());
        if(bukkitPlayer != null)
            bukkitPlayer.sendMessage(message);
    }

    /**
     * Convert a player data to string
     * @return converted string
     */
    @Override
    public String toString() {
        return super.toString();
    }
}