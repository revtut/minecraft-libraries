package net.revtut.libraries.minecraft.text.scroller;

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
     * Base 1 color of the text
     */
    private ChatColor base1;

    /**
     * Base 2 color of the text
     */
    private ChatColor base2;

    /**
     * Scroll color of the text
     */
    private final ChatColor scroll;

    /**
     * Constructor of TextScroller
     * @param text text of the scroller
     * @param length length of the scroller
     * @param base1 base 1 color of the text
     * @param base2 base 2 color of the text
     * @param scroll scroll color of the text
     */
    public ColorScroller(final String text, final int length, final ChatColor base1, final ChatColor base2, final ChatColor scroll) {
        super(text);

        if(length > text.length())
            throw new IllegalArgumentException("Length is bigger than the text to scroll");
        this.length = length;
        this.base1 = base1;
        this.base2 = base2;
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
            // Swap colors
            final ChatColor temp = base1;
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