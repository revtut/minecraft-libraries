package net.revtut.libraries.minecraft.games.events.arena;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.player.GamePlayer;
import org.bukkit.block.Block;

/**
 * Arena Block Break Event
 */
public class ArenaBlockBreakEvent extends ArenaEvent {

    /**
     * Player that broke the block
     */
    private final GamePlayer player;

    /**
     * Block that was broken
     */
    private final Block block;

    /**
     * Constructor of ArenaBlockBreakEvent
     *
     * @param arena arena where the event occurred
     * @param player player that broke the block
     * @param block block that was broken
     */
    public ArenaBlockBreakEvent(final Arena arena, final GamePlayer player, final Block block) {
        super(arena);

        this.player = player;
        this.block = block;
    }

    /**
     * Get the player that broke the block
     * @return player that broke the block
     */
    public GamePlayer getPlayer() {
        return player;
    }

    /**
     * Get the block that was broken
     * @return block that was broken
     */
    public Block getBlock() {
        return block;
    }
}