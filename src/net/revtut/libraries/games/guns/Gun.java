package net.revtut.libraries.games.guns;

import net.revtut.libraries.Libraries;
import net.revtut.libraries.games.events.gun.GunFireEvent;
import net.revtut.libraries.games.events.gun.GunHitEvent;
import net.revtut.libraries.games.events.gun.GunReloadEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Gun Object
 */
public abstract class Gun extends ItemStack {

    /**
     * Name of the gun
     */
    private String name;

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
     * Size of the magazine
     */
    private int magazineSize;

    /**
     * Number of bullets per shot
     */
    private int bulletsPerShot;

    /**
     * Bullet of the gun
     */
    private Bullet bullet;

    /**
     * Constructor of Gun
     * @param name name of the gun
     * @param appearance appearance of the gun
     * @param fireRate fire rate of the gun
     * @param reloadTime reload time of the gun
     * @param muzzleVelocity muzzle velocity
     * @param accuracy accuracy of the gun
     * @param recoil recoil of the gun
     * @param magazineSize size of the magazine
     * @param bulletsPerShot number of bullets per shot
     * @param bullet bullet of the gun
     */
    public Gun(String name, ItemStack appearance, int fireRate, int reloadTime, int muzzleVelocity, float accuracy, float recoil, int magazineSize, int bulletsPerShot, Bullet bullet) {
        super(appearance.getType(), appearance.getAmount(), appearance.getDurability());

        this.name = name;
        this.fireRate = fireRate;
        this.reloadTime = reloadTime;
        this.muzzleVelocity = muzzleVelocity;
        this.accuracy = accuracy;
        this.recoil = recoil;
        this.magazineSize = magazineSize;
        this.bulletsPerShot = bulletsPerShot;
        this.bullet = bullet;

        GunManager.getInstance().addGun(this);
    }

    /**
     * Get the name of the gun
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Get the item stack of the gun
     * @return item stack of the gun
     */
    public ItemStack getItemStack() {
        return this;
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
     * Get the bullet of the gun
     * @return bullet of the gun
     */
    public Bullet getBullet() {
        return bullet;
    }

    /**
     * Shoot the gun
     * @param shooter player to shoot from
     */
    public void shoot(Player shooter) {
        GunManager gunManager = GunManager.getInstance();

        // Check if player can shoot
        long lastShot = gunManager.getLastShot(shooter);
        if(lastShot != -1) {
            long currentTime = System.nanoTime();
            long delayPerShot = getFireRate() / 60000000000l; // Delay between each shot in nanoseconds

            if(currentTime - lastShot < delayPerShot)
                return;
        }
        int currentSizeMagazine = gunManager.getCurrentMagSize(shooter);
        if(currentSizeMagazine != -1) {
            if(currentSizeMagazine <= 0)
                return;
        } else
            currentSizeMagazine = getMagazineSize();

        // Call event
        GunFireEvent event = new GunFireEvent(shooter, this);
        Bukkit.getPluginManager().callEvent(event);

        if(event.isCancelled())
            return;

        // Shoot the gun
        for(int i = 0; i < getBulletsPerShot(); i++) {
            Vector direction = shooter.getEyeLocation().getDirection();
            direction.add(new Vector(Math.random() * getAccuracy() - getAccuracy(), Math.random() * getAccuracy() - getAccuracy(), Math.random() * getAccuracy() - getAccuracy()));

            Projectile projectile = shooter.launchProjectile(getBullet().getProjectile(), direction.multiply(getMuzzleVelocity()));
            projectile.setCustomName(getBullet().getName());
            projectile.setCustomNameVisible(false);
            GunManager.getInstance().addProjectile(projectile, shooter);
        }

        // Apply recoil
        Location location = shooter.getLocation();
        location.setPitch(location.getPitch() * getRecoil());
        shooter.teleport(location);

        // Add to maps
        gunManager.setCurrentMagSize(shooter, --currentSizeMagazine);
        gunManager.setLastShot(shooter, System.nanoTime());
    }

    /**
     * On hit by a bullet
     * @param shooter player that shot the gun
     * @param entity entity that was hit
     * @param projectile projectile that hit
     */
    public void onHit(Player shooter, Entity entity, Projectile projectile) {
        GunManager.getInstance().removeProjectile(projectile);

        // Call event
        GunHitEvent event = new GunHitEvent(shooter, entity, this);
        Bukkit.getPluginManager().callEvent(event);

        if(event.isCancelled())
            return;

        // Knockback
        entity.setVelocity(entity.getLocation().getDirection().multiply(-1 * getBullet().getKnockback()));

        // Apply damage
        if(!(entity instanceof Damageable))
            return;
        Damageable entityDamageable = (Damageable) entity;
        entityDamageable.damage(Math.random() * (getBullet().getMaxDamage() - getBullet().getMinDamage()) + getBullet().getMinDamage());
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

        GunManager.getInstance().setCurrentMagSize(player, 0); // Prevent shoot when gun is reloading
        Bukkit.getScheduler().runTaskLater(Libraries.getInstance(), () -> {
            GunManager.getInstance().setCurrentMagSize(player, getMagazineSize());
        }, getReloadTime());
    }

    /**
     * Convert a gun to string
     * @return converted string
     */
    @Override
    public String toString() {
        return "Gun{" +
                "name='" + name + '\'' +
                ", fireRate=" + fireRate +
                ", reloadTime=" + reloadTime +
                ", muzzleVelocity=" + muzzleVelocity +
                ", accuracy=" + accuracy +
                ", recoil=" + recoil +
                ", magazineSize=" + magazineSize +
                ", bulletsPerShot=" + bulletsPerShot +
                ", bullet=" + bullet +
                '}';
    }
}