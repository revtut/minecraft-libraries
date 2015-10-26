package net.revtut.libraries.minecraft.common.text.checks;

/**
 * Caps Lock Check
 */
public class CapsCheck implements Check {

    /**
     * Maximum number of capitals allowed in percentage
     */
    private static final double MAX_CAPITALS = 0.25D;

    /**
     * Check if message matches the check
     * @param message message to check
     * @return true if matches, false otherwise
     */
    @Override
    public boolean checkMessage(final String message) {
        double nCapitals = 0;
        for(final char character : message.toCharArray())
            if(Character.isUpperCase(character))
                nCapitals++;
        return (nCapitals / message.length()) >= MAX_CAPITALS;
    }

    /**
     * Fixes the message in order to remove / replace some elements
     * @param message message to be fixed
     * @return fixed message
     */
    @Override
    public String fixMessage(final String message) {
        return message.toLowerCase();
    }

    /**
     * Get the error message of the check
     * @return error message of the check
     */
    @Override
    public String getErrorMessage() {
        return "§4You may not write in caps lock here!";
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
