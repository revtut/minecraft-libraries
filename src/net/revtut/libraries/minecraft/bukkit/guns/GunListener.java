package net.revtut.libraries.minecraft.bukkit.guns;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EntityEquipment;

/**
 * Gun listener. Controls gun events
 */
public class GunListener implements Listener {

    /**
     * Controls the entity damage by entity event
     * @param event entity damage by entity event
     */
    @EventHandler
    public void onDamageByEntity(final EntityDamageByEntityEvent event) {
        // Get target
        if (!(event.getEntity() instanceof LivingEntity))
            return;
        final LivingEntity target = (LivingEntity) event.getEntity();

        // Check if damager is projectile
        if (!(event.getDamager() instanceof Projectile))
            return;

        // Get projectile
        final Projectile projectile = (Projectile) event.getDamager();
        if(!GunManager.getInstance().isBullet(projectile))
            return;

        // Get the shooter
        if(!(projectile.getShooter() instanceof LivingEntity))
            return;
        final LivingEntity shooter = (LivingEntity) projectile.getShooter();

        // Get the item in hand of the shooter
        final EntityEquipment shooterEquipment = shooter.getEquipment();
        if(shooterEquipment.getItemInHand() == null)
            return;

        if(!(shooterEquipment.getItemInHand() instanceof Gun))
            return;

        // Take care of the gun
        final Gun gun = (Gun) shooterEquipment.getItemInHand();
        gun.hit(shooter, target, projectile.getLocation(), projectile);

        event.setCancelled(true); // Cancelling the event because gun already applies custom damage
    }

    /**
     * Controls the player interact event
     * @param event player interact event
     */
    @EventHandler
    public void onInteract(final PlayerInteractEvent event) {
        final Player bukkitPlayer = event.getPlayer();

        if(bukkitPlayer.getItemInHand() == null)
            return;

        if(!(bukkitPlayer.getItemInHand() instanceof Gun))
            return;

        final Gun gun = (Gun)  bukkitPlayer.getItemInHand();
        if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
            gun.shoot(bukkitPlayer);
        else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
            gun.reload(bukkitPlayer);
    }
}
