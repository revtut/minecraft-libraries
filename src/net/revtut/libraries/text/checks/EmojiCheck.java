package net.revtut.libraries.text.checks;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
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
        EMOJI.put(":-)", "☻");
        EMOJI.put(":)", "☻");

        EMOJI.put(":-(", "☹");
        EMOJI.put(":(", "☹");

        EMOJI.put("<3", "❤");

        EMOJI.put("o.o", "◕_◕");
        EMOJI.put("0.0", "◕_◕");

        EMOJI.put("*_*", "★_★");
        EMOJI.put("*-*", "★_★");

        EMOJI.put(">:-[", "(◣_◢)");
        EMOJI.put(">:[", "(◣_◢)");

        EMOJI.put("O:-)", "◔◡◔");
        EMOJI.put("O:)", "◔◡◔");
        EMOJI.put("0:-)", "◔◡◔");
        EMOJI.put("0:)", "◔◡◔");

        EMOJI.put(":X", ":✖");
        EMOJI.put(":-X", ":✖");

        EMOJI.put("yolo", "Yᵒᵘ Oᶰˡʸ Lᶤᵛᵉ Oᶰᶜᵉ");

        EMOJI.put("Love you", "ᶫᵒᵛᵉᵧₒᵤ");

        EMOJI.put("Happy Birthday", "ዞᏜ℘℘Ꮍ ℬℹℛʈዞᗬᏜᎽ");

        EMOJI.put("(C)", "©");
        EMOJI.put("(R)", "®");
        EMOJI.put("(P)", "℗");
        EMOJI.put("(TM)", "™");

        EMOJI.put("->", "→");
        EMOJI.put("<-", "←");

        EMOJI.put("|>", "▶");
        EMOJI.put("<|", "◀");

        EMOJI.put("(v)", "✌");
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
            if(StringUtils.containsIgnoreCase(message, emoticon))
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
        for(final String emoticon : EMOJI.keySet()) {
            if (!StringUtils.containsIgnoreCase(message, emoticon))
                continue;
            message = message.replaceAll(getEmoticonSearchRegex(emoticon), Matcher.quoteReplacement(EMOJI.get(emoticon)));
        }

        return message;
    }

    /**
     * Get the emoticon search regex
     * @param emoticon emoticon the get search regex
     * @return search regex for the emoticon
     */
    private String getEmoticonSearchRegex(String emoticon) {
        // Allowed characters left + emoticon + allowed characters right
        return "(?<![-_a-zA-Z0-9)(;:*<>=/])((?i)" + Pattern.quote(emoticon) + ")(?![-_a-zA-Z0-9)(;:*<>=/])";
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
