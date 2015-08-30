package net.revtut.libraries.minigames.events.arena;

import net.revtut.libraries.minigames.arena.Arena;
import net.revtut.libraries.minigames.arena.ArenaState;

/**
 * Arena Switch State Event
 */
public class ArenaSwitchStateEvent extends ArenaEvent {

    /**
     * State before the switch
     */
    private ArenaState previousState;

    /**
     * State after the switch
     */
    private ArenaState nextState;

    /**
     * Duration of the next state
     */
    private int duration;

    /**
     * Constructor of ArenaSwitchStateEvent
     * Arena where the event occurred
     * @param previousState state before the switch
     * @param nextState state after the switch
     */
    public ArenaSwitchStateEvent(Arena arena, ArenaState previousState, ArenaState nextState, int duration) {
        super(arena);
        this.previousState = previousState;
        this.nextState = nextState;
    }

    /**
     * Get the state before the switch
     * @return state before the switch
     */
    public ArenaState getPreviousState() {
        return previousState;
    }

    /**
     * Get the state after the switch
     * @return state after the switch
     */
    public ArenaState getNextState() {
        return nextState;
    }

    /**
     * Get the duration of the next state
     * @return duration of the next state
     */
    public int getDuration() { return duration; }

    /**
     * Set the duration of the next state
     * @param duration duration of the next state
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }
}