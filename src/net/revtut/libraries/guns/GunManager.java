package net.revtut.libraries.guns;

import net.revtut.libraries.Libraries;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;

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
    private final List<Gun> guns;

    /**
     * Map with number of bullets left on the magazine per player
     */
    private final Map<UUID, Integer> currentMagSize;

    /**
     * Map with all shot projectiles (Projectile => Player)
     */
    private final Map<UUID, UUID> projectiles;

    /**
     * Map with last shot time per player
     */
    private final Map<UUID, Long> lastShot;

    /**
     * Constructor of GunManager
     */
    private GunManager() {
        guns = new ArrayList<>();
        currentMagSize = new HashMap<>();
        projectiles = new HashMap<>();
        lastShot = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(new GunListener(), Libraries.getInstance());
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
    public Gun getGun(final String name) {
        for(final Gun gun : guns)
            if(gun.getName().equalsIgnoreCase(name))
                return gun;
        return null;
    }

    /**
     * Get the time of the last shot of a entity
     * @param entity entity to get the last shot
     * @return last shot of the entity
     */
    public long getLastShot(final Entity entity) {
        if(!lastShot.containsKey(entity.getUniqueId()))
            return -1;
        return lastShot.get(entity.getUniqueId());
    }

    /**
     * Get the current magazine size of the gun
     * @param entity entity to get
     * @return current magazine size
     */
    public int getCurrentMagSize(final Entity entity) {
        if(!currentMagSize.containsKey(entity.getUniqueId()))
            return -1;
        return currentMagSize.get(entity.getUniqueId());
    }

    /**
     * Set the time of the last shot of the entity
     * @param entity entity to be set
     * @param time time of the shot
     */
    public void setLastShot(final Entity entity, final long time) {
        lastShot.put(entity.getUniqueId(), time);
    }

    /**
     * Set the current magazine size of the entity
     * @param entity entity to be set
     * @param magSize size of the magazine
     */
    public void setCurrentMagSize(final Entity entity, final int magSize) {
        currentMagSize.put(entity.getUniqueId(), magSize);
    }

    /**
     * Add a gun to the list
     * @param gun gun to be added
     */
    public void addGun(final Gun gun) {
        guns.add(gun);
    }

    /**
     * Remove a gun from the list
     * @param gun gun to be removed
     */
    public void removeGun(final Gun gun) {
        guns.remove(gun);
    }

    /**
     * Add a projectile to the map
     * @param projectile projectile to be added
     * @param entity entity who shot the projectile
     */
    public void addProjectile(final Projectile projectile, final Entity entity){
        projectiles.put(projectile.getUniqueId(), entity.getUniqueId());
    }

    /**
     * Remove a projectile from the map
     * @param projectile projectile to be removed
     */
    public void removeProjectile(final Projectile projectile) {
        projectiles.remove(projectile.getUniqueId());
    }

    /**
     * Check if a projectile is a bullet
     * @param projectile projectile to check
     * @return true if contains, false otherwise
     */
    public boolean isBullet(final Projectile projectile) {
        return projectiles.containsKey(projectile.getUniqueId());
    }
}
