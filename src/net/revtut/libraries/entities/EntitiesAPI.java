package net.revtut.libraries.entities;

import com.google.common.reflect.Reflection;
import net.minecraft.server.v1_8_R3.*;
import net.revtut.libraries.entities.goals.PetGoalFollowOwner;
import net.revtut.libraries.reflection.ReflectionAPI;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;

import java.lang.reflect.Field;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public static void entityFollow(Entity entity, Player player) {
        EntityLiving entityLiving = ((CraftLivingEntity) entity).getHandle();

        try {
            if (entityLiving instanceof EntityInsentient) {
                EntityCreature entityInsentient = (EntityCreature) entityLiving;

                // Get "b"
                Field bSelector = ReflectionAPI.getField(PathfinderGoalSelector.class, "b");
                if(bSelector == null)
                    return;
                bSelector.setAccessible(true);

                // Clear goals
                bSelector.set(entityInsentient.goalSelector, new UnsafeList<>());

                // Clear target
                bSelector.set(entityInsentient.targetSelector, new UnsafeList<>());

                System.out.println("Adding goals...");

                // Add goals
                entityInsentient.goalSelector.a(1, new PetGoalFollowOwner(entityInsentient, ((CraftPlayer) player).getHandle(), 1.0D, 5.0F, 3.0F, 10.0F));

                // Check if was added
                List object = (List) bSelector.get(entityInsentient.goalSelector);
                System.out.println("Size of the list: " + object.size());
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
        EntityLiving entityLiving = ((CraftLivingEntity) entity).getHandle();

        try {
            if (entityLiving instanceof EntityInsentient) {
                EntityInsentient entityInsentient = (EntityInsentient) entityLiving;

                // Clear goals
                Field goalSelector = ReflectionAPI.getField(PathfinderGoalSelector.class, "b");
                if(goalSelector == null)
                    return;

                goalSelector.setAccessible(true);
                goalSelector.set(entityInsentient.goalSelector, new UnsafeList<>());

                // Clear target
                Field targetSelector = ReflectionAPI.getField(PathfinderGoalSelector.class, "b");
                if(targetSelector == null)
                    return;

                targetSelector.setAccessible(true);
                targetSelector.set(entityInsentient.targetSelector, new UnsafeList<>());
            } else {
                throw new IllegalArgumentException(entity.getType().getName() + " is not an instance of an EntityInsentient.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
            List<Map<?, ?>> dataMaps = new ArrayList();
            Field[] arrayOfField;
            int j = (arrayOfField = EntityTypes.class.getDeclaredFields()).length;
            for (int i = 0; i < j; i++) {
                Field f = arrayOfField[i];
                if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
                    f.setAccessible(true);
                    dataMaps.add((Map)f.get(null));
                }
            }

            ((Map)dataMaps.get(1)).put(customClass, name);
            ((Map)dataMaps.get(3)).put(customClass, Integer.valueOf(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
