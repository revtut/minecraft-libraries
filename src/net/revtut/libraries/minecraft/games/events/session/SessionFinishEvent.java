package net.revtut.libraries.minecraft.games.events.session;

import net.revtut.libraries.minecraft.games.arena.session.GameSession;

/**
 * Session Finish Event
 */
public class SessionFinishEvent extends SessionEvent {

    /**
     * Constructor of SessionFinishEvent
     * @param gameSession session where the event occurred
     */
    public SessionFinishEvent(final GameSession gameSession) {
        super(gameSession);
    }
}