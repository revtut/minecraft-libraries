package net.revtut.libraries.scoreboard.label;

import net.revtut.libraries.scoreboard.InfoBoardLabel;
import net.revtut.libraries.text.scroller.Scroller;

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
     */
    public ScrollingLabel(final Scroller scroller) {
        this(scroller, -1);
    }

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
        super.update();
        setText(scroller.next());
    }
}