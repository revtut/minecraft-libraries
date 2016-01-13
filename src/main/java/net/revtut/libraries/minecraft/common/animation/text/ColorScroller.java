package net.revtut.libraries.minecraft.common.animation.text;

/**
 * Color Scroller
 */
public class ColorScroller extends Scroller {

    /**
     * Length of the scroll color
     */
    private final int length;

    /**
     * Base 1 color of the text
     */
    private String base1;

    /**
     * Base 2 color of the text
     */
    private String base2;

    /**
     * Scroll color of the text
     */
    private final String scroll;

    /**
     * Constructor of TextScroller
     * @param text text of the scroller
     * @param length length of the scroller
     * @param base1 base 1 color of the text
     * @param base2 base 2 color of the text
     * @param scroll scroll color of the text
     */
    public ColorScroller(final String text, final int length, final String base1, final String base2, final String scroll) {
        super(text);

        if(length > text.length())
            throw new IllegalArgumentException("Length is bigger than the text to scroll");
        this.length = length;
        this.base1 = base1;
        this.base2 = base2;
        this.scroll = scroll;
    }

    /**
     * Get the previous string for the scroller
     * @return previous string for the scroller
     */
    public String previous() {
        position--;
        if(position < 0)
            position = text.length() - 1;

        // Build the string
        final StringBuilder builder = new StringBuilder(text);
        builder.insert(position, scroll);
        int endPosition = (position + 2) + length; // Position + 2 because a color is two characters
        if(endPosition >= builder.length()) {
            // Swap colors
            final String temp = base1;
            base1 = base2;
            base2 = temp;

            endPosition -= builder.length();

            builder.insert(endPosition, base2);
            builder.insert(0, scroll);
        } else {
            builder.insert(endPosition, base2);
            builder.insert(0, base1);
        }


        return builder.toString();
    }

    /**
     * Get the next string for the scroller
     * @return next string for the scroller
     */
    @Override
    public String next() {
        position++;
        if(position >= text.length())
            position = 0;

        // Build the string
        final StringBuilder builder = new StringBuilder(text);
        builder.insert(position, scroll);
        int endPosition = (position + 2) + length; // Position + 2 because a color is two characters
        if(endPosition >= builder.length()) {
            // Swap colors
            final String temp = base1;
            base1 = base2;
            base2 = temp;

            endPosition -= builder.length();

            builder.insert(endPosition, base2);
            builder.insert(0, scroll);
        } else {
            builder.insert(endPosition, base2);
            builder.insert(0, base1);
        }


        return builder.toString();
    }
}