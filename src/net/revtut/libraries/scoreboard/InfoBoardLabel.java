package net.revtut.libraries.scoreboard;

import org.bukkit.ChatColor;

/**
 * Scoreboard Label
 */
public abstract class InfoBoardLabel {

    /**
     * Identification of the label
     */
    private String id;

    /**
     * Text of the label
     */
    private String text;

    /**
     * Text to be set on the next update
     */
    private String nextText;

    /**
     * Position of the label
     */
    private int position;

    /**
     * Constructor of InfoBoardLabel
     * @param id identification of the label
     * @param text text of the label
     * @param position position of the label
     */
    public InfoBoardLabel(final String id, final String text, final int position) {
        this.id = id;
        this.text = ChatColor.translateAlternateColorCodes('&', text);
        this.nextText = this.text;
        this.position = position;
    }

    /**
     * Get the identification of the label
     * @return identification of the label
     */
    public String getId() {
        return id;
    }

    /**
     * Get the text of the label
     * @return text of the label
     */
    public String getText() {
        return text;
    }

    /**
     * Set the text of the label
     * @param text text of the label
     */
    public void setText(final String text) {
        this.nextText = text;
    }

    /**
     * Get the position of the label
     * @return position of the label
     */
    public int getPosition() {
        return position;
    }

    /**
     * Set the position of the label
     * @param position position of the label
     */
    public void setPosition(final int position) {
        this.position = position;
    }

    /**
     * Update the label
     */
    public void update() {
        text = nextText;
    }
}