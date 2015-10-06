package net.revtut.libraries.text.checks;

import net.revtut.libraries.Libraries;
import net.revtut.libraries.utils.FilesAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
        EMOJI.put(":-)", "?");
        EMOJI.put(":)", "?");

        EMOJI.put(":-(", "?");
        EMOJI.put(":(", "?");

        EMOJI.put("<3", "?");

        EMOJI.put("o.o", "?_?");
        EMOJI.put("o.O", "?_?");
        EMOJI.put("O.o", "?_?");
        EMOJI.put("0.0", "?_?");
        EMOJI.put("o.0", "?_?");
        EMOJI.put("0.o", "?_?");

        EMOJI.put("*_*", "?_?");
        EMOJI.put("*-*", "?_?");

        EMOJI.put(">:-[", "(?_?)");
        EMOJI.put(">:[", "(?_?)");

        EMOJI.put("O:-)", "???");
        EMOJI.put("O:)", "???");
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
            message = message.replaceAll(getEmoticonSearchRegex(emoticon), EMOJI.get(emoticon));
        return message;
    }

    /**
     * Get the emoticon search regex
     * @param emoticon emoticon the get search regex
     * @return search regex for the emoticon
     */
    private String getEmoticonSearchRegex(String emoticon) {
        // Allowed characters left + emoticon + allowed characters right
        return "(?<![-_a-zA-Z0-9)(;:*<>=/])(" + Pattern.quote(emoticon) + ")(?![-_a-zA-Z0-9)(;:*<>=/])";
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
