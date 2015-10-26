package net.revtut.libraries.minecraft.bukkit.network.events;

/**
 * Message Received Event
 */
public class MessageReceivedEvent extends MessageEvent {

    /**
     * Channel of the message
     */
    private final String channel;

    /**
     * Flag to control if the message was accepted or not
     */
    private final boolean accepted;

    /**
     * Constructor of MessageReceivedEvent
     * @param message message received
     * @param channel channel of the message
     */
    public MessageReceivedEvent(final String message, final String channel) {
        super(message);
        this.channel = channel;
        this.accepted = false;
    }

    /**
     * Get the channel of the message
     * @return channel of the message
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Accept the message
     * @return received message
     */
    public String accept() {
        return getMessage();
    }

    /**
     * Check if the message is accepted
     * @return true if message is accepted
     */
    public boolean isAccepted() {
        return accepted;
    }
}
