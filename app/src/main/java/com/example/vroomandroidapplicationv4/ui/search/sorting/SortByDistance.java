package com.example.vroomandroidapplicationv4.ui.search.sorting;

import android.util.Log;
import com.example.vroomandroidapplicationv4.ui.search.relatedtorecyclerview.Instructor;
import com.example.vroomandroidapplicationv4.ui.search.datastructuresandalgorithms.UndirectedWeightedGraphDataStructureAndDijkstraAlgorithm;
import java.util.*;

public class SortByDistance implements SortStrategy {
    private final String userLocation;

    public SortByDistance(String userLocation) {
        this.userLocation = userLocation;
    }

    @Override
    public void sort(List<Instructor> instructors) {
        UndirectedWeightedGraphDataStructureAndDijkstraAlgorithm graph =
                new UndirectedWeightedGraphDataStructureAndDijkstraAlgorithm();
        Map<String, Integer> distances = graph.dijkstra(userLocation);

        Collections.sort(instructors, (i1, i2) -> {
            int d1 = distances.getOrDefault(i1.getAddress(), Integer.MAX_VALUE);
            int d2 = distances.getOrDefault(i2.getAddress(), Integer.MAX_VALUE);
            return Integer.compare(d1, d2);
        });
        Log.d("SortAction", "Sorted by distance using Dijkstra");
    }
}
