package net.revtut.libraries.games;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.arena.ArenaFlag;
import net.revtut.libraries.games.arena.session.GameState;
import net.revtut.libraries.games.arena.types.ArenaSolo;
import net.revtut.libraries.games.arena.types.ArenaTeam;
import net.revtut.libraries.games.arena.types.ArenaType;
import net.revtut.libraries.games.events.player.*;
import net.revtut.libraries.games.guns.Gun;
import net.revtut.libraries.games.player.PlayerData;
import net.revtut.libraries.games.player.PlayerState;
import net.revtut.libraries.maths.AlgebraAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Listener of the games api
 */
public class GameListener implements Listener {

    /**
     * Game API instance
     */
    private GameAPI gameAPI = GameAPI.getInstance();

    /**
     * Controls the block break event
     * @param event block break event
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        // Get needed data
        Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;
        PlayerData player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.BLOCK_BREAK)) {
            event.setCancelled(true);
            return;
        }
    }

    /**
     * Controls the block place event
     * @param event block place event
     */
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        // Get needed data
        Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;
        PlayerData player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.BLOCK_PLACE)) {
            event.setCancelled(true);
            return;
        }
    }

    /**
     * Controls the player bucket empty event
     * @param event player bucket empty event
     */
    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        // Get needed data
        Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;
        PlayerData player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.BUCKET_EMPTY)) {
            event.setCancelled(true);
            return;
        }
    }

    /**
     * Controls the player bucket fill event
     * @param event player bucket fill event
     */
    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        // Get needed data
        Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;
        PlayerData player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.BUCKET_FILL)) {
            event.setCancelled(true);
            return;
        }
    }

    /**
     * Controls the player chat event
     * @param event async player chat event
     */
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        // Get needed data
        Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;
        PlayerData player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.CHAT)) {
            event.setCancelled(true);
            return;
        }

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
    @EventHandler
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

        // Check flag
        if(!arena.getFlag(ArenaFlag.DAMAGE)) {
            event.setCancelled(true);
            return;
        }

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
                    if(arena.getType() == ArenaType.SOLO) {
                        ArenaSolo arenaSolo = (ArenaSolo) arena;
                        target.getBukkitPlayer().teleport(arenaSolo.getDeathLocation());
                        return;
                    } else if (arena.getType() == ArenaType.TEAM) {
                        ArenaTeam arenaTeam = (ArenaTeam) arena;
                        target.getBukkitPlayer().teleport(arenaTeam.getDeathLocation(arenaTeam.getTeam(target)));
                        return;
                    }
                }
            }
        }

        // Call event
        PlayerDamageEvent playerDamageEvent = new PlayerDamageEvent(target, arena, event.getDamage());
        Bukkit.getPluginManager().callEvent(playerDamageEvent);

        if (playerDamageEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        event.setDamage(playerDamageEvent.getDamage());
    }

    /**
     * Controls the entity damage by entity event
     * @param event entity damage by entity event
     */
    @EventHandler
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

        // Check flag
        if(!arenaTarget.getFlag(ArenaFlag.DAMAGE)) {
            event.setCancelled(true);
            return;
        }


        // Check if they are on the same arena
        if (arenaTarget != arenaDamager) {
            event.setCancelled(true);
            return;
        }

        // Check friendly fire
        if(arenaDamager.getType() == ArenaType.TEAM) {
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
     * Controls the player drop item event
     * @param event player drop item event
     */
    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        // Get needed data
        Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;
        PlayerData player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.DROP_ITEM)) {
            event.setCancelled(true);
            return;
        }
    }

    /**
     * Controls the food level change event
     * @param event food level change event
     */
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        UUID uuid = event.getEntity().getUniqueId();

        // Get needed data
        Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;
        PlayerData player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.HUNGER)) {
            event.setCancelled(true);
            return;
        }
    }

    /**
     * Controls the player interact event
     * @param event player interact event
     */
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player bukkitPlayer = event.getPlayer();
        UUID uuid = bukkitPlayer.getUniqueId();

        // Get needed data
        Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;
        PlayerData player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.INTERACT)) {
            event.setCancelled(true);
            return;
        }

        if(bukkitPlayer.getItemInHand() == null)
            return;

        // Check guns
        ItemStack itemInHand = bukkitPlayer.getItemInHand();
        if(!(itemInHand instanceof Gun))
            return;

        Gun gun = (Gun) itemInHand;
        if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
            gun.shoot(bukkitPlayer);
        else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
            gun.reload(bukkitPlayer);
    }

    /**
     * Controls the inventory click event
     * @param event inventory click event
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        UUID uuid = event.getWhoClicked().getUniqueId();

        // Get needed data
        Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;
        PlayerData player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.INVENTORY_CLICK)) {
            event.setCancelled(true);
            return;
        }
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
        if (player == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.MOVE)) {
            event.setTo(event.getFrom());
            return;
        }

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

    /**
     * Controls the player pickup item event
     * @param event player pickup item event
     */
    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        // Get needed data
        Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;
        PlayerData player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.PICKUP_ITEM)) {
            event.setCancelled(true);
            return;
        }
    }

    /**
     * Controls the player quit event
     * @param event player quit event
     */
    @EventHandler
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
}