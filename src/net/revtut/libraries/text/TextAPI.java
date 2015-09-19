package net.revtut.libraries.text;

import net.revtut.libraries.Libraries;

import java.io.FileInputStream;
import java.util.ArrayList;
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
    private static List<String> badWords = new ArrayList<>();

    /**
     * Read files needed
     */
    static {
        FileInputStream inputStream = (FileInputStream) Libraries.getInstance().getResource("resources/badwords.txt");
    }
}
