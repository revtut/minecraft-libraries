package net.revtut.libraries.structures;

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
    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Set the first member of the pair
     * @param first new first member
     */
    public void setFirst(F first) {
        this.first = first;
    }

    /**
     * Set the second member of the pair
     * @param second second member of the pair
     */
    public void setSecond(S second) {
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
     * @param object pair object to be compared
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pair)) {
            return false;
        }

        Pair<?, ?> pair = (Pair<?, ?>) object;
        return pair.first.equals(first) && pair.second.equals(second);
    }
}
