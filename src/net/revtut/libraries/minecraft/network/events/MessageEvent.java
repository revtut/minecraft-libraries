package net.revtut.libraries.minecraft.network.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Message Event
 */
public abstract class MessageEvent extends Event {

    /**
     * Handlers list
     */
    private static final HandlerList handlers = new HandlerList();

    /**
     * Message of the event
     */
    private final String message;

    /**
     * Constructor of MessageEvent
     * @param message message of the event
     */
    public MessageEvent(final String message) {
        this.message = message;
    }

    /**
     * Get the mssage of the event
     * @return message of the event
     */
    protected String getMessage() {
        return message;
    }

    /**
     * Get the handlers of the event
     * @return handlers of the event
     */
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Get the handlers of the event
     * @return handlers of the event
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
