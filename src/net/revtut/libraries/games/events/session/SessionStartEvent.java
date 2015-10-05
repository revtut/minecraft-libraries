package net.revtut.libraries.games.events.session;

import net.revtut.libraries.games.arena.session.GameSession;

/**
 * Session Start Event
 */
public class SessionStartEvent extends SessionEvent {

    /**
     * Constructor of SessionStartEvent
     * @param gameSession session where the event occurred
     */
    public SessionStartEvent(final GameSession gameSession) {
        super(gameSession);
    }
}