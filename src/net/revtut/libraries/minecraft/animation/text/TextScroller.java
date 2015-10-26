package net.revtut.libraries.minecraft.animation.text;

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
     * Get the previous string for the scroller
     * @return previous string for the scroller
     */
    public String previous() {
        position--;
        if(position < 0)
            position = text.length() - 1;

        // Bypass initial color position if needed
        while(text.charAt(position) == '§')
            position += 2;
        if(position >= text.length())
            position = 0;

        // Get the color that will start the text message
        String initialColor = "§r";
        for(int index = position; index >= 0; index--) {
            if(text.charAt(index) == '§' && index < text.length() - 1) {
                initialColor = "" + text.charAt(index) + text.charAt(index + 1);
                break;
            }
        }

        // Build the string
        final StringBuilder builder = new StringBuilder(initialColor);
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
        if(position >= text.length())
            position = 0;

        // Get the color that will start the text message
        String initialColor = "§r";
        for(int index = position; index >= 0; index--) {
            if(text.charAt(index) == '§' && index < text.length() - 1) {
                initialColor = "" + text.charAt(index) + text.charAt(index + 1);
                break;
            }
        }

        // Build the string
        final StringBuilder builder = new StringBuilder(initialColor);
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
