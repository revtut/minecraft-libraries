package net.revtut.libraries.games.guns;

import net.minecraft.server.v1_8_R3.ItemStack;
import net.revtut.libraries.Libraries;
import net.revtut.libraries.games.events.gun.GunFireEvent;
import net.revtut.libraries.games.events.gun.GunHitEvent;
import net.revtut.libraries.games.events.gun.GunReloadEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Gun Object
 */
public class Gun {

    /**
     * Name of the gun
     */
    private String name;

    /**
     * Appearance of the gun
     */
    private ItemStack appearance;

    /**
     * Fire rate in RPM (rounds per minute)
     */
    private int fireRate;

    /**
     * Reload time of the gun in seconds
     */
    private int reloadTime;

    /**
     * Speed that the projectile has when leaves the muzzle
     */
    private int muzzleVelocity;

    /**
     * Accuracy of the gun
     */
    private float accuracy;

    /**
     * Shoot recoil
     */
    private float recoil;

    /**
     * Knockback of the bullet
     */
    private float knockback;

    /**
     * Size of the magazine
     */
    private int magazineSize;

    /**
     * Number of bullets per shot
     */
    private int bulletsPerShot;

    /**
     * Minimum damage of the weapon
     */
    private float minDamage;

    /**
     * Maximum damage of the weapon
     */
    private float maxDamage;

    /**
     * Bullet class
     */
    private Class<? extends Projectile> bullet;

    /**
     * Map with number of bullets left on the magazine per player
     */
    private Map<UUID, Integer> currentMagSize;

    /**
     * Map with last shot time per player
     */
    private Map<UUID, Long> lastShot;

    /**
     * Constructor of Gun
     * @param name name of the gun
     * @param appearance appearance of the gun
     * @param fireRate fire rate of the gun
     * @param reloadTime reload time of the gun
     * @param muzzleVelocity muzzle velocity
     * @param accuracy accuracy of the gun
     * @param recoil recoil of the gun
     * @param knockback knockback on hit of the bullet
     * @param magazineSize size of the magazine
     * @param bulletsPerShot number of bullets per shot
     * @param minDamage minimum damage per shot
     * @param maxDamage maximum damage per shot
     * @param bullet bullet type
     */
    public Gun(String name, ItemStack appearance, int fireRate, int reloadTime, int muzzleVelocity, float accuracy, float recoil, float knockback, int magazineSize, int bulletsPerShot, float minDamage, float maxDamage, Class<? extends Projectile> bullet) {
        this.name = name;
        this.appearance = appearance;
        this.fireRate = fireRate;
        this.reloadTime = reloadTime;
        this.muzzleVelocity = muzzleVelocity;
        this.accuracy = accuracy;
        this.recoil = recoil;
        this.knockback = knockback;
        this.magazineSize = magazineSize;
        this.bulletsPerShot = bulletsPerShot;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.bullet = bullet;
        this.currentMagSize = new HashMap<>();
        this.lastShot = new HashMap<>();
    }

    /**
     * Get the name of the gun
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Get the appearance item of the gun
     * @return appearance item of the gun
     */
    public ItemStack getAppearance() {
        return appearance;
    }

    /**
     * Get the fire rate of the gun in RPM (rounds per minute)
     * @return fire rate of the gun
     */
    public int getFireRate() {
        return fireRate;
    }

    /**
     * Get the reload time of the gun
     * @return reload time of the gun
     */
    public int getReloadTime() {
        return reloadTime;
    }

    /**
     * Get the bullet velocity when leaving the muzzle
     * @return bullet velocity when leaving the muzzle
     */
    public int getMuzzleVelocity() {
        return muzzleVelocity;
    }

    /**
     * Get the accuracy of the gun
     * @return accuracy of the gun
     */
    public float getAccuracy() {
        return accuracy;
    }

    /**
     * Get the recoil of the gun
     * @return recoil of the gun
     */
    public float getRecoil() {
        return recoil;
    }

