package net.revtut.libraries.games.events.gun;

import net.revtut.libraries.games.guns.Gun;
import org.bukkit.entity.Player;
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
    public GunReloadEvent(Player shooter, Gun gun) {
        super(shooter, gun);
    }
}
