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
     * Get the time of the last shot of a player
     * @param player player to get the last shot
     * @return last shot of the player
     */
    public long getLastShot(Player player) {
        if(!lastShot.containsKey(player.getUniqueId()))
            return -1;
        return lastShot.get(player.getUniqueId());
    }

    /**
     * Get the current magazine size of the gun
     * @param player player to get
     * @return current magazine size
     */
    public int getCurrentMagSize(Player player) {
        if(!currentMagSize.containsKey(player.getUniqueId()))
            return -1;
        return currentMagSize.get(player.getUniqueId());
    }

    /**
     * Set the time of the last shot of the player
     * @param player player to be set
     * @param time time of the shot
     */
    public void setLastShot(Player player, long time) {
        lastShot.put(player.getUniqueId(), time);
    }

    /**
     * Set the current magazine size of the player
     * @param player player to be set
     * @param magSize size of the magazine
     */
    public void setCurrentMagSize(Player player, int magSize) {
        currentMagSize.put(player.getUniqueId(), magSize);
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
     * Add a projectile to the map
     * @param projectile projectile to be added
     * @param player player who shot the projectile
     */
    public void addProjectile(Projectile projectile, Player player){
        projectiles.put(projectile.getUniqueId(), player.getUniqueId());
    }

    /**
     * Remove a projectile from the map
     * @param projectile projectile to be removed
     */
    public void removeProjectile(Projectile projectile) {
        projectiles.remove(projectile.getUniqueId());
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
