package net.revtut.libraries.games;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.arena.session.GameState;
import net.revtut.libraries.games.arena.types.ArenaSolo;
import net.revtut.libraries.games.arena.types.ArenaTeam;
import net.revtut.libraries.games.events.player.*;
import net.revtut.libraries.games.player.PlayerData;
import net.revtut.libraries.games.player.PlayerState;
import net.revtut.libraries.maths.AlgebraAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

/**
 * Listener of the games api
 */
public abstract class GameListener implements Listener {

    /**
     * Game API instance
     */
    private GameAPI gameAPI = GameAPI.getInstance();

    /**
     * Controls the player chat event
     * @param event async player chat event
     */
    public void onChat(AsyncPlayerChatEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        // Get needed data
        Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;
        PlayerData player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Cancel the event to use our own methods
        event.setCancelled(true);

        // Call event
        PlayerTalkEvent playerTalkEvent = new PlayerTalkEvent(player, arena, "<" + player.getName() + "> " + event.getMessage());
        Bukkit.getPluginManager().callEvent(playerTalkEvent);

        if(playerTalkEvent.isCancelled())
            return;

        // Broadcast message
        arena.broadcastMessage(playerTalkEvent.getFormattedMessage());
    }

    /**
     * Controls the entity damage event
     * @param event entity damage event
     */
    public void onDamage(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player))
            return;

        if(event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK)
            return;

        // Get needed data
        Arena arena = gameAPI.getPlayerArena(event.getEntity().getUniqueId());
        if(arena == null)
            return;
        PlayerData target = gameAPI.getPlayer(event.getEntity().getUniqueId());
        if(target == null)
            return;

        // Falling in void
        if(event.getCause() == EntityDamageEvent.DamageCause.VOID) {
            if(arena.getSession().getState() == GameState.LOBBY) {
                event.setCancelled(true);
                target.getBukkitPlayer().teleport(arena.getLobbyLocation());
                return;
            } else {
                if(target.getState() == PlayerState.SPECTATOR) {
                    event.setCancelled(true);
                    target.getBukkitPlayer().teleport(arena.getSpectatorLocation());
                    return;
                } else if (target.getState() == PlayerState.DEAD) {
                    event.setCancelled(true);
                    if(arena instanceof ArenaSolo) {
                        ArenaSolo arenaSolo = (ArenaSolo) arena;
                        target.getBukkitPlayer().teleport(arenaSolo.getDeathLocation());
                        return;
                    } else if (arena instanceof ArenaTeam) {
                        ArenaTeam arenaTeam = (ArenaTeam) arena;
                        target.getBukkitPlayer().teleport(arenaTeam.getDeathLocation(arenaTeam.getTeam(target)));
                        return;
                    }
                }
            }
        }

        // Call event
        PlayerDamageEvent playerDamageEvent = new PlayerDamageEvent(target, arena);
        Bukkit.getPluginManager().callEvent(playerDamageEvent);

        event.setCancelled(playerDamageEvent.isCancelled());
    }

    /**
     * Controls the entity damage by entity event
     * @param event entity damage by entity event
     */
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        if (!(event.getDamager() instanceof Player) && !(event.getDamager() instanceof Projectile))
            return;

        // Get the UUID of the damager
        UUID damagerUUID;
        if(event.getDamager() instanceof Player) {
            damagerUUID = event.getDamager().getUniqueId();
        } else {
            Projectile projectile = (Projectile) event.getDamager();
            if(!(projectile.getShooter() instanceof Player))
                return;

            damagerUUID = ((Player) projectile.getShooter()).getUniqueId();
        }

        // Get needed data
        Arena arenaTarget = gameAPI.getPlayerArena(event.getEntity().getUniqueId());
        if (arenaTarget == null)
            return;
        Arena arenaDamager = gameAPI.getPlayerArena(damagerUUID);
        if (arenaDamager == null)
            return;
        PlayerData target = gameAPI.getPlayer(event.getEntity().getUniqueId());
        if (target == null)
            return;
        PlayerData damager = gameAPI.getPlayer(damagerUUID);
        if (damager == null)
            return;


        // Check if they are on the same arena
        if (arenaTarget != arenaDamager) {
            event.setCancelled(true);
            return;
        }

        // Check friendly fire
        if(arenaDamager instanceof ArenaTeam) {
            ArenaTeam arena = (ArenaTeam) arenaDamager;

            if(arena.sameTeam(damager, target)) {
                PlayerFriendlyFireEvent playerFriendlyFireEvent = new PlayerFriendlyFireEvent(target, damager, damager.getBukkitPlayer().getItemInHand(), event.getDamage(), arenaDamager);
                Bukkit.getPluginManager().callEvent(playerFriendlyFireEvent);

                if (playerFriendlyFireEvent.isCancelled()) {
                    event.setCancelled(true);
                    return;
                }

                event.setDamage(playerFriendlyFireEvent.getDamage());
                return;
            }
        }

        // Call event
        PlayerDamageByPlayerEvent playerDamageByPlayerEvent = new PlayerDamageByPlayerEvent(target, damager, damager.getBukkitPlayer().getItemInHand(), event.getDamage(), arenaDamager);
        Bukkit.getPluginManager().callEvent(playerDamageByPlayerEvent);

        if (playerDamageByPlayerEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        event.setDamage(playerDamageByPlayerEvent.getDamage());
    }

    /**
     * Controls the player quit event
     * @param event player quit event
     */
    public void onQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        // Get needed data
        Arena arena = gameAPI.getPlayerArena(uuid);
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
     * Controls the player move event
     * @param event player move event
     */
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        // Get needed data
        Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;
        PlayerData player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Crossing borders
        if(!AlgebraAPI.isInAABB(event.getTo(), arena.getCorners()[0], arena.getCorners()[1])) {
            // Call event
            PlayerCrossArenaBorderEvent crossArenaBorderEvent = new PlayerCrossArenaBorderEvent(player, arena);
            Bukkit.getPluginManager().callEvent(crossArenaBorderEvent);

            if(event.isCancelled()) {
                event.setTo(event.getFrom());
                return;
            }
        }
    }
}