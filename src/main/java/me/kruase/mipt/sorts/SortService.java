package me.kruase.mipt.sorts;


import me.kruase.mipt.sorts.types.SortType;
import me.kruase.mipt.sorts.types.Sorter;

import java.util.List;


/**
 * Service class that allows choosing between multiple sorting algorithms
 */
public class SortService {
    private final List<Sorter> sorters;

    public SortService(List<Sorter> sorters) {
        this.sorters = sorters;
    }

    /**
     * Returns sorted copy of the specified list using the specified sorting algorithm
     *
     * @param list list to be sorted
     * @param type type of sorting algorithm
     * @return sorted list
     * @throws IllegalArgumentException if list is too large (see {@link Sorter#sort(List)})
     * @throws IllegalStateException if suitable {@link Sorter} was not found
     */
    public List<Integer> sort(List<Integer> list, SortType type)
            throws IllegalArgumentException, IllegalStateException {
        boolean foundSorter = false;
        for (Sorter sorter : sorters) {
            if (sorter.type() == type) {
                foundSorter = true;
                try {
                    return sorter.sort(list);
                } catch (IllegalArgumentException ignored) {
                }
            }
        }

        if (foundSorter) {
            throw new IllegalArgumentException("All sorters failed due to the large list size");
        }

        throw new IllegalStateException("Sorter with " + type + " not found");
    }
}
