package com.example.vroomandroidapplicationv4.ui.search.datastructuresandalgorithms;

import com.example.vroomandroidapplicationv4.ui.search.relatedtorecyclerview.Instructor;

import java.util.List;

public class heapsortalgorithm {
    // Sort by rating (High to Low)
    public static void heapSortByRating(List<Instructor> list) {
        int n = list.size();

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapifyByRating(list, n, i);
        }

        // Heap sort logic
        for (int i = n - 1; i > 0; i--) {
            swap(list, 0, i);
            heapifyByRating(list, i, 0);
        }
    }

    private static void heapifyByRating(List<Instructor> list, int n, int i) {
        int smallest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && list.get(left).getRating() < list.get(smallest).getRating()) {
            smallest = left;
        }

        if (right < n && list.get(right).getRating() < list.get(smallest).getRating()) {
            smallest = right;
        }

        if (smallest != i) {
            swap(list, i, smallest);
            heapifyByRating(list, n, smallest);
        }
    }

    // Sort by price (Low to High)
    public static void heapSortByPrice(List<Instructor> list, boolean ascending) {
        int n = list.size();

        // Build heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapifyByPrice(list, n, i, ascending);
        }

        // Heap sort logic
        for (int i = n - 1; i > 0; i--) {
            swap(list, 0, i);
            heapifyByPrice(list, i, 0, ascending);
        }
    }

    private static void heapifyByPrice(List<Instructor> list, int n, int i, boolean ascending) {
        int selected = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        int priceSelected = getPrice(list.get(selected));
        int priceLeft = (left < n) ? getPrice(list.get(left)) : Integer.MAX_VALUE;
        int priceRight = (right < n) ? getPrice(list.get(right)) : Integer.MAX_VALUE;

        if (ascending) {
            if (left < n && priceLeft < priceSelected) {
                selected = left;
                priceSelected = priceLeft;
            }
            if (right < n && priceRight < priceSelected) {
                selected = right;
            }
        } else {
            if (left < n && priceLeft > priceSelected) {
                selected = left;
                priceSelected = priceLeft;
            }
            if (right < n && priceRight > priceSelected) {
                selected = right;
            }
        }

        if (selected != i) {
            swap(list, i, selected);
            heapifyByPrice(list, n, selected, ascending);
        }
    }

    // Utility: Extract integer price from string like "$70/lesson"
    private static int getPrice(Instructor instructor) {
        return Integer.parseInt(instructor.getPrice().replaceAll("[^0-9]", ""));
    }

    // Swap helper
    private static void swap(List<Instructor> list, int i, int j) {
        Instructor temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
