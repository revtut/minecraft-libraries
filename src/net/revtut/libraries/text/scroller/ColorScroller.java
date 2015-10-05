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

        int endPosition = position + length - 1;
        if(endPosition >= text.length())
            endPosition = text.length() - 1;

        return base + text.substring(0, position) + scroll + text.substring(position, endPosition + 1) + base + text.substring(endPosition, text.length());
    }
}