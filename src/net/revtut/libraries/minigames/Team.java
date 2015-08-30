package net.revtut.libraries.minigames;

import net.revtut.libraries.minigames.player.PlayerData;
import net.revtut.libraries.minigames.player.PlayerState;
import org.bukkit.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Team Object
 */
public class Team {

    /**
     * Name of the team
     */
    private String name;

    /**
     * Color of the team
     */
    private Color color;

    /**
     * List with all players on the team
     */
    private List<PlayerData> players;

    /**
     * Default constructor of Team
     */
    public Team() {
        this("Default", Color.WHITE);
    }

    /**
     * Constructor of Team
     * @param name name of the team
     * @param color color of the team
     */
    public Team(String name, Color color) {
        this.name = name;
        this.color = color;
        this.players = new ArrayList<>();
    }

    /**
     * Get the name of the team
     * @return name of the team
     */
    public String getName() {
        return name;
    }

    /**
     * Get the color of the team
     * @return color of the team
     */
    public Color getColor() {
        return color;
    }

    /**
     * Get the players on the team
     * @return players on the team
     */
    public List<PlayerData> getAllPlayers() {
        return players;
    }

    /**
     * Get players that are currently on a state
     * @param state state to filter players
     * @return players that correspond to that state
     */
    public List<PlayerData> getPlayers(PlayerState state) {
        List<PlayerData> filteredPlayers = new ArrayList<>();
        for(PlayerData player : players)
            if(player.getState() == state)
                filteredPlayers.add(player);
        return filteredPlayers;
    }

    /**
     * Check if a player belongs to this team
     * @param player player to be checked
     * @return true if belongs, false otherwise
     */
    public boolean isOnTeam(PlayerData player) {
        return isOnTeam(player.getUuid());
    }

    /**
     * Check if a player belongs to this team
     * @param player UUID of the player to be checked
     * @return true if belongs, false otherwise
     */
    public boolean isOnTeam(UUID player) {
        for(PlayerData target : players)
            if(player.equals(target.getUuid()))
                return true;
        return false;
    }

    /**
     * Make a player join this team
     * @param player player to be added
     */
    public void join(PlayerData player) {
        if(players.contains(player))
            return;
        players.add(player);
    }

    /**
     * Make a player leave the team
     * @param player player to be removed
     */
    public void leave(PlayerData player) {
        if(!players.contains(player))
            return;
        players.remove(player);
    }

    /**
     * Make a player spectate the team
     * @param player player to spectate
     */
    public void spectate(PlayerData player) {
        join(player);
    }

    /**
     * Check if two teams are equal
     * @param obj team to be compared
     * @return true if they are equal, false otherwise
     */
    public boolean equals(Object obj) {
        if(!(obj instanceof Team))
            return false;
        Team target = (Team) obj;
        return target.getName().equals(name);
    }
}
