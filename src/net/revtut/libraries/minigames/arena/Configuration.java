package net.revtut.libraries.minigames.arena;

import java.io.File;

/**
 * Configuration of a Arena
 */
public class Configuration {

    /**
     * File where worlds are located
     */
    private File worldsFolder;

    /**
     * Minimum players on the arena
     */
    private int minPlayers;

    /**
     * Maximum players on the arena
     */
    private int maxPlayers;

    /**
     * Duration that players stay on the lobby
     */
    private int lobbyDuration;

    /**
     * Duration of the warm up
     */
    private int warmUpDuration;

    /**
     * Duration of the game
     */
    private int gameDuration;

    /**
     * Duration of the end game
     */
    private int endGameDuration;

    /**
     * True if wants to display timer on the XP bar
     */
    private boolean xpTimer;

    /**
     * Default constructor of Configuration
     */
    public Configuration() {
        this(null, 0, 0, 0, 0, 0, 0, false);
    }

    /**
     * Constructor of Configuration
     * @param worldsFolder worlds folder of the arena
     * @param minPlayers minimum players on the arena
     * @param maxPlayers maximum players on the arena
     * @param lobbyDuration duration that players stay on the lobby
     * @param warmUpDuration duration of the warm up
     * @param gameDuration duration of the game
     * @param endGameDuration duration of the end game
     * @param xpTimer true if wants to display timer on the XP bar
     */
    public Configuration(File worldsFolder, int minPlayers, int maxPlayers, int lobbyDuration, int warmUpDuration, int gameDuration, int endGameDuration, boolean xpTimer) {
        this.worldsFolder = worldsFolder;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.lobbyDuration = lobbyDuration;
        this.warmUpDuration = warmUpDuration;
        this.gameDuration = gameDuration;
        this.endGameDuration = endGameDuration;
        this.xpTimer = xpTimer;
    }

    /**
     * Get the worlds folder of the arena
     * @return worlds folder of the arena
     */
    public File getWorldsFolder() {
        return worldsFolder;
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
     * Get the duration that players stay on the lobby
     * @return duration that players stay on the lobby
     */
    public int getLobbyDuration() {
        return lobbyDuration;
    }

    /**
     * Get the warm up duration
     * @return warm up duration
     */
    public int getWarmUpDuration() {
        return warmUpDuration;
    }

    /**
     * Get the game duration
     * @return game duration
     */
    public int getGameDuration() {
        return gameDuration;
    }

    /**
     * Get the end game duration
     * @return end game duration
     */
    public int getEndGameDuration() {
        return endGameDuration;
    }

    /**
     * Checks if XP timer is enabled or not
     * @return true if enabled, false otherwise
     */
    public boolean enabledXPTimer() {
        return xpTimer;
    }

    /**
     * Set the worlds folder of the arena
     * @param worldsFolder worlds folder of the arena
     */
    public void setWorldsFolder(File worldsFolder) {
        this.worldsFolder = worldsFolder;
    }

    /**
     * Set the minimum players of the arena
     * @param minPlayers minimum players of the arena
     */
    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    /**
     * Set the maximum players of the arena
     * @param maxPlayers maximum players of the arena
     */
    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    /**
     * Set the duration that players stay on the lobby
     * @param lobbyDuration duration that players stay on the lobby
     */
    public void setLobbyDuration(int lobbyDuration) {
        this.lobbyDuration = lobbyDuration;
    }

    /**
     * Set the duration of the warm up
     * @param warmUpDuration duration of the warm up
     */
    public void setWarmUpDuration(int warmUpDuration) {
        this.warmUpDuration = warmUpDuration;
    }

    /**
     * Set the duration of the game
     * @param gameDuration duration of the game
     */
    public void setGameDuration(int gameDuration) {
        this.gameDuration = gameDuration;
    }

    /**
     * Set the end game duration
     * @param endGameDuration end game duration
     */
    public void setEndGameDuration(int endGameDuration) {
        this.endGameDuration = endGameDuration;
    }

    /**
     * Set XP timer to enabled or not
     * @param xpTimer xp timer flag
     */
    public void setXPTimer(boolean xpTimer) {
        this.xpTimer = xpTimer;
    }
}
