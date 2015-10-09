package net.revtut.libraries.minecraft.entities;

import net.minecraft.server.v1_8_R3.PathfinderGoal;

/**
 * PetGoal contains methods relative to paths with better names
 * than the default used by NMS.
 */
public abstract class PetGoal extends PathfinderGoal {

    /**
     * Check if path goal should start
     * @return true if should, false otherwise
     */
    public abstract boolean shouldStart();
    /**
     * Check if path goal should continue to be ticked
     * @return true if should, false otherwise
     */
    public abstract boolean shouldContinue();

    /**
     * Start the path
     */
    public abstract void start();

    /**
     * Finish the path
     */
    public abstract void finish();

    /**
     * Tick the path goal
     */
    public abstract void tick();

    /**
     * Check if should start
     * @return true if should, false otherwise
     */
    @Override
    public boolean a() {
        return shouldStart();
    }

    /**
     * Check if should continue
     * @return true if should, false otherwise
     */
    @Override
    public boolean b() {
        return shouldContinue();
    }

    /**
     * Start the movement path
     */
    @Override
    public void c() {
        start();
    }

    /**
     * Finish the movement
     */
    @Override
    public void d() {
        finish();
    }

    /**
     * Tick the path goal
     */
    @Override
    public void e() {
        tick();
    }
}
