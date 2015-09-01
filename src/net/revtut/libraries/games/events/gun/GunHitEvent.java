package net.revtut.libraries.games.events.gun;

import net.revtut.libraries.games.guns.Gun;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * Gun Hit Event
 */
public class GunHitEvent extends GunEvent implements Cancellable {

    /**
     * Entity that was hit
     */
    private Entity victim;

    /**
     * Constructor of GunHitEvent
     * @param shooter player that used the gun
     * @param victim entity that was hit
     * @param gun gun that was used
     */
    public GunHitEvent(Player shooter, Entity victim, Gun gun) {
        super(shooter, gun);
        this.victim = victim;
    }
}
