package net.revtut.libraries.scoreboard.label;

import net.revtut.libraries.scoreboard.InfoBoardLabel;

/**
 * Static label
 */
public class StaticLabel extends InfoBoardLabel {

    /**
     * Constructor of StaticLabel
     * @param text text of the label
     */
    public StaticLabel(final String text) {
        this(text, -1);
    }

    /**
     * Constructor of StaticLabel
     * @param text text of the label
     * @param position position of the label
     */
    public StaticLabel(final String text, final int position) {
        super(text, position);
    }

    /**
     * Update the label
     */
    @Override
    public void update() {
    }
}
