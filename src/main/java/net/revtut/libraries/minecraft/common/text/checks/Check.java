package net.revtut.libraries.minecraft.common.text.checks;

/**
 * Check Interface
 */
public interface Check {

    /**
     * Check if message / player matches the check
     * @param message message to check
     * @return true if matches, false otherwise
     */
    boolean checkMessage(String message);

    /**
     * Fixes the message in order to remove / replace some elements
     * @param message message to be fixed
     * @return fixed message
     */
    String fixMessage(String message);

    /**
     * Get the error message of the check
     * @return error message of the check
     */
    String getErrorMessage();

    /**
     * Get the violation level of the check
     * @return violation level of the check
     */
    int getViolationLevel();
}