    /**
     * Get the knockback of the gun
     * @return knockback of the gun
     */
    public float getKnockback() {
        return knockback;
    }

    /**
     * Get the size of the magazine
     * @return size of the magazine
     */
    public int getMagazineSize() {
        return magazineSize;
    }

    /**
     * Get the number of bullets per shot
     * @return number of bullets per shot
     */
    public int getBulletsPerShot() {
        return bulletsPerShot;
    }

    /**
     * Get the minimum damage per shot
     * @return minimum damage per shot
     */
    public float getMinDamage() {
        return minDamage;
    }

    /**
     * Get the maximum damage per shot
     * @return maximum damage per shot
     */
    public float getMaxDamage() {
        return maxDamage;
    }

    /**
     * Get the bullet projectile class
     * @return bullet projectile class
     */
    public Class<? extends Projectile> getBullet() {
        return bullet;
    }

    /**
     * Shoot the gun
     * @param shooter player to shoot from
     */
    public void shoot(Player shooter) {
        // Check if player can shoot
        if(lastShot.containsKey(shooter.getUniqueId())) {
            long currentTime = System.nanoTime();
            long lastTime = lastShot.get(shooter.getUniqueId());
            long delayPerShot = fireRate / 60000000000l; // Delay between each shot in nanoseconds

            if(currentTime - lastTime < delayPerShot)
                return;
        }
        int currentSizeMagazine = magazineSize;
        if(currentMagSize.containsKey(shooter.getUniqueId())) {
            currentSizeMagazine = currentMagSize.get(shooter.getUniqueId());
            if(currentSizeMagazine <= 0)
                return;
        }

        // Call event
        GunFireEvent event = new GunFireEvent(shooter, this);
        Bukkit.getPluginManager().callEvent(event);

        if(event.isCancelled())
            return;

        // Shoot the gun
        for(int i = 0; i < bulletsPerShot; i++) {
            Vector direction = shooter.getEyeLocation().getDirection();
            direction.add(new Vector(Math.random() * accuracy - accuracy, Math.random() * accuracy - accuracy, Math.random() * accuracy - accuracy));

            shooter.launchProjectile(bullet, direction.multiply(muzzleVelocity));
        }

        // Apply recoil
        Location location = shooter.getLocation();
        location.setPitch(location.getPitch() * recoil);
        shooter.teleport(location);

        // Add to maps
        currentMagSize.put(shooter.getUniqueId(), --currentSizeMagazine);
        lastShot.put(shooter.getUniqueId(), System.nanoTime());
    }

    /**
     * On hit by a bullet
     * @param shooter player that shot the gun
     * @param entity entity that was hit
     */
    public void onHit(Player shooter, Entity entity) {
        // Call event
        GunHitEvent event = new GunHitEvent(shooter, entity, this);
        Bukkit.getPluginManager().callEvent(event);

        if(event.isCancelled())
            return;

        entity.setVelocity(entity.getLocation().getDirection().multiply(-1 * knockback));
    }

    /**
     * Reload the gun
     * @param player player that is reloading the gun
     */
    public void reload(Player player) {
        // Call event
        GunReloadEvent event = new GunReloadEvent(player, this);
        Bukkit.getPluginManager().callEvent(event);

        if(event.isCancelled())
            return;

        Bukkit.getScheduler().runTaskLater(Libraries.getInstance(), () -> {
            currentMagSize.put(player.getUniqueId(), magazineSize);
        }, reloadTime);
    }

    /**
     * Convert a gun to string
     * @return converted string
     */
    @Override
    public String toString() {
        return "Gun{" +
                "name='" + name + '\'' +
                ", appearance=" + appearance +
                ", fireRate=" + fireRate +
                ", muzzleVelocity=" + muzzleVelocity +
                ", recoil=" + recoil +
                ", knockback=" + knockback +
                ", magazineSize=" + magazineSize +
                ", bulletsPerShot=" + bulletsPerShot +
                ", minDamage=" + minDamage +
                ", maxDamage=" + maxDamage +
                '}';
    }
}
