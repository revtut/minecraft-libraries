package net.revtut.libraries.games.guns;

import org.bukkit.entity.Projectile;

/**
 * Bullet Object.
 */
public abstract class Bullet {

    /**
     * Name of the bullet
     */
    private String name;

    /**
     * Projectile of the bullet
     */
    private Class<? extends Projectile> projectile;

    /**
     * Minimum damage of the weapon
     */
    private float minDamage;

    /**
     * Maximum damage of the weapon
     */
    private float maxDamage;

    /**
     * Knockback of the bullet
     */
    private float knockback;

    /**
     * Constructor of Bullet
     * @param name name of the bullet
     * @param projectile projectile of the bullet
     * @param minDamage minimum damage per shot
     * @param maxDamage maximum damage per shot
     * @param knockback knockback on hit of the bullet
     */
    public Bullet(String name, Class<? extends Projectile> projectile, float minDamage, float maxDamage, float knockback) {
        this.name = name;
        this.projectile = projectile;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.knockback = knockback;
    }

    /**
     * Get the name of the bullet
     * @return name of the bullet
     */
    public String getName() {
        return name;
    }

    /**
     * Get the projectile of the bullet
     * @return projectile of the bullet
     */
    public Class<? extends Projectile> getProjectile() {
        return projectile;
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
     * Get the knockback of the gun
     * @return knockback of the gun
     */
    public float getKnockback() {
        return knockback;
    }
}