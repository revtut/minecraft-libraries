package net.revtut.libraries.minigames.events.arena;

import net.revtut.libraries.minigames.arena.Arena;

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