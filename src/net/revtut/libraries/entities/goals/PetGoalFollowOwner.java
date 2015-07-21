package net.revtut.libraries.entities.goals;

import net.minecraft.server.v1_8_R3.*;
import net.revtut.libraries.entities.PetGoal;

/**
 * Pet goal follow owner
 */
public class PetGoalFollowOwner extends PetGoal {

    /**
     * Maximum distance to the owner so it teleports
     */
    private float maxDistance;

    /**
     * Distance to the owner so it start moving
     */
    private float startDistance;

    /**
     * Distance to the owner so it stop moving
     */
    private float stopDistance;

    /**
     * Follow speed
     */
    private double speed;

    /**
     * Pet
     */
    private EntityInsentient  pet;

    /**
     * Navigation of the pet
     */
    private Navigation navigation;

    /**
     * Owner of the pet
     */
    private EntityLiving owner;

    /**
     * Timer for ticking
     */
    private int timer = 0;

    /**
     * Constructor of PetGoalFollowOwner
     * @param pet pet of the goal
     * @param owner owner of the pet
     * @param speed speed of the pet
     * @param startDistance start distance so pet follow owner
     * @param stopDistance stop distance pet will stop following
     * @param maxDistance maximum distance between owner and pet
     */
    public PetGoalFollowOwner(EntityInsentient pet, EntityLiving owner, double speed, float startDistance, float stopDistance, float maxDistance) {
        this.pet = pet;
        this.owner = owner;
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
        else if (owner == null)
            return false;
        else if (pet.h(owner) < startDistance)
            return false;
        else
            return !(pet.getGoalTarget() != null && pet.getGoalTarget().isAlive());
    }

    /**
     * Should continue method
     * @return true if should, false otherwise
     */
    public boolean shouldContinue() {
        if (navigation.g())
            return false;
        else if (owner == null)
            return false;
        else if (this.pet.h(owner) <= stopDistance)
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
        pet.getControllerLook().a(owner, 10.0F, (float) pet.bQ());

        if(--timer > 0)
            return;

        timer = 10;

        //Don't move pet when owner flying
        if (!owner.getBukkitEntity().isOnGround())
            return;

            /*if (pet.h(owner) > maxDistance && owner.getBukkitEntity().isOnGround()) {
                //this.pet.getPet().teleportToOwner();
                // Teleport PET
                return;
            }*/

        if (pet.getGoalTarget() == null) {
            PathEntity path = pet.getNavigation().a(owner);

            //Smooth path finding to entity instead of location
            pet.getNavigation().a(path, speed);
        }
    }
}
