package net.revtut.libraries.minecraft.bukkit.games.events.session;

import net.revtut.libraries.minecraft.bukkit.games.arena.session.GameSession;

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