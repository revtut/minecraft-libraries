package net.revtut.libraries.games;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.events.player.PlayerCrossArenaBorderEvent;
import net.revtut.libraries.games.events.player.PlayerEvent;
import net.revtut.libraries.games.player.PlayerData;
import net.revtut.libraries.maths.AlgebraAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

/**
 * Listener of the games api
 */
public class EventListener implements Listener {

    /**
     * Game API instance
     */
    private GameAPI gameAPI = GameAPI.getInstance();

    /**
     * Controls the quit event
     * @param event player quit event
     */
    public void onQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        // Get needed data
        Arena arena = gameAPI.getArena(uuid);
        if(arena == null)
            return;
        PlayerData player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Leave the arena
        arena.leave(player);
        GameAPI.getInstance().removePlayer(player);
    }

    /**
     * Controls the movement event
     * @param event player move event
     */
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        // Get needed data
        Arena arena = gameAPI.getArena(uuid);
        if(arena == null)
            return;
        PlayerData player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Crossing borders
        if(!AlgebraAPI.isInAABB(event.getTo(), arena.getCorners()[0], arena.getCorners()[1])) {
            // Call event
            PlayerEvent crossArenaBorderEvent = new PlayerCrossArenaBorderEvent(player, arena);
            Bukkit.getPluginManager().callEvent(crossArenaBorderEvent);

            if(event.isCancelled()) {
                event.setTo(event.getFrom());
                return;
            }
        }
    }
}