package net.revtut.libraries.games.events.session;

import net.revtut.libraries.games.arena.session.GameSession;

/**
 * Session Timer Tick Event
 */
public class SessionTimerTickEvent extends SessionEvent {

    /**
     * Time until timer expire
     */
    private int time;

    /**
     * Constructor of SessionTimerTickEvent
     * @param gameSession session where the event occurred
     * @param time time until game session change state
     */
    public SessionTimerTickEvent(GameSession gameSession, int time) {
        super(gameSession);
        this.time = time;
    }

    /**
     * Get the time until the timer expire
     * @return timer until the timer expire
     */
    public int getTime() { return time; }
}