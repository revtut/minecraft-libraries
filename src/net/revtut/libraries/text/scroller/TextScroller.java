package net.revtut.libraries.text.scroller;

/**
 * Text scroller
 */
public class TextScroller extends Scroller {

    /**
     * Length of the text to be shown
     */
    private final int length;

    /**
     * Constructor of TextScroller
     * @param text text of the scroller
     * @param length length of the scroller
     */
    public TextScroller(final String text, final int length) {
        super(text);

        if(length > text.length())
            throw new IllegalArgumentException("Length is bigger than the text to scroll");
        this.length = length;
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
            endPosition -= text.length() + 1;

        if(endPosition < position)
            return text.substring(position, text.length() - 1) + text.substring(0, endPosition + 1);
        else
            return text.substring(position, endPosition + 1);
    }
}
