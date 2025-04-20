package com.example.vroomandroidapplicationv4.ui.calender;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vroomandroidapplicationv4.HomeActivity;
import com.example.vroomandroidapplicationv4.R;
import com.example.vroomandroidapplicationv4.ui.calender.relatedtorecyclerview.Event;
import com.example.vroomandroidapplicationv4.ui.calender.relatedtorecyclerview.EventsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CalenderFragment extends Fragment {

    private MaterialCalendarView materialCalendarView;
    private RecyclerView recyclerView;
    private EventsAdapter eventsAdapter;
    private CalenderViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calender, container, false);

        // Init views
        materialCalendarView = root.findViewById(R.id.materialCalendarView);
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Single adapter instantiation
        eventsAdapter = new EventsAdapter(new ArrayList<>());
        recyclerView.setAdapter(eventsAdapter);

        // ViewModel setup
        viewModel = new ViewModelProvider(this).get(CalenderViewModel.class);

        // Observe LiveData from ViewModel
        viewModel.getEventsMapLiveData().observe(getViewLifecycleOwner(), updatedMap -> {
            materialCalendarView.removeDecorators();
            materialCalendarView.addDecorator(new EventDecorator(Color.RED, updatedMap.keySet()));

            CalendarDay selected = materialCalendarView.getSelectedDate();
            if (selected != null && updatedMap.containsKey(selected)) {
                eventsAdapter.updateData(updatedMap.get(selected));
            } else {
                eventsAdapter.updateData(new ArrayList<>());
            }
        });

        // Fetch Firebase data
        HomeActivity activity = (HomeActivity) getActivity();
        if (activity != null) {
            String currentUserName = activity.getIntent().getStringExtra("name");
            viewModel.fetchBookings(FirebaseDatabase.getInstance().getReference("Registered_Users"), currentUserName);
        }

        // When user selects a new date on the calendar
        materialCalendarView.setOnDateChangedListener((widget, date, selected) -> {
            Map<CalendarDay, List<Event>> eventMap = viewModel.getEventsMapLiveData().getValue();
            if (eventMap != null) {
                List<Event> eventsForDay = eventMap.getOrDefault(date, new ArrayList<>());
                Collections.sort(eventsForDay, (e1, e2) -> {
                    int slot1 = getTimeSlotOrder(e1.getDateTime());
                    int slot2 = getTimeSlotOrder(e2.getDateTime());
                    return Integer.compare(slot1, slot2);
                });
                eventsAdapter.updateData(eventsForDay);
            }
        });

        return root;
    }

    private int getTimeSlotOrder(String dateTime) {
        if (dateTime.contains("9am - 11am")) return 0;
        if (dateTime.contains("1pm - 3pm")) return 1;
        if (dateTime.contains("4pm - 6pm")) return 2;
        if (dateTime.contains("8pm - 10pm")) return 3;
        return Integer.MAX_VALUE;
    }
}
