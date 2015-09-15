package net.revtut.libraries.games.guns;

import net.revtut.libraries.Libraries;
import net.revtut.libraries.games.events.gun.GunFireEvent;
import net.revtut.libraries.games.events.gun.GunHitEvent;
import net.revtut.libraries.games.events.gun.GunReloadEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;

import java.util.*;

/**
 * Manager of the guns
 */
public class GunManager {

    /**
     * Gun manager singleton
     */
    private static GunManager gunManager;

    /**
     * List with all the guns available
     */
    private List<Gun> guns;

    /**
     * Map with number of bullets left on the magazine per player
     */
    private Map<UUID, Integer> currentMagSize;

    /**
     * Map with all shot projectiles (Projectile => Player)
     */
    private Map<UUID, UUID> projectiles;

    /**
     * Map with last shot time per player
     */
    private Map<UUID, Long> lastShot;

    /**
     * Constructor of GunManager
     */
    private GunManager() {
        guns = new ArrayList<>();
        currentMagSize = new HashMap<>();
        projectiles = new HashMap<>();
        lastShot = new HashMap<>();
    }

    /**
     * Get the instance of GunManager
     * @return instance of GunManager
     */
    public static GunManager getInstance() {
        if(gunManager == null)
            gunManager = new GunManager();
        return gunManager;
    }

    /**
     * Get a gun by its name
     * @param name name of the gun
     * @return gun with that name
     */
    public Gun getGun(String name) {
        for(Gun gun : guns)
            if(gun.getName().equalsIgnoreCase(name))
                return gun;
        return null;
    }

    /**
     * Add a gun to the list
     * @param gun gun to be added
     */
    public void addGun(Gun gun) {
        guns.add(gun);
    }

    /**
     * Remove a gun from the list
     * @param gun gun to be removed
     */
    public void removeGun(Gun gun) {
        guns.remove(gun);
    }

    /**
     * Shoot the gun
     * @param gun gun to shoot the bullet
     * @param shooter player to shoot from
     */
    public void shoot(Gun gun, Player shooter) {
        // Check if player can shoot
        if(lastShot.containsKey(shooter.getUniqueId())) {
            long currentTime = System.nanoTime();
            long lastTime = lastShot.get(shooter.getUniqueId());
            long delayPerShot = gun.getFireRate() / 60000000000l; // Delay between each shot in nanoseconds

            if(currentTime - lastTime < delayPerShot)
                return;
        }
        int currentSizeMagazine = gun.getMagazineSize();
        if(currentMagSize.containsKey(shooter.getUniqueId())) {
            currentSizeMagazine = currentMagSize.get(shooter.getUniqueId());
            if(currentSizeMagazine <= 0)
                return;
        }

        // Call event
        GunFireEvent event = new GunFireEvent(shooter, gun);
        Bukkit.getPluginManager().callEvent(event);

        if(event.isCancelled())
            return;

        // Shoot the gun
        for(int i = 0; i < gun.getBulletsPerShot(); i++) {
            Vector direction = shooter.getEyeLocation().getDirection();
            direction.add(new Vector(Math.random() * gun.getAccuracy() - gun.getAccuracy(), Math.random() * gun.getAccuracy() - gun.getAccuracy(), Math.random() * gun.getAccuracy() - gun.getAccuracy()));

            Projectile projectile = shooter.launchProjectile(gun.getBullet().getProjectile(), direction.multiply(gun.getMuzzleVelocity()));
            projectile.setCustomName(gun.getBullet().getName());
            projectile.setCustomNameVisible(false);
            projectiles.put(projectile.getUniqueId(), shooter.getUniqueId());
        }

        // Apply recoil
        Location location = shooter.getLocation();
        location.setPitch(location.getPitch() * gun.getRecoil());
        shooter.teleport(location);

        // Add to maps
        currentMagSize.put(shooter.getUniqueId(), --currentSizeMagazine);
        lastShot.put(shooter.getUniqueId(), System.nanoTime());
    }

    /**
     * On hit by a bullet
     * @param gun gun where the bullet came from
     * @param shooter player that shot the gun
     * @param entity entity that was hit
     */
    public void onHit(Gun gun, Player shooter, Entity entity) {
        // Call event
        GunHitEvent event = new GunHitEvent(shooter, entity, gun);
        Bukkit.getPluginManager().callEvent(event);

        if(event.isCancelled())
            return;

        // Knockback
        entity.setVelocity(entity.getLocation().getDirection().multiply(-1 * gun.getBullet().getKnockback()));

        // Apply damage
        if(!(entity instanceof Damageable))
            return;
        Damageable entityDamageable = (Damageable) entity;
        entityDamageable.damage(Math.random() * (gun.getBullet().getMaxDamage() - gun.getBullet().getMinDamage()) + gun.getBullet().getMinDamage());
    }

    /**
     * Reload the gun
     * @param gun gun to be reloaded
     * @param player player that is reloading the gun
     */
    public void reload(Gun gun, Player player) {
        // Call event
        GunReloadEvent event = new GunReloadEvent(player, gun);
        Bukkit.getPluginManager().callEvent(event);

        if(event.isCancelled())
            return;

        Bukkit.getScheduler().runTaskLater(Libraries.getInstance(), () -> {
            currentMagSize.put(player.getUniqueId(), gun.getMagazineSize());
        }, gun.getReloadTime());
    }

    /**
     * Check if a projectile is a bullet
     * @param projectile
     * @return
     */
    public boolean isBullet(Projectile projectile) {
        return projectiles.containsKey(projectile);
    }
}
