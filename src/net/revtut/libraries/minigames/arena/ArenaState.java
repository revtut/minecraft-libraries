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
    LOBBY,

    /**
     * Warming up the game
     */
    WARMUP,

    /**
     * Game has started
     */
    START,

    /**
     * Death match has started
     */
    DEATHMATCH,

    /**
     * Game has finished
     */
    FINISH,

    /**
     * Arena has stopped
     */
    STOP,

    /**
     * Arena exists but it is not in any state
     */
    NONE;
}
