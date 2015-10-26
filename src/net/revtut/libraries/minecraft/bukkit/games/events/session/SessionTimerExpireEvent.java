package net.revtut.libraries.minecraft.bukkit.games.events.session;

import net.revtut.libraries.minecraft.bukkit.games.arena.session.GameSession;

/**
 * Session Timer Expire Event
 */
public class SessionTimerExpireEvent extends SessionEvent {

    /**
     * Constructor of SessionTimerExpireEvent
     * @param gameSession session where the event occurred
     */
    public SessionTimerExpireEvent(final GameSession gameSession) {
        super(gameSession);
    }
}