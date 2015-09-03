package net.revtut.libraries.games.events.session;

import net.revtut.libraries.games.arena.session.GameSession;

/**
 * Session Timer Expire Event
 */
public class SessionTimerExpireEvent extends SessionEvent {

    /**
     * Constructor of SessionTimerExpireEvent
     * @param gameSession session where the event occurred
     */
    public SessionTimerExpireEvent(GameSession gameSession) {
        super(gameSession);
    }
}