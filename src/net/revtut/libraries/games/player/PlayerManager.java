package net.revtut.libraries.games.player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Player Manager
 */
public class PlayerManager {

    /**
     * Instance of PlayerManager
     */
    private static PlayerManager instance;

    /**
     * List with all players
     */
    private List<PlayerData> players;

    /**
     * Constructor of PlayerManager
     */
    private PlayerManager() {
        players = new ArrayList<>();
    }

    /**
     * Get the instance of player manager
     * @return instance of the player manager
     */
    public static PlayerManager getInstance() {
        if(instance == null)
            instance = new PlayerManager();
        return instance;
    }

    /**
     * Get the player data of a given player
     * @param uuid uuid of the player to get the player data
     * @return player data of the player
     */
    public PlayerData getPlayer(UUID uuid) {
        for(PlayerData player : players)
            if(player.getUuid().equals(uuid))
                return player;
        return null;
    }

    /**
     * Add a player to the list
     * @param player player to be added
     */
    public void addPlayer(PlayerData player) {
        players.add(player);
    }

    /**
     * Remove a player from the list
     * @param player player to be removed
     */
    public void removePlayer(PlayerData player) {
        players.remove(player);
    }
}