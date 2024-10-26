package me.kruase.mipt.sorts.types;


import java.util.ArrayList;
import java.util.List;


/**
 * Bubble sort implementation of {@link Sorter} base class
 */
public class BubbleSort extends Sorter {
    /**
     * @param maxListSize {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    public BubbleSort(int maxListSize) {
        super(maxListSize);
    }

    @Override
    public SortType type() {
        return SortType.BUBBLE_SORT;
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

        for (int i = 0; i < listCopy.size(); i++) {
            for (int j = i + 1; j < listCopy.size(); j++) {
                if (listCopy.get(i) > listCopy.get(j)) {
                    int temp = listCopy.get(i);
                    listCopy.set(i, listCopy.get(j));
                    listCopy.set(j, temp);
                }
            }
        }

        return listCopy;
    }
}
