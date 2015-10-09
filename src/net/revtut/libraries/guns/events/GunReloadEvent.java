package net.revtut.libraries.guns.events;

import net.revtut.libraries.guns.Gun;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;

/**
 * Gun Reload Event
 */
public class GunReloadEvent extends GunEvent implements Cancellable {

    /**
     * Constructor of GunReloadEvent
     * @param shooter player that used the gun
     * @param gun gun that was used
     */
    public GunReloadEvent(final LivingEntity shooter, final Gun gun) {
        super(shooter, gun);
    }
}
