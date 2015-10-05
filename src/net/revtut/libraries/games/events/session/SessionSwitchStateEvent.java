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
    private final GameState previousState;

    /**
     * State after the switch
     */
    private final GameState nextState;

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
    public SessionSwitchStateEvent(final GameSession gameSession, final GameState previousState, final GameState nextState, final int duration) {
        super(gameSession);
        this.previousState = previousState;
        this.nextState = nextState;
        this.duration = duration;
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
    public void setDuration(final int duration) {
        this.duration = duration;
    }
}