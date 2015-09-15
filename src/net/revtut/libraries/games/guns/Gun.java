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
