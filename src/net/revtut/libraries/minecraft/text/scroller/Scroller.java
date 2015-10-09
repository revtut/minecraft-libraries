package net.revtut.libraries.minecraft.text.scroller;

/**
 * Scroller
 */
public abstract class Scroller {
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
     * Get the next string for the scroller
     * @return next string for the scroller
     */
    public abstract String next();
}
