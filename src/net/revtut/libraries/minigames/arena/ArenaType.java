package net.revtut.libraries.minigames.arena;

/**
 * Type of arenas
 */
public enum ArenaType {
    /**
     * Standard arena type. Multiple spawns allowed, death spawn, spectators spawn, countdown, etc
     */
    DEFAULT,

    /**
     * Join and play arena type. No countdowns, players can join and leave any moment
     */
    JOIN_PLAY
}
