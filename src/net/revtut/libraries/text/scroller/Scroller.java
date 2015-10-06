package net.revtut.libraries.text.scroller;

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
     * Get the full text of the scroller
     * @return full text of the scroller
     */
    public String getText() {
        return text;
    }

    /**
     * Get the position pointer of the scroller
     * @return position pointer of the scroller
     */
    public int getPosition() {
        return position;
    }

    /**
     * Get the next string for the scroller
     * @return next string for the scroller
     */
    public abstract String next();
}
