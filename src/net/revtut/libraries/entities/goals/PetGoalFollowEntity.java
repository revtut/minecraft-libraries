package net.revtut.libraries.entities.goals;

import net.minecraft.server.v1_8_R3.*;
import net.revtut.libraries.entities.PetGoal;

/**
 * Pet goal follow entity
 */
public class PetGoalFollowEntity extends PetGoal {

    /**
     * Pet entity
     */
    private final EntityInsentient pet;

    /**
     * Target of the pet
     */
    private final Entity target;

    /**
     * Follow speed
     */
    private final double speed;

    /**
     * Distance to the target so it start moving
     */
    private final float startDistance;

    /**
     * Distance to the target so it stop moving
     */
    private final float stopDistance;

    /**
     * Maximum distance to the target so it teleports
     */
    private final float maxDistance;

    /**
     * Navigation of the pet
     */
    private final Navigation navigation;

    /**
     * Timer for ticking
     */
    private int timer = 0;

    /**
     * Constructor of PetGoalFollowEntity
     * @param pet pet of the goal
     * @param target target of the pet
     * @param speed speed of the pet
     * @param startDistance start distance so pet follow target
     * @param stopDistance stop distance pet will stop following
     * @param maxDistance maximum distance between target and pet
     */
    public PetGoalFollowEntity(final EntityInsentient pet, final Entity target, final double speed, final float startDistance, final float stopDistance, final float maxDistance) {
        this.pet = pet;
        this.target = target;
        this.speed = speed;
        this.startDistance = startDistance;
        this.stopDistance = stopDistance;
        this.maxDistance = maxDistance;
        this.navigation = (Navigation) pet.getNavigation();
    }

    /**
     * Should start method
     * @return true if should, false otherwise
     */
    public boolean shouldStart() {
        if (!pet.isAlive())
            return false;
        else if (target == null)
            return false;
        else if (pet.h(target) < Math.pow(startDistance, 2))
            return false;
        else if(pet.getGoalTarget() != null)
            if(pet.getGoalTarget().isAlive())
                return false;

        return true;
    }

    /**
     * Should continue method
     * @return true if should, false otherwise
     */
    public boolean shouldContinue() {
        if (navigation.g())
            return false;
        else if (target == null)
            return false;
        else if (pet.h(target) < Math.pow(stopDistance, 2))
            return false;

        return true;
    }

    /**
     * Start method
     */
    @Override
    public void start() {
        this.timer = 0;

        //Set pathfinding radius
        pet.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(maxDistance);
    }

    /**
     * Finish method
     */
    @Override
    public void finish() {
        this.navigation.n();
    }

    /**
     * Tick method
     */
    public void tick() {
        if(--timer > 0)
            return;

        timer = 10;

        //Don't move pet when target flying
        if (!target.getBukkitEntity().isOnGround())
            return;

        // Teleport back to the target if too far
        if (pet.h(target) > Math.pow(maxDistance, 2)) {
            pet.getBukkitEntity().teleport(target.getBukkitEntity());
            return;
        }

        if (pet.getGoalTarget() == null) {
            final PathEntity path = navigation.a(target);

            //Smooth path finding to entity instead of location
            navigation.a(path, speed);
        }
    }
}
