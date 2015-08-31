package net.revtut.libraries.games.events.session;

import net.revtut.libraries.games.arena.session.GameSession;

/**
 * Session Tick Event
 */
public class SessionTickEvent extends SessionEvent {

    /**
     * Time until change state
     */
    private int time;

    /**
     * Constructor of SessionSwitchStateEvent
     * @param gameSession Session where the event occurred
     * @param time time until game session change state
     */
    public SessionTickEvent(GameSession gameSession, int time) {
        super(gameSession);
        this.time = time;
    }

    /**
     * Get the time until game session change state
     * @return duration of the next state
     */
    public int getTime() { return time; }
}