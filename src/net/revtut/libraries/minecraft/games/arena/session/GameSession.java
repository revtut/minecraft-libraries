package net.revtut.libraries.minecraft.games.arena.session;

import net.revtut.libraries.generic.structures.Pair;
import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.events.session.*;
import net.revtut.libraries.minecraft.games.player.GamePlayer;
import net.revtut.libraries.minecraft.games.player.PlayerState;
import net.revtut.libraries.minecraft.games.utils.Winner;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Game session
 */
public class GameSession {

    /**
     * Arena of the session
     */
    private final Arena arena;

    /**
     * State of the arena
     */
    private GameState state;

    /**
     * Initial value of the timer and current timer (used for countdown, game time, etc)
     */
    private int initialTimer, currentTimer;

    /**
     * Minimum and maximum players on the game session
     */
    private final int minPlayers, maxPlayers;

    /**
     * Winner of the game session
     */
    private Winner winner;

    /**
     * Start and end date of the session
     */
    private Date startDate, endDate;

    /**
     * List with all initial and death match players
     */
    private final List<UUID> initialPlayers, deathMatchPlayers;

    /**
     * Events and chat log
     */
    private final List<Pair<String, Date>> eventsLog, chatLog;

    /**
     * Constructor of GameSession
     * @param arena arena of the game session
     * @param minPlayers minimum players of the game
     * @param maxPlayers maximum players of the game
     */
    public GameSession(final Arena arena, final int minPlayers, final int maxPlayers) {
        this.arena = arena;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.state = GameState.NONE;
        this.initialTimer = 0;
        this.currentTimer = 0;
        this.winner = null;
        this.initialPlayers = new ArrayList<>();
        this.deathMatchPlayers = new ArrayList<>();
        this.eventsLog = new ArrayList<>();
        this.chatLog = new ArrayList<>();
    }

    /**
     * Get the arena of the game session
     * @return arena of the game session
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * Get the state of the game session
     * @return state of the game session
     */
    public GameState getState() {
        return state;
    }

    /**
     * Get the timer of the game session
     * @return timer of the game session
     */
    public int getTimer() {
        return currentTimer;
    }

    /**
     * Get the minimum players
     * @return minimum players
     */
    public int getMinPlayers() {
        return minPlayers;
    }

    /**
     * Get the maximum players
     * @return maximum players
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Get the winner of the game
     * @return winner of the game
     */
    public Winner getWinner() {
        return winner;
    }

    /**
     * Get the start date of the game
     * @return start date of the game
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Get the end date of the game
     * @return end date of the game
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Get the initial players of the game
     * @return initial players of the game
     */
    public List<UUID> getInitialPlayers() {
        return initialPlayers;
    }

    /**
     * Get the death match players of the game
     * @return death match players of the game
     */
    public List<UUID> getDeathMatchPlayers() {
        return deathMatchPlayers;
    }

    /**
     * Get the events log of the game
     * @return events log of the game
     */
    public List<Pair<String, Date>> getEventsLog() {
        return eventsLog;
    }

    /**
     * Get the chat log of the game
     * @return chat log of the game
     */
    public List<Pair<String, Date>> getChatLog() {
        return chatLog;
    }

    /**
     * Set the winner of the game
     * @param winner winner of the game
     */
    public void setWinner(final Winner winner) {
        this.winner = winner;
    }

    /**
     * Add a event to the log
     * @param event event to be added
     * @param date date of the event
     */
    public void addEvent(final String event, final Date date) {
        this.eventsLog.add(new Pair<>(event, date));
    }

    /**
     * Add a chat message to the log
     * @param message message to be added
     * @param date date of the message
     */
    public void addChatMessage(final String message, final Date date) {
        this.chatLog.add(new Pair<>(message, date));
    }

    /**
     * Update the game session state
     * @param state new value for the state
     * @param duration duration of the new state
     */
    public void updateState(final GameState state, final int duration) {
        // Call event
        SessionEvent event = new SessionSwitchStateEvent(this, this.state, state, duration);
        Bukkit.getPluginManager().callEvent(event);

        if(event.isCancelled())
            return;

        // Call start event
        if(state == GameState.START) {
            // Call event
            event = new SessionStartEvent(this);
            Bukkit.getPluginManager().callEvent(event);

            if(event.isCancelled())
                return;

            startDate = new Date();
            initialPlayers.addAll(arena.getPlayers(PlayerState.ALIVE)
                    .stream()
                    .map(GamePlayer::getUuid).collect(Collectors.toList()));
        }

        // Death match
        if(state == GameState.DEATHMATCH) {
            deathMatchPlayers.addAll(arena.getPlayers(PlayerState.ALIVE)
                    .stream()
                    .map(GamePlayer::getUuid).collect(Collectors.toList()));
        }

        // Call finish event
        if(state == GameState.FINISH) {
            // Call event
            event = new SessionFinishEvent(this);
            Bukkit.getPluginManager().callEvent(event);

            if(event.isCancelled())
                return;

            endDate = new Date();
        }

        // Update state
        this.state = state;
        this.currentTimer = duration;
        this.initialTimer = duration;
    }

    /**
     * Reset the game session timer
     */
    public void resetTimer() {
        this.currentTimer = this.initialTimer;
    }

    /**
     * Tick the game session
     */
    public void tick() {
        // Call event
        SessionEvent event = new SessionTimerTickEvent(this, --currentTimer);
        Bukkit.getPluginManager().callEvent(event);

        if(event.isCancelled())
            ++currentTimer;

        // Timer has expired
        if(currentTimer <= -1) {
            // Call event
            event = new SessionTimerExpireEvent(this);
            Bukkit.getPluginManager().callEvent(event);

            if(event.isCancelled())
                resetTimer();
        }
    }

    /**
     * Convert game session to a string
     * @return converted string
     */
    @Override
    public String toString() {
        return "GameSession{" +
                "arena=" + arena +
                ", state=" + state +
                ", initialTimer=" + initialTimer +
                ", currentTimer=" + currentTimer +
                ", minPlayers=" + minPlayers +
                ", maxPlayers=" + maxPlayers +
                '}';
    }
}
