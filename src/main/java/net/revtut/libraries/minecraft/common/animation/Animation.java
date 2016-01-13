package net.revtut.libraries.minecraft.common.animation;

/**
 * Animation Interface
 */
public interface Animation {

    /**
     * Get the previous frame for the animation
     * @return previous frame for the animation
     */
    String previous();

    /**
     * Get the next frame for the animation
     * @return next frame for the animation
     */
    String next();
}