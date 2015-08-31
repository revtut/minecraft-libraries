package net.revtut.libraries.games.arena.session;

import net.revtut.libraries.Libraries;
import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.events.session.SessionSwitchStateEvent;
import net.revtut.libraries.games.events.session.SessionTickEvent;

/**
 * Game session
 */
public class GameSession {

    /**
     * Arena of the session
     */
    private Arena arena;

    /**
     * State of the arena
     */
    private GameState state;

    /**
     * Initial value of the timer and current timer (used for countdown, game time, etc)
     */
    private int initialTimer, currentTimer;

    /**
     * Minimum and maximum players on the game session
     */
    private int minPlayers, maxPlayers;

    /**
     * Constructor of GameSession
     * @param arena arena of the game session
     * @param minPlayers minimum players of the game
     * @param maxPlayers maximum players of the game
     */
    public GameSession(Arena arena, int minPlayers, int maxPlayers) {
        this.arena = arena;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.state = GameState.NONE;
        this.initialTimer = 0;
        this.currentTimer = 0;
    }

    /**
     * Get the arena of the game session
     * @return arena of the game session
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * Get the state of the game session
     * @return state of the game session
     */
    public GameState getState() {
        return state;
    }

    /**
     * Get the timer of the game session
     * @return timer of the game session
     */
    public int getTimer() {
        return currentTimer;
    }

    /**
     * Get the minimum players
     * @return minimum players
     */
    public int getMinPlayers() {
        return minPlayers;
    }

    /**
     * Get the maximum players
     * @return maximum players
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Update the game session state
     * @param state new value for the state
     * @param duration duration of the new state
     */
    public void updateState(GameState state, int duration) {
        // Call event
        SessionSwitchStateEvent event = new SessionSwitchStateEvent(this, this.state, state, duration);
        Libraries.getInstance().getServer().getPluginManager().callEvent(event);

        if(event.isCancelled())
            return;

        // Update state
        this.state = state;
        this.currentTimer = duration;
        this.initialTimer = duration;
    }

    /**
     * Reset the game session timer
     */
    public void resetTimer() {
        this.currentTimer = this.initialTimer;
    }

    /**
     * Tick the game session
     */
    public void tick() {
        // Call event
        SessionTickEvent event = new SessionTickEvent(this, --currentTimer);
        Libraries.getInstance().getServer().getPluginManager().callEvent(event);

        if(event.isCancelled())
            ++currentTimer;
    }
}