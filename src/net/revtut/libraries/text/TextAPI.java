package net.revtut.libraries.text;

import net.revtut.libraries.Libraries;
import net.revtut.libraries.utils.FilesAPI;

import java.io.FileInputStream;
import java.util.List;

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
     * List with all the badwords
     */
    private static List<String> badWords;

    /**
     * Array with replace symbols (for example, for bad words)
     */
    private static char[] replaceSymbols;

    /**
     * Initialize needed variables
     */
    static {
        // Bad Words
        FileInputStream inputStream = (FileInputStream) Libraries.getInstance().getResource("resources/badwords.txt");
        badWords = FilesAPI.getLines(inputStream);

        // Replace symbols
        replaceSymbols = new char[] {'#', '$', '@', '&', '%', '*'};
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
