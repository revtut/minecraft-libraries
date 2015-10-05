package net.revtut.libraries.games.events.player;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.player.PlayerData;

/**
 * Player Join Arena Event
 */
public class PlayerJoinArenaEvent extends PlayerEvent {

    /**
     * Message on joining the arena
     */
    private String joinMessage;

    /**
     * Constructor of PlayerJoinArenaEvent
     * @param player player that joined the arena
     * @param arena arena that was joined
     * @param joinMessage join message
     */
    public PlayerJoinArenaEvent(final PlayerData player, final Arena arena, final String joinMessage) {
        super(player, arena);
        this.joinMessage = joinMessage;
    }

    /**
     * Get the join message
     * @return join message
     */
    public String getJoinMessage() {
        return this.joinMessage;
    }

    /**
     * Set the join message
     * @param joinMessage new join message
     */
    public void setJoinMessage(final String joinMessage) {
        this.joinMessage = joinMessage;
    }

}
