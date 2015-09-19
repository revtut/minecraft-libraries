package net.revtut.libraries.text;

import net.revtut.libraries.Libraries;
import net.revtut.libraries.utils.FilesAPI;

import java.io.FileInputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Text Library.
 *
 * <P>A library with methods text related to.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class TextAPI {

    /**
     * List with all the bad words
     */
    private static List<String> badWords;

    /**
     * Array with replace symbols (for example, for bad words)
     */
    private static char[] replaceSymbols;

    /**
     * IP address pattern
     */
    private static Pattern ipPattern;

    /**
     * Web address pattern
     */
    private static Pattern websitePattern;

    /**
     * Initialize needed variables
     */
    static {
        // Bad Words
        FileInputStream inputStream = (FileInputStream) Libraries.getInstance().getResource("resources/badwords.txt");
        badWords = FilesAPI.getLines(inputStream);

        // Replace symbols
        replaceSymbols = new char[] {'#', '$', '@', '&', '%', '*'};

        // Patterns
        ipPattern = Pattern.compile("((?<![0-9])(?:(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})[ ]?[., ][ ]?(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})[ ]?[., ][ ]?(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})[ ]?[., ][ ]?(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2}))(?![0-9]))");
        websitePattern = Pattern.compile("(http://)|(https://)?(www)?\\S{2,}((\\.com)|(\\.ru)|(\\.net)|(\\.org)|(\\.co\\.uk)|(\\.tk)|(\\.info)|(\\.es)|(\\.de)|(\\.arpa)|(\\.edu)|(\\.firm)|(\\.me)|(\\.pt)|(\\.int)|(\\.mil)|(\\.mobi)|(\\.nato)|(\\.to)|(\\.fr)|(\\.ms)|(\\.br)|(\\.vu)|(\\.eu)|(\\.nl)|(\\.us)|(\\.dk))");
    }

    /**
     * Replace all bad words on a string with random characters
     * @param text text to have its bad words replaced
     * @return text with bad words replaced
     */
    public static String replaceBadWords(String text) {
        for(String badWord : badWords) {
            if(!text.contains(badWord))
                continue;

            text = text.replaceAll(badWord, generateRandomString(replaceSymbols, badWord.length()));
        }

        return text;
    }

    /**
     * Replace all advertisements on the string such as IP addresses and websites
     * @param text text to have its advertisements replaced
     * @param ipReplacement replacement string for ip addresses
     * @param websiteReplacement replacement string for websites
     * @return text with no advertisements
     */
    public static String replaceAdvertisement(String text, String ipReplacement, String websiteReplacement) {
        Matcher matcherIP = ipPattern.matcher(text);
        Matcher matcherWebsite = websitePattern.matcher(text);
        if(matcherIP.find())
            text = matcherIP.replaceAll(ipReplacement);
        if(matcherWebsite.find())
            text = matcherWebsite.replaceAll(websiteReplacement);

        return text;
    }

    /**
     * Generate a random string from a array of characters
     * @param characters characters allowed on the string
     * @param length length of the string
     * @return random string
     */
    public static String generateRandomString(char[] characters, int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        for(int i = 0; i < length; i++)
            stringBuilder.append(characters[(int)(Math.random() * (characters.length - 1))]);

        return stringBuilder.toString();
    }
}
