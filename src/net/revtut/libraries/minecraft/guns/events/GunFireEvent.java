package net.revtut.libraries.minecraft.guns.events;

import net.revtut.libraries.minecraft.guns.Gun;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;

/**
 * Gun Fire Event
 */
public class GunFireEvent extends GunEvent implements Cancellable {

    /**
     * Constructor of GunFireEvent
     * @param shooter player that used the gun
     * @param gun gun that was used
     */
    public GunFireEvent(final LivingEntity shooter, final Gun gun) {
        super(shooter, gun);
    }
}
