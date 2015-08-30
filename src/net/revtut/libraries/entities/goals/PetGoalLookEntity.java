package net.revtut.libraries.entities.goals;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.revtut.libraries.entities.PetGoal;

/**
 * Pet goal look entity
 */
public class PetGoalLookEntity extends PetGoal {

    /**
     * Pet entity
     */
    private EntityInsentient pet;

    /**
     * Target of the pet
     */
    private Entity target;

    /**
     * Maximum distance to look at target
     */
    private double range;

    /**
     * Constructor of PetGoalFollowEntity
     * @param pet pet of the goal
     * @param target target of the pet
     * @param range range to look at target
     */
    public PetGoalLookEntity(EntityInsentient pet, Entity target, double range) {
        this.pet = pet;
        this.target = target;
        this.range = range;
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
        else if (pet.h(target) > Math.pow(range, 2))
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
        if(target == null)
            return false;
        else if (pet.h(target) > Math.pow(range, 2))
            return false;

        return true;
    }

    /**
     * Start method
     */
    @Override
    public void start() {
    }

    /**
     * Finish method
     */
    @Override
    public void finish() {
    }

    /**
     * Tick method
     */
    public void tick() {
        // Update yaw and pitch
        pet.getControllerLook().a(target.locX, target.locY + target.getHeadHeight(), target.locZ, 10.0F, (float) pet.bQ());
    }
}
