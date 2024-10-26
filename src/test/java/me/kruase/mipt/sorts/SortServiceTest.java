package me.kruase.mipt.sorts;


import me.kruase.mipt.sorts.types.BubbleSort;
import me.kruase.mipt.sorts.types.MergeSort;
import me.kruase.mipt.sorts.types.SortType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class SortServiceTest {
    public static final List<Integer> unsortedList = List.of(5, 2, 9, 1, 8, 3, 6, 4, 7, 0);
    public static final List<Integer> sortedList = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

    @Test
    void testSort() {
        SortService sortService = new SortService(List.of(
                new BubbleSort(100),
                new MergeSort(100)
        ));

        assertEquals(sortedList, sortService.sort(unsortedList, SortType.MERGE_SORT));
    }

    @Test
    void testSortThrowsIllegalArgumentException() {
        SortService sortService = new SortService(List.of(
                new BubbleSort(1),
                new MergeSort(100)
        ));

        assertThrows(IllegalArgumentException.class,
                     () -> sortService.sort(unsortedList, SortType.BUBBLE_SORT));
    }

    @Test
    void testSortThrowsIllegalStateException() {
        SortService sortService = new SortService(List.of(
                new BubbleSort(100)
        ));

        assertThrows(IllegalStateException.class,
                     () -> sortService.sort(unsortedList, SortType.MERGE_SORT));
    }
}
