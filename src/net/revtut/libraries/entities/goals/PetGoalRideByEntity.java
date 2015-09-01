package net.revtut.libraries.entities.goals;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.revtut.libraries.entities.PetGoal;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Pet goal ride by entity
 */
public class PetGoalRideByEntity extends PetGoal {

    /**
     * Pet entity
     */
    private EntityInsentient pet;

    /**
     * Passenger of the pet
     */
    private Entity passenger;

    /**
     * Ride speed of the pet
     */
    private float rideSpeed;

    /**
     * Jump height of the pet
     */
    private float jumpHeight;

    /**
     * Set yaw and pitch protected method
     */
    private Method SET_YAW_PITCH_METHOD;

    /**
     * Jump field
     */
    private Field JUMP_FIELD;

    /**
     * Constructor of PetGoalFollowEntity
     * @param pet pet of the goal
     * @param passenger passenger of the pet
     * @param rideSpeed ride speed of the pet
     * @param jumpHeight height of the pet jump
     */
    public PetGoalRideByEntity(EntityInsentient pet, Entity passenger, float rideSpeed, float jumpHeight) {
        this.pet = pet;
        this.passenger = passenger;
        this.rideSpeed = rideSpeed;
        this.jumpHeight = jumpHeight;

        // Extract method and fields
        try {
            SET_YAW_PITCH_METHOD = Entity.class.getDeclaredMethod("setYawPitch", float.class, float.class);
            SET_YAW_PITCH_METHOD.setAccessible(true);
            JUMP_FIELD = EntityLiving.class.getDeclaredField("aY");
            JUMP_FIELD.setAccessible(true);
        } catch (NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * Should start method
     * @return true if should, false otherwise
     */
    public boolean shouldStart() {
        if (!pet.isAlive())
            return false;
        else if (passenger == null)
            return false;
        else if(pet.passenger == null)
            return false;
        else if(!pet.passenger.getUniqueID().equals(passenger.getUniqueID()))
            return false;

        return true;
    }

    /**
     * Should continue method
     * @return true if should, false otherwise
     */
    public boolean shouldContinue() {
        if(passenger == null)
            return false;
        else if(pet.passenger == null)
            return false;
        else if(!pet.passenger.getUniqueID().equals(passenger.getUniqueID()))
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
        float sideMot;
        float forwardMot;

        pet.lastYaw = pet.yaw = passenger.yaw;
        pet.pitch = passenger.pitch * 0.5F;

        // Set the entity's pitch, yaw, head rotation etc.
        try {
            SET_YAW_PITCH_METHOD.invoke(pet, pet.yaw, pet.pitch);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        pet.aI = pet.aG = pet.yaw;

        sideMot = ((EntityLiving) this.passenger).aZ * 0.5F;
        forwardMot = ((EntityLiving) this.passenger).ba;

        if (forwardMot <= 0.0F)
            forwardMot *= 0.25F; // Backwards slower
        sideMot *= 0.75F; // Sideways slower

        pet.k(rideSpeed); // Apply the speed
        pet.g(sideMot, forwardMot); // Apply the motion

        pet.S = 0.5F; // Climb automatically half slabs

        // Make entity look in front
        pet.getControllerLook().a(pet.locX + Math.random(), pet.locY + (double) pet.getHeadHeight(), pet.locZ + Math.random(), 10.0F, (float) pet.bQ());

        // Apply jump
        if (JUMP_FIELD != null && pet.onGround) {
            try {
                if(JUMP_FIELD.getBoolean(passenger)) {
                    pet.motY = jumpHeight;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
