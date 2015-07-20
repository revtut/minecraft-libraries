package net.revtut.libraries.entities;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
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
        try {
            gsa = PathfinderGoalSelector.class.getDeclaredField("b");
            gsa.setAccessible(true);

            goalSelector = EntityInsentient.class.getDeclaredField("goalSelector");
            goalSelector.setAccessible(true);

            targetSelector = EntityInsentient.class.getDeclaredField("targetSelector");
            targetSelector.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            } else {
                throw new IllegalArgumentException(entity.getType().getName() + " is not an instance of an EntityInsentient.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
