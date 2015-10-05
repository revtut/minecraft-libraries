package net.revtut.libraries.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

/**
 * InfoBoard Library.
 *
 * <P>A library with methods related to scoreboard.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class InfoBoard {

    /**
     * Bukkit scoreboard
     */
    private Scoreboard scoreboard;

    /**
     * Scoreboard objective
     */
    private Objective objective;

    /**
     * Map with all the information labels
     */
    private List<InfoBoardLabel> infoLabels;

    /**
     * Constructor of InfoBoard
     */
    public InfoBoard() {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("side", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        this.infoLabels = new ArrayList<>();
    }

    /**
     * Get the bukkit scoreboard
     * @return bukkit scoreboard
     */
    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    /**
     * Get a information label by its name
     * @param text name of the label
     * @return label with that name
     */
    public InfoBoardLabel getLabel(String text) {
        for(InfoBoardLabel label : infoLabels)
            if(label.getText().equalsIgnoreCase(text))
                return label;
        return null;
    }

    /**
     * Get a information label by its position
     * @param position position of the label
     * @return label with that position
     */
    public InfoBoardLabel getLabel(int position) {
        for(InfoBoardLabel label : infoLabels)
            if(label.getPosition() == position)
                return label;
        return null;
    }

    /**
     * Set the title of the scoreboard
     * @param title title of the scoreboard
     */
    public void setTitle(final String title) {
        objective.setDisplayName(title);
    }

    /**
     * Add a label to the information board
     * @param label label to be added
     */
    public void addLabel(InfoBoardLabel label) {
        infoLabels.add(label);
    }

    /**
     * Remove a label from the information board
     * @param label label to be removed
     */
    public void removeLabel(InfoBoardLabel label) {
        infoLabels.remove(label);
    }
}
