package net.revtut.libraries.games.arena.session;

/**
 * Game States
 */
public enum GameState {
    /**
     * Building the arena
     */
    BUILD,

    /**
     * Waiting for players to join the session
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
     * Session exists but it is not in any state
     */
    NONE
}
