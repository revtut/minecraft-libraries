package net.revtut.libraries.minecraft.games.events.player;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.player.GamePlayer;

/**
 * Player Talk Event
 */
public class PlayerTalkEvent extends PlayerEvent {

    /**
     * Message sent
     */
    private final String message;

    /**
     * Formatted message
     */
    private String formattedMessage;

    /**
     * Constructor of PlayerJoinArenaEvent
     * @param player player that joined the arena
     * @param arena arena that was joined
     */
    public PlayerTalkEvent(final GamePlayer player, final Arena arena, final String message) {
        super(player, arena);
        this.message = message;
        this.formattedMessage = "<" + player.getName() + "> " + message;
    }

    /**
     * Get the message that was sent
     * @return message that was sent
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get the formatted message
     * @return formatted message
     */
    public String getFormattedMessage() {
        return this.formattedMessage;
    }

    /**
     * Set the formatted message
     * @param formattedMessage new formatted message
     */
    public void setFormattedMessage(final String formattedMessage) {
        this.formattedMessage = formattedMessage;
    }

}
