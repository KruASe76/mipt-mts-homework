package me.kruase.mipt;


import me.kruase.mipt.sorts.SortService;
import me.kruase.mipt.sorts.types.BubbleSort;
import me.kruase.mipt.sorts.types.MergeSort;
import me.kruase.mipt.sorts.types.SortType;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        SortService sortService = new SortService(List.of(
                new BubbleSort(5),
                new MergeSort(5),
                new BubbleSort(100),
                new MergeSort(100)
        ));

        List<Integer> unsortedList = List.of(5, 2, 9, 1, 8, 3, 6, 4, 7, 0);
        System.out.println("Unsorted list: " + unsortedList);

        List<Integer> sortedList = sortService.sort(unsortedList, SortType.MERGE_SORT);
        System.out.println("Sorted list: " + sortedList);
    }
}
