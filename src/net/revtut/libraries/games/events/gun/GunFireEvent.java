package net.revtut.libraries.games.events.gun;

import net.revtut.libraries.games.guns.Gun;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
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
    public GunFireEvent(final Entity shooter, final Gun gun) {
        super(shooter, gun);
    }
}
