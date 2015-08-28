package net.revtut.libraries.minigames.arena;

/**
 * Arena States
 */
public enum ArenaState {
    /**
     * Building the arena
     */
    BUILD,

    /**
     * Waiting for players to join the arena
     */
    JOIN,

    /**
     * Warming up the game
     */
    WARMUP,

    /**
     * Game has started
     */
    START,

    /**
     * Game has finished
     */
    FINISH,

    /**
     * Arena has stopped
     */
    STOP
}
