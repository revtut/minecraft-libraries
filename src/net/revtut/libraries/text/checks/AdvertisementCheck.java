package net.revtut.libraries.text.checks;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Advertisement Check
 */
public class AdvertisementCheck implements Check {

    /**
     * Host addresses pattern
     */
    private static final Pattern HOST_PATTERN = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    /**
     * Websites pattern
     */
    private static final Pattern WEBSITE_PATTERN = Pattern.compile("@(https?|ftp)://(-\\.)?([^\\s/?\\.#-]+\\.?)+(/[^\\s]*)?$@iS");

    /**
     * Check if message matches the check
     * @param player player that sent the message
     * @param message message to check
     * @return true if matches, false otherwise
     */
    @Override
    public boolean checkMessage(final Player player, final String message) {
        return HOST_PATTERN.matcher(message).find() || WEBSITE_PATTERN.matcher(message).find();
    }

    /**
     * Fixes the message in order to remove / replace non allowed elements
     * @param message message to be fixed
     * @return fixed message
     */
    @Override
    public String fixMessage(String message) {
        final Matcher matcherIP = HOST_PATTERN.matcher(message);
        final Matcher matcherWebsite = WEBSITE_PATTERN.matcher(message);
        if(matcherIP.find())
            message = matcherIP.replaceAll("mc.revtut.net");
        if(matcherIP.find())
            message = matcherWebsite.replaceAll("https://www.revtut.net");
        return message;
    }

    /**
     * Get the error message of the check
     * @return error message of the check
     */
    @Override
    public String getErrorMessage() {
        return ChatColor.RED + "You may not advertise here!";
    }

    /**
     * Get the violation level of the check
     * @return violation level of the check
     */
    @Override
    public int getViolationLevel() {
        return 1;
    }
}
