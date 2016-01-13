package net.revtut.libraries.minecraft.bukkit.games.events.arena;

import net.revtut.libraries.minecraft.bukkit.games.arena.Arena;
import net.revtut.libraries.minecraft.bukkit.games.player.GamePlayer;
import org.bukkit.block.Block;

/**
 * Arena Block Place Event
 */
public class ArenaBlockPlaceEvent extends ArenaEvent {

    /**
     * Player that place the block
     */
    private final GamePlayer player;

    /**
     * Block that was placed
     */
    private final Block block;

    /**
     * Constructor of ArenaBlockPlaceEvent
     *
     * @param arena arena where the event occurred
     * @param player player that place the block
     * @param block block that was placed
     */
    public ArenaBlockPlaceEvent(final Arena arena, final GamePlayer player, final Block block) {
        super(arena);

        this.player = player;
        this.block = block;
    }

    /**
     * Get the player that place the block
     * @return player that place the block
     */
    public GamePlayer getPlayer() {
        return player;
    }

    /**
     * Get the block that was placed
     * @return block that was placed
     */
    public Block getBlock() {
        return block;
    }
}