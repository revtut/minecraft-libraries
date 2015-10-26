package net.revtut.libraries.minecraft.bukkit.games.events.player;

import net.revtut.libraries.minecraft.bukkit.games.arena.Arena;
import net.revtut.libraries.minecraft.bukkit.games.player.GamePlayer;

/**
 * Player Spectate Arena Event
 */
public class PlayerSpectateArenaEvent extends PlayerEvent {

    /**
     * Message on joining the arena
     */
    private String joinMessage;

    /**
     * Constructor of PlayerSpectateArenaEvent
     * @param player player that spectated the arena
     * @param arena arena that was spectated
     * @param joinMessage join message
     */
    public PlayerSpectateArenaEvent(final GamePlayer player, final Arena arena, final String joinMessage) {
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
