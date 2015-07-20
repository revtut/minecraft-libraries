package net.revtut.libraries.entities;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import net.revtut.libraries.reflection.ReflectionAPI;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Entities API.
 *
 * <P>Help manage entities</P>
 *
 * @author João Silva
 * @version 1.0
 */
public class EntitiesAPI {

    /**
     * Goal selector item of path finder
     */
    private static Field gsa;

    /**
     * Goal selector of the entity
     */
    private static Field goalSelector;

    /**
     * Target selector of the entity
     */
    private static Field targetSelector;

    /**
     * Initialize variables
     */
    static {
        gsa = ReflectionAPI.getField(PathfinderGoalSelector.class, "b");
        if(gsa != null)
            gsa.setAccessible(true);

        goalSelector = ReflectionAPI.getField(EntityInsentient.class, "goalSelector");
        if(goalSelector != null)
            goalSelector.setAccessible(true);

        targetSelector = ReflectionAPI.getField(EntityInsentient.class, "targetSelector");
        if(targetSelector != null)
            targetSelector.setAccessible(true);
    }

    /**
     * Rename a entity
     * @param entity entity to be renamed
     * @param name new name of the entity
     */
    public static void renameEntity(Entity entity, String name) {
        entity.setCustomNameVisible(true);
        entity.setCustomName(name);
    }

    /**
     * Spawn a entity on a given location
     * @param entityType entity type to spawn
     * @param location location to spawn
     * @return spawned entity
     */
    public static Entity spawnEntity(EntityType entityType, Location location) {
        return location.getWorld().spawnEntity(location, entityType);
    }

    /**
     * Make a entity follow a player
     * @param entity entity to follow
     * @param player player to be followed
     */
    public static void entityFollow(LivingEntity entity, UUID player) {
        try {
            Object nmsEntity = ((CraftLivingEntity) entity).getHandle();

            if (nmsEntity instanceof EntityInsentient) {
                PathfinderGoalSelector goal = (PathfinderGoalSelector) goalSelector.get(nmsEntity);
                PathfinderGoalSelector target = (PathfinderGoalSelector) targetSelector.get(nmsEntity);

                gsa.set(goal, new UnsafeList<>());
                gsa.set(target, new UnsafeList<>());

                goal.a(0, new PathfinderGoalFloat((EntityInsentient) nmsEntity));
                goal.a(1, new PathfinderGoalWalktoTile((EntityInsentient) nmsEntity, player));
                System.out.println("Here");
            } else {
                throw new IllegalArgumentException(entity.getType().getName() + " is not an instance of an EntityInsentient.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Freeze a entity
     * @param entity entity to be frozen
     */
    public static void freezeEntity(LivingEntity entity) {
        try {
            Object nmsEntity = ((CraftLivingEntity) entity).getHandle();

            if (nmsEntity instanceof EntityInsentient) {
                PathfinderGoalSelector goal = (PathfinderGoalSelector) goalSelector.get(nmsEntity);
                PathfinderGoalSelector target = (PathfinderGoalSelector) targetSelector.get(nmsEntity);

                gsa.set(goal, new UnsafeList<>());
                gsa.set(target, new UnsafeList<>());
            } else {
                throw new IllegalArgumentException(entity.getType().getName() + " is not an instance of an EntityInsentient.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
