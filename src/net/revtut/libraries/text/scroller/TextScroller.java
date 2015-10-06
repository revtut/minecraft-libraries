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

        // Bypass initial color position if needed
        while(text.charAt(position) == '§')
            position += 2;

        // Get the color that will start the text message
        String initialColor = "§r";
        for(int index = position; index >= 0; index--) {
            if(text.charAt(index) == '§' && index < text.length() - 1) {
                initialColor = "" + text.charAt(index) + text.charAt(index + 1);
                break;
            }
        }

        // Build the string
        StringBuilder builder = new StringBuilder(initialColor);
        for(int length = 0, index = position; length < this.length; index++) {
            if(index >= text.length())
                index = 0;

            // Only count non colors as length
            if(text.charAt(index) != '§' && index > 0 && text.charAt(index - 1) != '§')
                length++;

            builder.append(text.charAt(index));
        }

        return builder.toString();
    }
}
