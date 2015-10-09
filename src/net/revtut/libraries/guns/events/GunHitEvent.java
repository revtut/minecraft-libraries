package net.revtut.libraries.guns.events;

import net.revtut.libraries.guns.Gun;
import net.revtut.libraries.guns.ShotType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;

/**
 * Gun Hit Event
 */
public class GunHitEvent extends GunEvent implements Cancellable {

    /**
     * Entity that was hit
     */
    private final LivingEntity victim;

    /**
     * Type of the shot
     */
    private final ShotType shotType;

    /**
     * Damage multiplier (can be used for example to apply more damage when its headshot)
     */
    private double damageMultiplier;

    /**
     * Constructor of GunHitEvent
     * @param shooter player that used the gun
     * @param victim entity that was hit
     * @param gun gun that was used
     * @param shotType shot type
     */
    public GunHitEvent(final LivingEntity shooter, final LivingEntity victim, final Gun gun, final ShotType shotType) {
        super(shooter, gun);
        this.victim = victim;
        this.shotType = shotType;
        this.damageMultiplier = 1;
    }

    /**
     * Get the victim of the hit
     * @return victim of the hit
     */
    public LivingEntity getVictim() {
        return victim;
    }

    /**
     * Get the shot type
     * @return shot type
     */
    public ShotType getShotType() {
        return shotType;
    }

    /**
     * Get the damage multiplier
     * @return dammage multiplier
     */
    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    /**
     * Set the damage multiplier
     * @param damageMultiplier new damage multiplier value
     */
    public void setDamageMultiplier(final double damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }
}
