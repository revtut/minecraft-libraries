package net.revtut.libraries.text.scroller;

import org.bukkit.ChatColor;

/**
 * Color Scroller
 */
public class ColorScroller extends Scroller {

    /**
     * Length of the scroll color
     */
    private final int length;

    /**
     * Base color of the text
     */
    private final ChatColor base;

    /**
     * Scroll color of the text
     */
    private final ChatColor scroll;

    /**
     * Constructor of TextScroller
     * @param text text of the scroller
     * @param length length of the scroller
     * @param base base color of the text
     * @param scroll scroll color of the text
     */
    public ColorScroller(final String text, final int length, final ChatColor base, final ChatColor scroll) {
        super(text);

        if(length > text.length())
            throw new IllegalArgumentException("Length is bigger than the text to scroll");
        this.length = length;
        this.base = base;
        this.scroll = scroll;
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
            endPosition -= builder.length();
            endPosition += 2; // Insert the 'scroll' text at the beginning so we have to increase 2 again.

            builder.insert(0, scroll);
        }
        builder.insert(endPosition, base);


        return builder.toString();
    }
}