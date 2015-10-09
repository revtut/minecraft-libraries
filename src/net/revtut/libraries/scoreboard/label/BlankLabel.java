package net.revtut.libraries.scoreboard.label;

import net.revtut.libraries.scoreboard.InfoBoardLabel;

/**
 * Blank label
 */
public class BlankLabel extends InfoBoardLabel {

    /**
     * Constructor of BlankLabel
     * @param position position of the label
     */
    public BlankLabel(final int position) {
        this("", position);
    }

    /**
     * Constructor of BlankLabel
     * @param id identification of the label
     * @param position position of the label
     */
    private BlankLabel(final String id, final int position) {
        super(id, " ", position);
    }

    /**
     * Update the label
     */
    @Override
    public void update() {
        super.update();
    }
}