package net.revtut.libraries.games.player;

/**
 * Player State
 */
public enum PlayerState {

    /**
     * Player is alive
     */
    ALIVE,

    /**
     * Player is dead
     */
    DEAD,

    /**
     * Player is respawning
     */
    RESPAWN,

    /**
     * Player is spectating
     */
    SPECTATOR,

    /**
     * Player is not in any arena
     */
    NOT_ASSIGNED
}
