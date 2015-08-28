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
        return new ArrayList<>(players);
    }

    /**
     * Get players that are currently on a state
     * @param state state to filter players
     * @return players that correspond to that state
     */
    public List<PlayerData> getPlayers(PlayerState state) {
        List<PlayerData> players = new ArrayList<>();
        for(PlayerData player : this.players)
            if(player.getState() == state)
                players.add(player);
        return players;
    }

    /**
     * Check if a player belongs to this team
     * @param player player to be checked
     * @return true if belongs, false otherwise
     */
    public boolean hasPlayer(PlayerData player) {
        return hasPlayer(player.getUuid());
    }

    /**
     * Check if a player belongs to this team
     * @param player UUID of the player to be checked
     * @return true if belongs, false otherwise
     */
    public boolean hasPlayer(UUID player) {
        for(PlayerData target : players)
            if(player.equals(target.getUuid()))
                return true;
        return false;
    }

    /**
     * Add a player to the team
     * @param player player to be added
     * @return true if successfully added, false otherwise
     */
    public boolean addPlayer(PlayerData player) {
        if(players.contains(player))
            return false;
        return players.add(player);
    }

    /**
     * Remove a player from the team
     * @param player player to be removed
     * @return true if successfully removed, false otherwise
     */
    public boolean removePlayer(PlayerData player) {
        if(!players.contains(player))
            return false;
        return players.remove(player);
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
