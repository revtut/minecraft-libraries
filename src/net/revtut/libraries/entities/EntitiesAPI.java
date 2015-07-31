package net.revtut.libraries.entities;

import net.minecraft.server.v1_8_R3.*;
import net.revtut.libraries.entities.goals.PetGoalFollowEntity;
import net.revtut.libraries.entities.goals.PetGoalLookEntity;
import net.revtut.libraries.entities.goals.PetGoalRideByEntity;
import net.revtut.libraries.reflection.ReflectionAPI;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Entities API.
 *
 * <P>Help manage entities</P>
 *
 * @author João Silva
 * @version 1.0
 */
public final class EntitiesAPI {

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
     * @param target target to be followed
     * @param lookTarget true if want the entity to look at the target
     */
    public static void entityFollow(Entity entity, Entity target, boolean lookTarget) {
        EntityLiving entityLiving = ((CraftLivingEntity) entity).getHandle();

        if (entityLiving instanceof EntityInsentient) {
            EntityInsentient entityInsentient = (EntityInsentient) entityLiving;

            // Clear goals
            clearGoals(entity);
            clearTargets(entity);

            // Add goals
            addGoal(entity, new PathfinderGoalFloat(entityInsentient), 0);
            addGoal(entity, new PetGoalFollowEntity(entityInsentient, ((CraftLivingEntity) target).getHandle(), 2.5D, 3.0F, 1.0F, 10.0F), 1);

            if(lookTarget)
                addGoal(entity, new PetGoalLookEntity(entityInsentient, ((CraftLivingEntity) target).getHandle(), 3.0F), 2);
        } else {
            throw new IllegalArgumentException(entity.getType().getName() + " is not an instance of an EntityInsentient.");
        }
    }

    /**
     * Freeze a entity
     * @param entity entity to be frozen
     */
    public static void freezeEntity(Entity entity) {
        clearGoals(entity);
        clearTargets(entity);
    }

    /**
     * Ride a entity
     * @param entity entity to be ridden
     * @param rider rider of the entity
     */
    public static void rideEntity(Entity entity, Entity rider) {
        // Set passenger
        entity.setPassenger(rider);

        EntityLiving entityLiving = ((CraftLivingEntity) entity).getHandle();

        if (entityLiving instanceof EntityInsentient) {
            EntityInsentient entityInsentient = (EntityInsentient) entityLiving;

            // Clear goals
            clearGoals(entity);
            clearTargets(entity);

            // Add goals
            addGoal(entity, new PathfinderGoalFloat(entityInsentient), 0);
            addGoal(entity, new PetGoalRideByEntity(entityInsentient, ((CraftLivingEntity) rider).getHandle(), 0.4F, 0.5F), 1); // Default speed is 0.2 and jump 0.5
        } else {
            throw new IllegalArgumentException(entity.getType().getName() + " is not an instance of an EntityInsentient.");
        }
    }

    /**
     * Clear goals of a entity
     * @param entity entity to be cleared
     */
    public static void clearGoals(Entity entity) {
        EntityLiving entityLiving = ((CraftLivingEntity) entity).getHandle();

        try {
            if (entityLiving instanceof EntityInsentient) {
                EntityInsentient entityInsentient = (EntityInsentient) entityLiving;

                // Get "b"
                Field bSelector = ReflectionAPI.getField(PathfinderGoalSelector.class, "b");
                if(bSelector == null)
                    return;
                bSelector.setAccessible(true);

                // Clear goals
                bSelector.set(entityInsentient.goalSelector, new UnsafeList<>());
            } else {
                throw new IllegalArgumentException(entity.getType().getName() + " is not an instance of an EntityInsentient.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Clear the targets of a entity
     * @param entity entity to be cleared
     */
    public static void clearTargets(Entity entity) {
        EntityLiving entityLiving = ((CraftLivingEntity) entity).getHandle();

        try {
            if (entityLiving instanceof EntityInsentient) {
                EntityInsentient entityInsentient = (EntityInsentient) entityLiving;

                // Get "b"
                Field bSelector = ReflectionAPI.getField(PathfinderGoalSelector.class, "b");
                if(bSelector == null)
                    return;
                bSelector.setAccessible(true);

                // Clear targets
                bSelector.set(entityInsentient.targetSelector, new UnsafeList<>());
            } else {
                throw new IllegalArgumentException(entity.getType().getName() + " is not an instance of an EntityInsentient.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a goal to a entity
     * @param entity entity to receive the goal
     * @param goal goal to be added
     * @param priority priority of the goal
     */
    public static void addGoal(Entity entity, PathfinderGoal goal, int priority) {
        EntityLiving entityLiving = ((CraftLivingEntity) entity).getHandle();

        try {
            if (entityLiving instanceof EntityInsentient) {
                EntityInsentient entityInsentient = (EntityInsentient) entityLiving;

                // Add goal
                entityInsentient.goalSelector.a(priority, goal);
            } else {
                throw new IllegalArgumentException(entity.getType().getName() + " is not an instance of an EntityInsentient.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Register a custom entity
     * @param name name of the entity
     * @param id id of the entity
     * @param customClass custom class for the entity
     */
    public static void registerEntity(String name, int id, Class<?> customClass) {
        try {
            List<Map<Object, Object>> dataMaps = new ArrayList<>();
            Field[] arrayOfField;
            int j = (arrayOfField = EntityTypes.class.getDeclaredFields()).length;
            for (int i = 0; i < j; i++) {
                Field field = arrayOfField[i];
                if (field.getType().getSimpleName().equals(Map.class.getSimpleName())) {
                    field.setAccessible(true);
                    dataMaps.add((Map<Object, Object>) field.get(null));
                }
            }

            dataMaps.get(1).put(customClass, name);
            dataMaps.get(3).put(customClass, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
