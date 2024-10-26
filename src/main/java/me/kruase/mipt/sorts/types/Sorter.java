package me.kruase.mipt.sorts.types;


import java.util.List;


/**
 * Base sorter class
 */
public abstract class Sorter {
    protected final int maxListSize;

    /**
     * @param maxListSize If {@link List} size exceeds this maximum,
     *                    {@link IllegalArgumentException} will be thrown
     *                    on {@link #sort(List)} invocation
     * @throws IllegalArgumentException if {@code maxListSize} it is not positive
     */
    public Sorter(int maxListSize) throws IllegalArgumentException {
        if (maxListSize <= 0) {
            throw new IllegalArgumentException("maxListSize must be positive");
        }

        this.maxListSize = maxListSize;
    }

    public abstract SortType type();

    /**
     * Returns sorted copy of the specified list
     *
     * @param list list to be sorted
     * @return sorted list
     * @throws IllegalArgumentException if list size exceeds {@code maxListSize}
     */
    public abstract List<Integer> sort(List<Integer> list) throws IllegalArgumentException;
}
