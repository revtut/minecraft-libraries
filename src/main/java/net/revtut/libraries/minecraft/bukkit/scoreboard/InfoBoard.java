package net.revtut.libraries.minecraft.bukkit.scoreboard;

import net.revtut.libraries.Libraries;
import net.revtut.libraries.minecraft.bukkit.scoreboard.label.ScrollingLabel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private final Scoreboard scoreboard;

    /**
     * Scoreboard objective
     */
    private Objective objective;

    /**
     * Title of the information board
     */
    private InfoBoardLabel title;

    /**
     * Map with all the information labels
     */
    private final List<InfoBoardLabel> infoLabels;

    /**
     * Constructor of InfoBoard
     * @param interval interval between updates dynamic labels (-1 for disabling)
     */
    public InfoBoard(final int interval) {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("side", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        this.infoLabels = new ArrayList<>();

        if(interval < 0)
            return;
        Bukkit.getScheduler().runTaskTimer(Libraries.getInstance(), () -> {
            if(title.getClass() == ScrollingLabel.class)
                updateTitle();
            getLabels(ScrollingLabel.class).forEach(this::updateLabel);
        }, 0L, interval);
    }

    /**
     * Get the bukkit scoreboard
     * @return bukkit scoreboard
     */
    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    /**
     * Get all the information labels from a class
     * @param clazz class of the information labels to get
     * @return information labels of that class
     */
    public List<InfoBoardLabel> getLabels(final Class<? extends InfoBoardLabel> clazz) {
        return infoLabels.stream()
                .filter(label -> label.getClass() == clazz)
                .collect(Collectors.toList());
    }

    /**
     * Get a information label by its identification
     * @param id identification of the label
     * @return label with that identification
     */
    public InfoBoardLabel getLabel(final String id) {
        for(final InfoBoardLabel label : infoLabels)
            if(label.getId().equalsIgnoreCase(id))
                return label;
        return null;
    }

    /**
     * Get a information label by its position
     * @param position position of the label
     * @return label with that position
     */
    public InfoBoardLabel getLabel(final int position) {
        for(final InfoBoardLabel label : infoLabels)
            if(label.getPosition() == position)
                return label;
        return null;
    }

    /**
     * Set the title label of the scoreboard
     * @param title title label of the scoreboard
     */
    public void setTitle(final InfoBoardLabel title) {
        this.title = title;
        updateTitle();
    }

    /**
     * Update the title of the scoreboard
     */
    public void updateTitle() {
        title.update();
        objective.setDisplayName(title.getText());
    }

    /**
     * Add a label to the information board
     * @param label label to be added
     */
    public void addLabel(final InfoBoardLabel label) {
        // Fix duplicated entry
        String labelText = label.getText();
        if(containsText(labelText)) {
            while (containsText(labelText))
                labelText += "§r";
            label.setText(labelText);
            label.update();
        }

        objective.getScore(label.getText()).setScore(label.getPosition());
        infoLabels.add(label);
    }

    /**
     * Remove a label from the information board
     * @param label label to be removed
     */
    public void removeLabel(final InfoBoardLabel label) {
        scoreboard.resetScores(label.getText());
        infoLabels.remove(label);
    }

    /**
     * Update a label in the information board
     * @param label label to be updated
     */
    public void updateLabel(final InfoBoardLabel label) {
        if(infoLabels.contains(label))
            removeLabel(label);
        label.update();
        addLabel(label);
    }

    /**
     * Check if the scoreboard contains a text
     * @param text text to check if is contained
     * @return true if contains, false otherwise
     */
    public boolean containsText(final String text) {
        for(final InfoBoardLabel label : infoLabels)
            if(label.getText().equalsIgnoreCase(text))
                return true;
        return false;
    }

    /**
     * Send the scoreboard to a player
     * @param player player to be sent the scoreboard
     */
    public void send(final Player player) {
        player.setScoreboard(scoreboard);
    }

    /**
     * Clear the information board
     */
    public void clear() {
        objective.unregister();
        objective = scoreboard.registerNewObjective("side", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        infoLabels.clear();
    }
}
