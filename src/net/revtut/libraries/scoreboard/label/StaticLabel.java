package net.revtut.libraries.scoreboard.label;

import net.revtut.libraries.scoreboard.InfoBoardLabel;

/**
 * Static label
 */
public class StaticLabel extends InfoBoardLabel {

    /**
     * Constructor of StaticLabel
     * @param id identification of the label
     * @param text text of the label
     * @param position position of the label
     */
    public StaticLabel(final String id, final String text, final int position) {
        super(id, text, position);
    }

    /**
     * Update the label
     */
    @Override
    public void update() {
        super.update();
    }
}
