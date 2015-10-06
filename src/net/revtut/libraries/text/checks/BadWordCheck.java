package net.revtut.libraries.text.checks;

import net.revtut.libraries.Libraries;
import net.revtut.libraries.utils.FilesAPI;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Caps Lock Check
 */
public class BadWordCheck implements Check {

    /**
     * List with all the bad words
     */
    private static final List<String> BAD_WORDS;

    /**
     * Array with replace symbols
     */
    private static final String REPLACE_PATTERN;

    /**
     * Initialize variables
     */
    static {
        // Bad Words
        final InputStream inputStream = Libraries.getInstance().getResource("resources/badwords.txt");
        BAD_WORDS = FilesAPI.getLines(inputStream);

        // Replace symbols
        REPLACE_PATTERN = "#*$@%!";
    }

    /**
     * Check if message matches the check
     * @param player player that sent the message
     * @param message message to check
     * @return true if matches, false otherwise
     */
    @Override
    public boolean checkMessage(final Player player, final String message) {
        for(final String badWord : BAD_WORDS)
            if(StringUtils.containsIgnoreCase(message, badWord))
                return true;
        return false;
    }

    /**
     * Fixes the message in order to remove / replace some elements
     * @param message message to be fixed
     * @return fixed message
     */
    @Override
    public String fixMessage(String message) {
        for(final String badWord : BAD_WORDS) {
            if(!StringUtils.containsIgnoreCase(message, badWord))
                continue;

            message = message.replaceAll("(?i)" + Pattern.quote(badWord), Matcher.quoteReplacement(generateCensoredString(REPLACE_PATTERN, badWord.length())));
        }

        return message;
    }

    /**
     * Get the error message of the check
     * @return error message of the check
     */
    @Override
    public String getErrorMessage() {
        return ChatColor.RED + "You may not use bad words here!";
    }

    /**
     * Get the violation level of the check
     * @return violation level of the check
     */
    @Override
    public int getViolationLevel() {
        return 1;
    }

    /**
     * Generate a censored string from a pattern string
     * @param pattern pattern string
     * @param length length of the string
     * @return censored string
     */
    private String generateCensoredString(final String pattern, final int length) {
        final StringBuilder stringBuilder = new StringBuilder(length);
        for(int i = 0; i < length; i++)
            stringBuilder.append(pattern.charAt(i % pattern.length()));

        return stringBuilder.toString();
    }
}
