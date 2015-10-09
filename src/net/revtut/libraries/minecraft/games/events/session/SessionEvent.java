package net.revtut.libraries.minecraft.games.events.session;

import net.revtut.libraries.minecraft.games.arena.session.GameSession;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Session Event
 */
public abstract class SessionEvent extends Event implements Cancellable {

    /**
     * Handlers list
     */
    private static final HandlerList handlers = new HandlerList();

    /**
     * Session where the event occurred
     */
    private final GameSession gameSession;

    /**
     * Flag to check if the event was cancelled
     */
    private boolean isCancelled;

    /**
     * Constructor of SessionEvent
     * @param gameSession session where the event occurred
     */
    public SessionEvent(final GameSession gameSession) {
        this.gameSession = gameSession;
        this.isCancelled = false;
    }

    /**
     * Get the session where the event occurred
     * @return session where the event occurred
     */
    public GameSession getSession() {
        return gameSession;
    }

    /**
     * Get the handlers of the event
     * @return handlers of the event
     */
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Check if the event is cancelled
     * @return true if event is cancelled, false otherwise
     */
    public boolean isCancelled() {
        return isCancelled;
    }

    /**
     * Set the event as cancelled or not
     * @param cancelled new value for cancelled
     */
    public void setCancelled(final boolean cancelled) {
        this.isCancelled = cancelled;
    }

    /**
     * Get the handlers of the event
     * @return handlers of the event
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
