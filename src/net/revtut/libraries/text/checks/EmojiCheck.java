package net.revtut.libraries.text.checks;

import net.revtut.libraries.Libraries;
import net.revtut.libraries.utils.FilesAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Caps Lock Check
 */
public class EmojiCheck implements Check {

    /**
     * Map with all Emoticon => Emoji
     */
    private static final Map<String, String> EMOJI;

    /**
     * Initialize variables
     */
    static {
        // Emoji
        EMOJI = new HashMap<>();

        final InputStream inputStream = Libraries.getInstance().getResource("resources/emoji.txt");
        final List<String> emojiList = FilesAPI.getLines(inputStream);
        for(final String emoji : emojiList) {
            final String[] emojiArray = emoji.split(" ");
            final String unicodeCharacter = emojiArray[emojiArray.length - 1]; // Last 'word' in the file must be the unicode character
            for(int i = 0; i < emojiArray.length - 1; i++)
                EMOJI.put(emojiArray[i], unicodeCharacter);
        }
    }

    /**
     * Check if message matches the check
     * @param player player that sent the message
     * @param message message to check
     * @return true if matches, false otherwise
     */
    @Override
    public boolean checkMessage(final Player player, final String message) {
        for(final String emoticon : EMOJI.keySet())
            if(message.contains(emoticon))
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
        for(final String emoticon : EMOJI.keySet())
            message = message.replaceAll(emoticon, EMOJI.get(emoticon));
        return message;
    }

    /**
     * Get the error message of the check
     * @return error message of the check
     */
    @Override
    public String getErrorMessage() {
        return ChatColor.RED + "You may not use smiles here!";
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
