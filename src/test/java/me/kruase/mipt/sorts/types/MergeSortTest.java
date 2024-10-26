package me.kruase.mipt.sorts.types;


import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class MergeSortTest {
    public static final List<Integer> unsortedList = List.of(5, 2, 9, 1, 8, 3, 6, 4, 7, 0);
    public static final List<Integer> sortedList = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

    @Test
    void testType() {
        assertEquals(SortType.MERGE_SORT, new MergeSort(10).type());
    }

    @Test
    void testSort() {
        MergeSort mergeSort = new MergeSort(10);

        assertEquals(sortedList, mergeSort.sort(unsortedList));
    }

    @Test
    void testSortThrows() {
        MergeSort mergeSort = new MergeSort(1);

        assertThrows(IllegalArgumentException.class, () -> mergeSort.sort(unsortedList));
    }
}
