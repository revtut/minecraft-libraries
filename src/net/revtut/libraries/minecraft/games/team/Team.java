package net.revtut.libraries.minecraft.games.team;

import net.revtut.libraries.Libraries;
import net.revtut.libraries.minecraft.games.events.player.PlayerJoinTeamEvent;
import net.revtut.libraries.minecraft.games.events.player.PlayerLeaveTeamEvent;
import net.revtut.libraries.minecraft.games.events.player.PlayerSpectateTeamEvent;
import net.revtut.libraries.minecraft.games.player.PlayerData;
import net.revtut.libraries.minecraft.games.player.PlayerState;
import net.revtut.libraries.minecraft.games.utils.Color;
import net.revtut.libraries.minecraft.games.utils.Winner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Team Object
 */
public class Team implements Winner {

    /**
     * Name of the team
     */
    private final String name;

    /**
     * Color of the team
     */
    private final Color color;

    /**
     * List with all players on the team
     */
    private final List<PlayerData> players;

    /**
     * Default constructor of Team
     */
    public Team() {
        this("Default", Color.values()[(int) (Math.random() * (Color.values().length - 1))]);
    }

    /**
     * Constructor of Team
     * @param name name of the team
     * @param color color of the team
     */
    public Team(final String name, final Color color) {
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
    public List<PlayerData> getPlayers(final PlayerState state) {
        return players.stream().filter(player -> player.getState() == state).collect(Collectors.toList());
    }

    /**
     * Get the size of the team
     * @return size of the team
     */
    public int getSize() {
        return players.size();
    }

    /**
     * Check if a player belongs to this team
     * @param player player to be checked
     * @return true if belongs, false otherwise
     */
    public boolean containsPlayer(final PlayerData player) {
        return containsPlayer(player.getUuid());
    }

    /**
     * Check if a player belongs to this team
     * @param player UUID of the player to be checked
     * @return true if belongs, false otherwise
     */
    public boolean containsPlayer(final UUID player) {
        for(final PlayerData target : players)
            if(player.equals(target.getUuid()))
                return true;
        return false;
    }

    /**
     * Make a player join this team
     * @param player player to be added
     * @return true if has joined, false otherwise
     */
    public boolean join(final PlayerData player) {
        if(players.contains(player))
            return true;

        // Call event
        final PlayerJoinTeamEvent event = new PlayerJoinTeamEvent(player, player.getCurrentArena(), this, player.getName() + " has joined the team " + name);
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
    public boolean leave(final PlayerData player) {
        if(!players.contains(player))
            return true;

        // Call event
        final PlayerLeaveTeamEvent event = new PlayerLeaveTeamEvent(player, player.getCurrentArena(), this, player.getName() + " has left the team " + name);
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
    public boolean spectate(final PlayerData player) {
        if(players.contains(player))
            return true;

        // Call event
        final PlayerSpectateTeamEvent event = new PlayerSpectateTeamEvent(player, player.getCurrentArena(), this, player.getName() + " is now spectating the team " + name);
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
    public void broadcastMessage(final String message) {
        for(final PlayerData player : getAllPlayers())
            player.getBukkitPlayer().sendMessage(message);
    }

    /**
     * Check if two teams are equal
     * @param obj team to be compared
     * @return true if they are equal, false otherwise
     */
    public boolean equals(final Object obj) {
        if(!(obj instanceof Team))
            return false;
        final Team target = (Team) obj;
        return target.getName().equals(name);
    }

    /**
     * Convert Team to string
     * @return converted string
     */
    @Override
    public String toString() {
        return name;
    }
}
