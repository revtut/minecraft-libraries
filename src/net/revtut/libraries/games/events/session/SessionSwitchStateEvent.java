package net.revtut.libraries.games.events.session;

import net.revtut.libraries.games.arena.session.GameSession;
import net.revtut.libraries.games.arena.session.GameState;

/**
 * Session Switch State Event
 */
public class SessionSwitchStateEvent extends SessionEvent {

    /**
     * State before the switch
     */
    private GameState previousState;

    /**
     * State after the switch
     */
    private GameState nextState;

    /**
     * Duration of the next state
     */
    private int duration;

    /**
     * Constructor of SessionSwitchStateEvent
     * @param gameSession session where the event occurred
     * @param previousState state before the switch
     * @param nextState state after the switch
     */
    public SessionSwitchStateEvent(GameSession gameSession, GameState previousState, GameState nextState, int duration) {
        super(gameSession);
        this.previousState = previousState;
        this.nextState = nextState;
    }

    /**
     * Get the state before the switch
     * @return state before the switch
     */
    public GameState getPreviousState() {
        return previousState;
    }

    /**
     * Get the state after the switch
     * @return state after the switch
     */
    public GameState getNextState() {
        return nextState;
    }

    /**
     * Get the duration of the next state
     * @return duration of the next state
     */
    public int getDuration() { return duration; }

    /**
     * Set the duration of the next state
     * @param duration duration of the next state
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }
}