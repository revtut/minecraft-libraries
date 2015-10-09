package net.revtut.libraries.scoreboard.label;

import net.revtut.libraries.scoreboard.InfoBoardLabel;

/**
 * Blank label
 */
public class BlankLabel extends InfoBoardLabel {

    /**
     * Constructor of BlankLabel
     */
    public BlankLabel() {
        this(-1);
    }

    /**
     * Constructor of BlankLabel
     * @param position position of the label
     */
    public BlankLabel(final int position) {
        super(" ", position);
    }

    /**
     * Update the label
     */
    @Override
    public void update() {
        super.update();
    }
}