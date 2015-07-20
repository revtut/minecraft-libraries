package net.revtut.libraries.entities;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.NavigationAbstract;
import net.minecraft.server.v1_8_R3.PathEntity;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.UUID;

/**
 * Path Finder Goal Walk To Tile.
 *
 * <P>Help manage path finder of a entity</P>
 *
 * @author João Silva
 * @version 1.0
 */
public class PathfinderGoalWalktoTile extends PathfinderGoal {

    /**
     * Entity of the path finder
     */
    private EntityInsentient entity;

    /**
     * Path of the entity
     */
    private PathEntity path;

    /**
     * UUID of the player to be followed
     */
    private UUID player;

    /**
     * Constructor of PathfinderGoalWalktoTile
     * @param entity entity of the path finder
     * @param player player to be followed
     */
    public PathfinderGoalWalktoTile(EntityInsentient entity, UUID player) {
        this.entity = entity;
        this.player = player;
    }

    /**
     * Validate if path is valid
     * @return true if valid false otherwise
     */
    @Override
    public boolean a() {
        if (Bukkit.getPlayer(player) == null) {
            return path != null;
        }
        Location targetLocation = Bukkit.getPlayer(player).getLocation();

        NavigationAbstract navigation = this.entity.getNavigation();
        this.path = navigation.a(targetLocation.getX() + 1, targetLocation.getY(), targetLocation.getZ() + 1);

        if (this.path != null) {
            this.c();
        }

        return this.path != null;
    }

    /**
     * Make entity move to path
     */
    @Override
    public void c() {
        this.entity.getNavigation().a(this.path, 1D);
    }
}
