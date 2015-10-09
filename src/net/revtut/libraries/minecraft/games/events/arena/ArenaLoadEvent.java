package net.revtut.libraries.minecraft.games.events.arena;

import net.revtut.libraries.minecraft.games.arena.Arena;

/**
 * Arena Load Event
 */
public class ArenaLoadEvent extends ArenaEvent {

    /**
     * Constructor of ArenaLoadEvent
     *
     * @param arena arena where the event occurred
     */
    public ArenaLoadEvent(final Arena arena) {
        super(arena);
    }
}