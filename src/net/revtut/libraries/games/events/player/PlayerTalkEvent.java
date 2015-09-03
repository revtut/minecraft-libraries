package net.revtut.libraries.games.events.player;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.player.PlayerData;
import org.bukkit.event.HandlerList;

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
    public PlayerTalkEvent(PlayerData player, Arena arena, String formattedMessage) {
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
    public void setFormattedMessage(String formattedMessage) {
        this.formattedMessage = formattedMessage;
    }

    /**
     * Get the handlers of the event
     * @return handlers of the event
     */
    public HandlerList getHandlers() {
        return super.getHandlers();
    }
}
