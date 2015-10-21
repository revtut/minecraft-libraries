package net.revtut.libraries.minecraft.games.events.player;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.player.GamePlayer;

/**
 * Player Talk Event
 */
public class PlayerTalkEvent extends PlayerEvent {

    /**
     * Message on joining the arena
     */
    private String formattedMessage;

    /**
     * Constructor of PlayerJoinArenaEvent
     * @param player player that joined the arena
     * @param arena arena that was joined
     * @param formattedMessage formatted message
     */
    public PlayerTalkEvent(final GamePlayer player, final Arena arena, final String formattedMessage) {
        super(player, arena);
        this.formattedMessage = formattedMessage;
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
