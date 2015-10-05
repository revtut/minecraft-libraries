package net.revtut.libraries.text.checks;

import net.revtut.libraries.Libraries;
import net.revtut.libraries.utils.FilesAPI;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
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
    private static final char[] REPLACE_SYMBOLS;

    /**
     * Initialize variables
     */
    static {
        // Bad Words
        final FileInputStream inputStream = (FileInputStream) Libraries.getInstance().getResource("resources/badwords.txt");
        BAD_WORDS = FilesAPI.getLines(inputStream);

        // Replace symbols
        REPLACE_SYMBOLS = new char[] {'#', '$', '@', '&', '%', '*'};
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
            if(message.contains(badWord))
                return true;
        return false;
    }

    /**
     * Fixes the message in order to remove / replace non allowed elements
     * @param message message to be fixed
     * @return fixed message
     */
    @Override
    public String fixMessage(String message) {
        for(final String badWord : BAD_WORDS) {
            if(!message.contains(badWord))
                continue;

            message = message.replaceAll(badWord, generateRandomString(REPLACE_SYMBOLS, badWord.length()));
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
     * Generate a random string from a array of characters
     * @param characters characters allowed on the string
     * @param length length of the string
     * @return random string
     */
    public static String generateRandomString(final char[] characters, final int length) {
        final StringBuilder stringBuilder = new StringBuilder(length);
        for(int i = 0; i < length; i++)
            stringBuilder.append(characters[(int)(Math.random() * (characters.length - 1))]);

        return stringBuilder.toString();
    }
}
