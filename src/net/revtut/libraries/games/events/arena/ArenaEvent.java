package net.revtut.libraries.games.events.arena;

import net.revtut.libraries.games.arena.Arena;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Arena Event
 */
public abstract class ArenaEvent extends Event implements Cancellable {

    /**
     * Handlers list
     */
    private static final HandlerList handlers = new HandlerList();

    /**
     * Arena where the event occurred
     */
    private Arena arena;

    /**
     * Flag to check if the event was cancelled
     */
    private boolean isCancelled;

    /**
     * Constructor of PlayerEvent
     * @param arena arena where the event occurred
     */
    public ArenaEvent(Arena arena) {
        this.arena = arena;
        this.isCancelled = false;
    }

    /**
     * Get the arena where the event occurred
     * @return arena where the event occurred
     */
    public Arena getArena() {
        return arena;
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
    public void setCancelled(boolean cancelled) {
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
