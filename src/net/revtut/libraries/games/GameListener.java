package net.revtut.libraries.games;

import com.google.common.collect.ImmutableList;
import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.arena.ArenaFlag;
import net.revtut.libraries.games.arena.ArenaPreference;
import net.revtut.libraries.games.arena.session.GameState;
import net.revtut.libraries.games.arena.types.ArenaSolo;
import net.revtut.libraries.games.arena.types.ArenaTeam;
import net.revtut.libraries.games.arena.types.ArenaType;
import net.revtut.libraries.games.events.player.*;
import net.revtut.libraries.games.guns.Gun;
import net.revtut.libraries.games.player.PlayerData;
import net.revtut.libraries.games.player.PlayerState;
import net.revtut.libraries.maths.AlgebraAPI;
import net.revtut.libraries.text.checks.AdvertisementCheck;
import net.revtut.libraries.text.checks.BadWordCheck;
import net.revtut.libraries.text.checks.CapsCheck;
import net.revtut.libraries.text.checks.Check;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

/**
 * Listener of the games api
 */
public class GameListener implements Listener {

    /**
     * Game API instance
     */
    private final GameAPI gameAPI;

    /**
     * Checks to be used on chat
     */
    private final List<Check> checksList;

    /**
     * Constructor of GameListener
     */
    public GameListener() {
        gameAPI = GameAPI.getInstance();
        checksList = ImmutableList.copyOf(new Check[] { new AdvertisementCheck(), new BadWordCheck(), new CapsCheck()});
    }

