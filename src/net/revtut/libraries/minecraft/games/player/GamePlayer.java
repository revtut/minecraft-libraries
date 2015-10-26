package net.revtut.libraries.minecraft.games.player;

import net.revtut.libraries.minecraft.games.GameAPI;
import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.classes.GameClass;
import net.revtut.libraries.minecraft.games.utils.Winner;
import net.revtut.libraries.minecraft.player.ServerPlayer;
import net.revtut.libraries.minecraft.text.Language;

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
     * Update the state of the player
     * @param state new state of the player
     */
    public void updateState(final PlayerState state) {
        this.state = state;
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