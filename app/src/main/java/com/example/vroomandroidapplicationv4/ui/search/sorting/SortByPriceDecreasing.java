package com.example.vroomandroidapplicationv4.ui.search.sorting;

import com.example.vroomandroidapplicationv4.ui.search.relatedtorecyclerview.Instructor;
import com.example.vroomandroidapplicationv4.ui.search.datastructuresandalgorithms.HeapSortAlgorithm;
import java.util.List;

public class SortByPriceDecreasing implements SortStrategy {
    @Override
    public void sort(List<Instructor> instructors) {
        HeapSortAlgorithm.heapSortByPrice(instructors, true);
    }
}
