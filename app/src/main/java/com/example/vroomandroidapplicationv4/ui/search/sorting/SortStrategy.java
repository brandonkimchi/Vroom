package com.example.vroomandroidapplicationv4.ui.search.sorting;

import com.example.vroomandroidapplicationv4.ui.search.relatedtorecyclerview.Instructor;
import java.util.List;

public interface SortStrategy {
    void sort(List<Instructor> instructors);
}
