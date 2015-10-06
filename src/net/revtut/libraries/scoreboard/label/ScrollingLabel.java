package net.revtut.libraries.scoreboard.label;

import net.revtut.libraries.scoreboard.InfoBoardLabel;
import net.revtut.libraries.text.scroller.Scroller;
import org.bukkit.ChatColor;

/**
 * Scrolling label
 */
public class ScrollingLabel extends InfoBoardLabel {

    /**
     * Scroller of the label
     */
    private final Scroller scroller;

    /**
     * Constructor of ScrollingLabel
     * @param scroller scroller of the label
     * @param position position of the label
     */
    public ScrollingLabel(final Scroller scroller, final int position) {
        super(scroller.next(), position);
        this.scroller = scroller;
    }

    /**
     * Update the label
     */
    @Override
    public void update() {
        String text = scroller.next();

        // Compensate color codes length
        while(text.charAt(0) == '§') {
            scroller.next();
            text = scroller.next();
        }

        // Fix colors in final displayed message
        final StringBuilder textBuilder = new StringBuilder(text);

        // Get the color to add at the beginning of the text
        String color = "§r";
        final String initialText = scroller.getText();
        int index = scroller.getPosition();
        if(initialText.charAt(index) != '§') {
            while(initialText.charAt(index) != '§' && index > 0) // Step back until a 'color code char' is found
                index--;
            if(initialText.charAt(index) == '§' && index < initialText.length() - 1) // Check if a color has been found
                color = "" + initialText.charAt(index) + initialText.charAt(index + 1);
        }

        // Check if last character is the 'colour code char'
        if(textBuilder.charAt(textBuilder.length() - 1) == '§')
            textBuilder.setCharAt(textBuilder.length() - 1, ' ');

        setText(color + textBuilder.toString());
    }
}