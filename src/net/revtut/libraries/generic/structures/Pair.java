package net.revtut.libraries.generic.structures;

/**
 * Pair Template Class
 */
public class Pair<F, S> {

    /**
     * First member of the pair
     */
    private F first;

    /**
     * Second member of the pair
     */
    private S second;

    /**
     * Constructor of Pair
     * @param first first member of the pair
     * @param second second member of the pair
     */
    public Pair(final F first, final S second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Set the first member of the pair
     * @param first new first member
     */
    public void setFirst(final F first) {
        this.first = first;
    }

    /**
     * Set the second member of the pair
     * @param second second member of the pair
     */
    public void setSecond(final S second) {
        this.second = second;
    }

    /**
     * Get the first member of the pair
     * @return first member of the pair
     */
    public F getFirst() {
        return first;
    }

    /**
     * Get the second member of the pair
     * @return second member of the pair
     */
    public S getSecond() {
        return second;
    }

    /**
     * Check if two pairs are equal
     * @param obj pair object to be compared
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Pair)) {
            return false;
        }

        final Pair<?, ?> pair = (Pair<?, ?>) obj;
        return pair.first.equals(first) && pair.second.equals(second);
    }
}
