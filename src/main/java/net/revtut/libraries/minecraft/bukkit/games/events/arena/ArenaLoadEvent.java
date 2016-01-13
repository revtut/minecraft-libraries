package net.revtut.libraries.minecraft.bukkit.games.events.arena;

import net.revtut.libraries.minecraft.bukkit.games.arena.Arena;

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