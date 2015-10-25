package net.revtut.libraries.minecraft.games;

import com.google.common.collect.ImmutableList;
import net.revtut.libraries.Libraries;
import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.arena.ArenaFlag;
import net.revtut.libraries.minecraft.games.arena.ArenaPreference;
import net.revtut.libraries.minecraft.games.arena.session.GameState;
import net.revtut.libraries.minecraft.games.arena.types.ArenaSolo;
import net.revtut.libraries.minecraft.games.arena.types.ArenaTeam;
import net.revtut.libraries.minecraft.games.arena.types.ArenaType;
import net.revtut.libraries.minecraft.games.events.arena.ArenaBlockBreakEvent;
import net.revtut.libraries.minecraft.games.events.arena.ArenaBlockPlaceEvent;
import net.revtut.libraries.minecraft.games.events.arena.ArenaBucketEmptyEvent;
import net.revtut.libraries.minecraft.games.events.arena.ArenaBucketFillEvent;
import net.revtut.libraries.minecraft.games.events.player.*;
import net.revtut.libraries.minecraft.games.player.GamePlayer;
import net.revtut.libraries.minecraft.games.player.PlayerState;
import net.revtut.libraries.minecraft.maths.AlgebraAPI;
import net.revtut.libraries.minecraft.text.checks.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.ArrayList;
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
     * @param gameAPI instance of game api
     */
    public GameListener(final GameAPI gameAPI) {
        this.gameAPI = gameAPI;
        this.checksList = ImmutableList.copyOf(
                new Check[] {
                        new AdvertisementCheck(),
                        new BadWordCheck(),
                        new CapsCheck(),
                        new EmojiCheck()
                }
        );
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
        final GamePlayer player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.BLOCK_BREAK))
            event.setCancelled(true);

        // Call event
        final ArenaBlockBreakEvent arenaBlockBreakEvent = new ArenaBlockBreakEvent(arena, player, event.getBlock());
        Bukkit.getPluginManager().callEvent(arenaBlockBreakEvent);

        if (arenaBlockBreakEvent.isCancelled())
            event.setCancelled(true);
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
        final GamePlayer player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.BLOCK_PLACE))
            event.setCancelled(true);

        // Call event
        final ArenaBlockPlaceEvent arenaBlockPlaceEvent = new ArenaBlockPlaceEvent(arena, player, event.getBlock());
        Bukkit.getPluginManager().callEvent(arenaBlockPlaceEvent);

        if (arenaBlockPlaceEvent.isCancelled())
            event.setCancelled(true);
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
        final GamePlayer player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.BUCKET_EMPTY))
            event.setCancelled(true);

        // Call event
        final ArenaBucketEmptyEvent arenaBucketEmptyEvent = new ArenaBucketEmptyEvent(arena, player);
        Bukkit.getPluginManager().callEvent(arenaBucketEmptyEvent);

        if (arenaBucketEmptyEvent.isCancelled())
            event.setCancelled(true);
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
        final GamePlayer player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.BUCKET_FILL))
            event.setCancelled(true);

        // Call event
        final ArenaBucketFillEvent arenaBucketFillEvent = new ArenaBucketFillEvent(arena, player);
        Bukkit.getPluginManager().callEvent(arenaBucketFillEvent);

        if (arenaBucketFillEvent.isCancelled())
            event.setCancelled(true);
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
        final GamePlayer player = gameAPI.getPlayer(uuid);
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
        String message = event.getMessage();
        for(final Check check : checksList)
            if (check.checkMessage(bukkitPlayer, message))
                message = check.fixMessage(message);

        // Call event
        final PlayerTalkEvent playerTalkEvent = new PlayerTalkEvent(player, arena, message);
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
        final GamePlayer target = gameAPI.getPlayer(event.getEntity().getUniqueId());
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
        final GamePlayer target = gameAPI.getPlayer(event.getEntity().getUniqueId());
        if (target == null)
            return;
        final GamePlayer damager = gameAPI.getPlayer(damagerUUID);
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
        final GamePlayer player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;
        GamePlayer killer = null;
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
        final GamePlayer player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.DROP_ITEM))
            event.setCancelled(true);

        // Call event
        final PlayerThrowItemEvent playerThrowItemEvent = new PlayerThrowItemEvent(player, arena, event.getItemDrop());
        Bukkit.getPluginManager().callEvent(playerThrowItemEvent);

        if (playerThrowItemEvent.isCancelled())
            event.setCancelled(true);
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
        final GamePlayer player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.HUNGER))
            event.setCancelled(true);

        // Call event
        final PlayerHungerEvent playerHungerEvent = new PlayerHungerEvent(player, arena, event.getFoodLevel());
        Bukkit.getPluginManager().callEvent(playerHungerEvent);

        if (playerHungerEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        event.setFoodLevel(playerHungerEvent.getHunger());
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
        final GamePlayer player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.INTERACT))
            event.setCancelled(true);

        // Call event
        final PlayerInteractionEvent playerInteractionEvent = new PlayerInteractionEvent(player, arena, event.getAction(), event.getClickedBlock(), event.getItem());
        Bukkit.getPluginManager().callEvent(playerInteractionEvent);

        if (playerInteractionEvent.isCancelled())
            event.setCancelled(true);
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
        final GamePlayer player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.INVENTORY_CLICK))
            event.setCancelled(true);

        // Call event
        final PlayerInventoryClickEvent playerInventoryClickEvent = new PlayerInventoryClickEvent(player, arena, event.getClickedInventory(), event.getSlot());
        Bukkit.getPluginManager().callEvent(playerInventoryClickEvent);

        if (playerInventoryClickEvent.isCancelled())
            event.setCancelled(true);
    }

    /**
     * Controls the player join event
     * @param event player join event
     */
    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        event.setJoinMessage(null);

        final UUID uuid = event.getPlayer().getUniqueId();

        // Hide every player
        gameAPI.hideServer(event.getPlayer());

        // Get needed data
        final GamePlayer player = new GamePlayer(uuid);
        // TODO get database information about the player

        // Join random game
        final GameController gameController = gameAPI.getRandomGame();
        final Arena arena = gameController.getAvailableArena(ArenaPreference.MORE_PLAYERS);

        // No arena available or not allowed to join the arena
        if(arena == null || !arena.join(player)) {
            Libraries.getInstance().getNetwork().connectPlayer(player.getBukkitPlayer(), "hub");
            return;
        }

        // Create more arenas if needed
        if(gameController.getAvailableArenas().size() <= 1)
            gameController.createArena(arena.getType());
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
        final GamePlayer player = gameAPI.getPlayer(uuid);
        if (player == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.MOVE)) {
            event.setTo(event.getFrom());
            return;
        }

        // Events
        // Jump event
        if(event.getFrom().getY() != event.getTo().getX()) {
            final double height = event.getTo().getY() - event.getFrom().getY();
            final PlayerJumpEvent playerJumpEvent = new PlayerJumpEvent(player, arena, height);
            Bukkit.getPluginManager().callEvent(playerJumpEvent);

            if (playerJumpEvent.isCancelled()) {
                event.setTo(event.getFrom());
                return;
            }
        }

        // Crossing borders event
        final Location[] corners;
        if(arena.getSession().getState() == GameState.DEATHMATCH)
            corners = arena.getCornersDeathMatch();
        else
            corners = arena.getCorners();

        if(!AlgebraAPI.isInAABB(event.getTo(), corners[0], corners[1])) {
            // Call event
            final PlayerCrossArenaBorderEvent crossArenaBorderEvent = new PlayerCrossArenaBorderEvent(player, arena);
            Bukkit.getPluginManager().callEvent(crossArenaBorderEvent);

            if(crossArenaBorderEvent.isCancelled()) {
                event.setTo(event.getFrom());
                return;
            }
        }

        // Walk event
        final double distance = AlgebraAPI.distanceBetween(event.getFrom(), event.getTo());
        final PlayerWalkEvent playerWalkEvent = new PlayerWalkEvent(player, arena, distance);
        Bukkit.getPluginManager().callEvent(playerWalkEvent);

        if (playerWalkEvent.isCancelled())
            event.setTo(event.getFrom());
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
        final GamePlayer player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.PICKUP_ITEM))
            event.setCancelled(true);

        // Call event
        final PlayerCatchItemEvent playerCatchItemEvent = new PlayerCatchItemEvent(player, arena, event.getItem());
        Bukkit.getPluginManager().callEvent(playerCatchItemEvent);

        if (playerCatchItemEvent.isCancelled())
            event.setCancelled(true);
    }

    /**
     * Controls the player quit event
     * @param event player quit event
     */
    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        event.setQuitMessage(null);

        final UUID uuid = event.getPlayer().getUniqueId();

        // Get needed data
        final GamePlayer player = gameAPI.getPlayer(uuid);
        if(player == null)
            return;
        GameAPI.getInstance().removePlayer(player);

        final Arena arena = gameAPI.getPlayerArena(uuid);
        if(arena == null)
            return;

        arena.leave(player);

        // Delete arena if needed and join randomly all the remaining players
        if(arena.getSession() != null && arena.getSession().getState() != GameState.LOBBY) {
            if(arena.getPlayers(PlayerState.ALIVE).size() <= 1) {
                for(final GamePlayer target : new ArrayList<>(arena.getAllPlayers())) {
                    if(target == player)
                        continue;
                    arena.leave(target);

                    // Join random game
                    final GameController gameController = gameAPI.getRandomGame();
                    final Arena targetArena = gameController.getAvailableArena(ArenaPreference.MORE_PLAYERS);

                    // No arena available or not allowed to join the arena
                    if(targetArena == null || !arena.join(target)) // TODO Message user why he was reconnected
                        Libraries.getInstance().getNetwork().connectPlayer(target.getBukkitPlayer(), "hub");
                }
                final GameController arenaController = GameAPI.getInstance().getGameController(arena);
                if(arenaController == null)
                    return;
                arenaController.removeArena(arena);
            }
        }
    }

    /**
     * Controls the weather change event
     * @param event weather change event
     */
    @EventHandler
    public void onWeatherChange(final WeatherChangeEvent event) {
        // Get needed data
        final Arena arena = gameAPI.getArena(event.getWorld().getName());
        if(arena == null)
            return;

        // Check flag
        if(!arena.getFlag(ArenaFlag.WEATHER))
            event.setCancelled(true);
    }
}