    /**
     * Controls the block break event
     * @param event block break event
     */
    @EventHandler
    public void onBlockBreak(final BlockBreakEvent event) {
        final UUID uuid = event.getPlayer().getUniqueId();

        // Get needed data
        final Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;
        final PlayerData player = gameAPI.getPlayer(uuid);
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
    public void onBlockPlace(final BlockPlaceEvent event) {
        final UUID uuid = event.getPlayer().getUniqueId();

        // Get needed data
        final Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;
        final PlayerData player = gameAPI.getPlayer(uuid);
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
    public void onBucketEmpty(final PlayerBucketEmptyEvent event) {
        final UUID uuid = event.getPlayer().getUniqueId();

        // Get needed data
        final Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;
        final PlayerData player = gameAPI.getPlayer(uuid);
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
    public void onBucketFill(final PlayerBucketFillEvent event) {
        final UUID uuid = event.getPlayer().getUniqueId();

        // Get needed data
        final Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;
        final PlayerData player = gameAPI.getPlayer(uuid);
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
    public void onChat(final AsyncPlayerChatEvent event) {
        final Player bukkitPlayer = event.getPlayer();
        final UUID uuid = bukkitPlayer.getUniqueId();

        // Get needed data
        final Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;
        final PlayerData player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.CHAT)) {
            event.setCancelled(true);
            return;
        }

        // Cancel the event to use our own methods
        event.setCancelled(true);

        // Checkers
        final String message = event.getMessage();
        for(final Check check : checksList)
            if(check.checkMessage(bukkitPlayer, message))
                check.fixMessage(message);

        // Call event
        final PlayerTalkEvent playerTalkEvent = new PlayerTalkEvent(player, arena, "<" + player.getName() + "> " + message);
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
    public void onDamage(final EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player))
            return;

        if(event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK)
            return;

        // Get needed data
        final Arena arena = gameAPI.getPlayerArena(event.getEntity().getUniqueId());
        if(arena == null)
            return;
        final PlayerData target = gameAPI.getPlayer(event.getEntity().getUniqueId());
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

                    // Get spectator location accordingly the arena state
                    final Location spectatorLocation;
                    if(arena.getSession().getState() == GameState.DEATHMATCH)
                        spectatorLocation = arena.getSpectatorDeathMatchLocation();
                    else
                        spectatorLocation = arena.getSpectatorLocation();
                    target.getBukkitPlayer().teleport(spectatorLocation);
                    return;
                } else if (target.getState() == PlayerState.DEAD) {
                    event.setCancelled(true);
                    if(arena.getType() == ArenaType.SOLO) {
                        final ArenaSolo arenaSolo = (ArenaSolo) arena;

                        // Get dead location accordingly the arena state
                        final Location deadLocation;
                        if(arenaSolo.getSession().getState() == GameState.DEATHMATCH)
                            deadLocation = arenaSolo.getDeadDeathMatchLocation();
                        else
                            deadLocation = arenaSolo.getDeadLocation();
                        target.getBukkitPlayer().teleport(deadLocation);
                        return;
                    } else if (arena.getType() == ArenaType.TEAM) {
                        final ArenaTeam arenaTeam = (ArenaTeam) arena;

                        // Get dead location accordingly the arena state
                        final Location deadLocation;
                        if(arenaTeam.getSession().getState() == GameState.DEATHMATCH)
                            deadLocation = arenaTeam.getDeadDeathMatchLocation(arenaTeam.getTeam(target));
                        else
                            deadLocation = arenaTeam.getDeadLocation(arenaTeam.getTeam(target));
                        target.getBukkitPlayer().teleport(deadLocation);
                        return;
                    }
                }
            }
        }

        // Check flag
        if(!arena.getFlag(ArenaFlag.DAMAGE)) {
            event.setCancelled(true);
            return;
        }

        // Call event
        final PlayerDamageEvent playerDamageEvent = new PlayerDamageEvent(target, arena, event.getDamage());
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
    public void onDamageByEntity(final EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        if (!(event.getDamager() instanceof Player) && !(event.getDamager() instanceof Projectile))
            return;

        // Get the UUID of the damager
        final UUID damagerUUID;
        if(event.getDamager() instanceof Player) {
            damagerUUID = event.getDamager().getUniqueId();
        } else {
            final Projectile projectile = (Projectile) event.getDamager();
            if(!(projectile.getShooter() instanceof Player))
                return;

            damagerUUID = ((Player) projectile.getShooter()).getUniqueId();
        }

        // Get needed data
        final Arena arenaTarget = gameAPI.getPlayerArena(event.getEntity().getUniqueId());
        if (arenaTarget == null)
            return;
        final Arena arenaDamager = gameAPI.getPlayerArena(damagerUUID);
        if (arenaDamager == null)
            return;
        final PlayerData target = gameAPI.getPlayer(event.getEntity().getUniqueId());
        if (target == null)
            return;
        final PlayerData damager = gameAPI.getPlayer(damagerUUID);
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
            final ArenaTeam arena = (ArenaTeam) arenaDamager;

            if(arena.sameTeam(damager, target)) {
                final PlayerFriendlyFireEvent playerFriendlyFireEvent = new PlayerFriendlyFireEvent(target, damager, damager.getBukkitPlayer().getItemInHand(), event.getDamage(), arenaDamager);
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
        final PlayerDamageByPlayerEvent playerDamageByPlayerEvent = new PlayerDamageByPlayerEvent(target, damager, damager.getBukkitPlayer().getItemInHand(), event.getDamage(), arenaDamager);
        Bukkit.getPluginManager().callEvent(playerDamageByPlayerEvent);

        if (playerDamageByPlayerEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        event.setDamage(playerDamageByPlayerEvent.getDamage());
    }

    /**
     * Controls the player death event
     * @param event player death event
     */
    @EventHandler
    public void onDeath(final PlayerDeathEvent event) {
        final UUID uuid = event.getEntity().getUniqueId();

        // Get needed data
        final Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;
        final PlayerData player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;
        PlayerData killer = null;
        if(event.getEntity().getKiller() != null) {
            killer = gameAPI.getPlayer(event.getEntity().getKiller().getUniqueId());
            if (killer == null)
                return;
        }

        // Call event
        final PlayerDieEvent playerDieEvent = new PlayerDieEvent(player, killer, arena, "<" + player.getName() + "> has died.");
        Bukkit.getPluginManager().callEvent(playerDieEvent);

        arena.broadcastMessage(playerDieEvent.getDeathMessage());
        event.setDeathMessage(null);
    }

    /**
     * Controls the player drop item event
     * @param event player drop item event
     */
    @EventHandler
    public void onDropItem(final PlayerDropItemEvent event) {
        final UUID uuid = event.getPlayer().getUniqueId();

        // Get needed data
        final Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;
        final PlayerData player = gameAPI.getPlayer(uuid);
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
    public void onFoodLevelChange(final FoodLevelChangeEvent event) {
        final UUID uuid = event.getEntity().getUniqueId();

        // Get needed data
        final Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;
        final PlayerData player = gameAPI.getPlayer(uuid);
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
    public void onInteract(final PlayerInteractEvent event) {
        final Player bukkitPlayer = event.getPlayer();
        final UUID uuid = bukkitPlayer.getUniqueId();

        // Get needed data
        final Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;
        final PlayerData player = gameAPI.getPlayer(uuid);
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
        final ItemStack itemInHand = bukkitPlayer.getItemInHand();
        if(!(itemInHand instanceof Gun))
            return;

        final Gun gun = (Gun) itemInHand;
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
    public void onInventoryClick(final InventoryClickEvent event) {
        final UUID uuid = event.getWhoClicked().getUniqueId();

        // Get needed data
        final Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;
        final PlayerData player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.INVENTORY_CLICK)) {
            event.setCancelled(true);
            return;
        }
    }

    /**
     * Controls the player join event
     * @param event player join event
     */
    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final UUID uuid = event.getPlayer().getUniqueId();

        // Hide every player
        gameAPI.hideServer(event.getPlayer());

        // Get needed data
        final PlayerData player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Join random game
        final GameController gameController = gameAPI.getRandomGame();
        final Arena arena = gameController.getAvailableArena(ArenaPreference.MORE_PLAYERS);

        // No arena available or not allowed to join the arena
        if(arena == null || !arena.join(player)) {
            // TODO take care when player can not join an arena
        }

        event.setJoinMessage(null);
    }

    /**
     * Controls the player move event
     * @param event player move event
     */
    @EventHandler
    public void onMove(final PlayerMoveEvent event) {
        final UUID uuid = event.getPlayer().getUniqueId();

        // Get needed data
        final Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;
        final PlayerData player = gameAPI.getPlayer(uuid);
        if (player == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.MOVE)) {
            event.setTo(event.getFrom());
            return;
        }

        // Crossing borders
        final Location[] corners;
        if(arena.getSession().getState() == GameState.DEATHMATCH)
            corners = arena.getCornersDeathMatch();
        else
            corners = arena.getCorners();

        if(!AlgebraAPI.isInAABB(event.getTo(), corners[0], corners[1])) {
            // Call event
            final PlayerCrossArenaBorderEvent crossArenaBorderEvent = new PlayerCrossArenaBorderEvent(player, arena);
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
    public void onPickupItem(final PlayerPickupItemEvent event) {
        final UUID uuid = event.getPlayer().getUniqueId();

        // Get needed data
        final Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;
        final PlayerData player = gameAPI.getPlayer(uuid);
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
    public void onQuit(final PlayerQuitEvent event) {
        final UUID uuid = event.getPlayer().getUniqueId();

        // Get needed data
        final PlayerData player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;
        final Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena != null)
            arena.leave(player);

        GameAPI.getInstance().removePlayer(player);

        event.setQuitMessage(null);
    }
}