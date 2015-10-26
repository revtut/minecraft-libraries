package net.revtut.libraries.minecraft.text;

import java.util.List;

/**
 * Converters Library.
 *
 * <P>Useful converters to be used.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class Converters {

    /**
     * Constructor of Converters
     */
    private Converters() {}

    /**
     * Convert a text to JSON format
     * @param text text to be converted
     * @return converted text to JSON
     */
    public static String convertToJSON(final String text) {
        if (text == null || text.length() == 0) {
            return "\"\"";
        }
        char c;
        int i;
        final int len = text.length();
        final StringBuilder sb = new StringBuilder(len + 4);
        String t;
        sb.append('"');
        for (i = 0; i < len; i += 1) {
            c = text.charAt(i);
            switch (c) {
                case '\\':
                case '"':
                    sb.append('\\');
                    sb.append(c);
                    break;
                case '/':
                    sb.append('\\');
                    sb.append(c);
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                default:
                    if (c < ' ') {
                        t = "000" + Integer.toHexString(c);
                        sb.append("\\u").append(t.substring(t.length() - 4));
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append('"');
        return sb.toString();
    }

    /**
     * Convert seconds to D : H : M : S format.
     * Days : Hours : Minutes : Seconds
     * @param sec seconds to be converted
     * @return seconds converted to the format
     */
    public static String convertSecondsToDHMS(final long sec) {
        final long days = sec / (86400);
        long rest = sec % 86400;
        final long hours = rest / 3600;
        rest = rest % 3600;
        final long minutes = rest / 60;
        final long seconds = rest % 60;

        return days + "d : " + hours + "h : " + minutes + "m : " + seconds + "s";
    }

    /**
     * Convert seconds to M : S format.
     * Minutes : Seconds
     * @param sec seconds to be converted
     * @return seconds converted to the format
     */
    public static String convertSecondsToMS(final long sec) {
        final long minutes = sec / 60;
        final long seconds = sec % 60;

        return minutes + "m : " + seconds + "s";
    }

    /**
     * Convert a string list to a string
     * @param list list with all the strings
     * @param separator string to split each string
     * @return string unified
     */
    public static String convertListToString(final List<?> list, final String separator) {
        String converted = "";
        for (int i = 0; i < list.size(); i++) {
            if (i < (list.size() - 1))
                converted += list.get(i).toString().replaceAll("'", "") + separator;
            else
                converted += list.get(i).toString();
        }
        return converted;
    }
}