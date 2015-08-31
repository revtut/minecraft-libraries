package net.revtut.libraries.games;

import net.revtut.libraries.Libraries;
import net.revtut.libraries.games.events.player.PlayerJoinTeamEvent;
import net.revtut.libraries.games.events.player.PlayerLeaveTeamEvent;
import net.revtut.libraries.games.events.player.PlayerSpectateTeamEvent;
import net.revtut.libraries.games.player.PlayerData;
import net.revtut.libraries.games.player.PlayerState;
import net.revtut.libraries.games.utils.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        return players.stream().filter(player -> player.getState() == state).collect(Collectors.toList());
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
     * @return true if has joined, false otherwise
     */
    public boolean join(PlayerData player) {
        if(players.contains(player))
            return true;

        // Call event
        PlayerJoinTeamEvent event = new PlayerJoinTeamEvent(player, player.getCurrentArena(), this, player.getName() + " has joined the team " + name);
        Libraries.getInstance().getServer().getPluginManager().callEvent(event);

        if(event.isCancelled())
            return false;

        broadcastMessage(event.getJoinMessage());

        players.add(player);

        return true;
    }

    /**
     * Make a player leave the team
     * @param player player to be removed
     * @return true if has left, false otherwise
     */
    public boolean leave(PlayerData player) {
        if(!players.contains(player))
            return true;

        // Call event
        PlayerLeaveTeamEvent event = new PlayerLeaveTeamEvent(player, player.getCurrentArena(), this, player.getName() + " has left the team " + name);
        Libraries.getInstance().getServer().getPluginManager().callEvent(event);

        if(event.isCancelled())
            return false;

        broadcastMessage(event.getLeaveMessage());

        players.remove(player);

        return true;
    }

    /**
     * Make a player spectate the team
     * @param player player to spectate
     * @return true if is spectating, false otherwise
     */
    public boolean spectate(PlayerData player) {
        if(players.contains(player))
            return true;

        // Call event
        PlayerSpectateTeamEvent event = new PlayerSpectateTeamEvent(player, player.getCurrentArena(), this, player.getName() + " is now spectating the team " + name);
        Libraries.getInstance().getServer().getPluginManager().callEvent(event);

        if(event.isCancelled())
            return false;

        broadcastMessage(event.getJoinMessage());

        players.add(player);

        return true;
    }

    /**
     * Broadcast a message to the team
     * @param message message to be broadcast
     */
    public void broadcastMessage(String message) {
        for(PlayerData player : getAllPlayers())
            player.getBukkitPlayer().sendMessage(message);
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
