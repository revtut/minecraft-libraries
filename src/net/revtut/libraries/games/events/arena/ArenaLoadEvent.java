package net.revtut.libraries.games.events.arena;

import net.revtut.libraries.games.arena.Arena;

/**
 * Arena Load Event
 */
public class ArenaLoadEvent extends ArenaEvent {

    /**
     * Constructor of ArenaLoadEvent
     *
     * @param arena arena where the event occurred
     */
    public ArenaLoadEvent(Arena arena) {
        super(arena);
    }
}