package net.revtut.libraries.minecraft.scoreboard.label;

import net.revtut.libraries.minecraft.scoreboard.InfoBoardLabel;
import net.revtut.libraries.minecraft.text.scroller.Scroller;

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
     * @param id identification of the label
     * @param scroller scroller of the label
     * @param position position of the label
     */
    public ScrollingLabel(final String id, final Scroller scroller, final int position) {
        super(id, scroller.next(), position);
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