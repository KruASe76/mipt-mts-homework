package me.kruase.mipt.sorts.types;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Merge sort implementation of {@link Sorter} base class
 */
public class MergeSort extends Sorter {
    /**
     * @param maxListSize {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    public MergeSort(int maxListSize) {
        super(maxListSize);
    }

    @Override
    public SortType type() {
        return SortType.MERGE_SORT;
    }

    /**
     * {@inheritDoc}
     *
     * @param list {@inheritDoc}
     * @return {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    public List<Integer> sort(List<Integer> list) throws IllegalArgumentException {
        if (list.size() > maxListSize) {
            throw new IllegalArgumentException("List size exceeds maximum allowed limit");
        }

        ArrayList<Integer> listCopy = new ArrayList<>(list);

        Collections.sort(listCopy);

        return listCopy;
    }
}
