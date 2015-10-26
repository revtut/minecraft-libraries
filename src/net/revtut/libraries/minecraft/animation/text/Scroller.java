package net.revtut.libraries.minecraft.animation.text;

import net.revtut.libraries.minecraft.animation.Animation;

/**
 * Scroller
 */
public abstract class Scroller implements Animation {
    /**
     * Text of the scroller
     */
    protected final String text;

    /**
     * Current position of the scroller
     */
    protected int position;

    /**
     * Constructor of Scroller
     * @param text text of the scroller
     */
    public Scroller(final String text) {
        this.text = text;
        this.position = 0;
    }

    /**
     * Scroll the text to the left
     * @return scrolled text
     */
    public String scrollLeft() { return next(); }

    /**
     * Scroll the text to the right
     * @return scrolled text
     */
    public String scrollRight() { return previous(); }
}
