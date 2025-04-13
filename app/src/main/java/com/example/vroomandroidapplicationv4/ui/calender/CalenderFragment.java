package com.example.vroomandroidapplicationv4.ui.calender;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
    private Map<CalendarDay, List<Event>> eventsMap = new HashMap<>();
    private DatabaseReference databaseRef;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calender, container, false);

        // Init views
        materialCalendarView = root.findViewById(R.id.materialCalendarView);
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        eventsAdapter = new EventsAdapter(new ArrayList<>());
        recyclerView.setAdapter(eventsAdapter);

        // Firebase setup
        databaseRef = FirebaseDatabase.getInstance().getReference("Registered_Users");

        // Fetch Firebase bookings into calendar & recyclerview
        fetchBookingsFromFirebase();

        // Respond to date selection
        materialCalendarView.setOnDateChangedListener((widget, date, selected) -> {
            List<Event> eventsForDay = eventsMap.containsKey(date) ? eventsMap.get(date) : new ArrayList<>();

            // Sort events before displaying
            Collections.sort(eventsForDay, (e1, e2) -> {
                // Example: extract time slot ordering
                int slot1 = getTimeSlotOrder(e1.getDateTime());
                int slot2 = getTimeSlotOrder(e2.getDateTime());
                return Integer.compare(slot1, slot2);
            });

            // Update RecyclerView
            eventsAdapter.updateData(eventsForDay);
        });

        return root;
    }

    private void fetchBookingsFromFirebase() {
        // Get user name passed from HomeActivity
        HomeActivity activity = (HomeActivity) getActivity();
        if (activity == null) return;
        String currentUserName = activity.getIntent().getStringExtra("name");

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventsMap.clear();

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String fetchedName = userSnapshot.child("name").getValue(String.class);
                    if (currentUserName.equals(fetchedName)) {
                        // Load bookings
                        DataSnapshot bookingsSnapshot = userSnapshot.child("bookings");
                        for (DataSnapshot booking : bookingsSnapshot.getChildren()) {
                            HashMap<String, Object> bookingData = (HashMap<String, Object>) booking.getValue();

                            int day = ((Long) bookingData.get("day")).intValue();
                            int month = ((Long) bookingData.get("month")).intValue();
                            int year = ((Long) bookingData.get("year")).intValue();
                            String time = (String) bookingData.get("time");
                            String instructor = (String) bookingData.get("instructor");
                            String drivingCenter = (String) bookingData.get("driving_center");
                            String address = (String) bookingData.get("address");

                            CalendarDay calendarDay = CalendarDay.from(year, month - 1, day);

                            Event event = new Event(
                                    "Lesson with " + instructor + " at " + drivingCenter,
                                    day + "/" + month + "/" + year + " | " + time,
                                    "Pickup at " + address + ". Please arrive 15 minutes earlier!"
                            );

                            if (!eventsMap.containsKey(calendarDay)) {
                                eventsMap.put(calendarDay, new ArrayList<>());
                            }
                            eventsMap.get(calendarDay).add(event);
                        }
                        break;
                    }
                }

                // After building the map:
                materialCalendarView.removeDecorators(); // Optional: clear old decorators
                materialCalendarView.addDecorator(new EventDecorator(Color.RED, eventsMap.keySet()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Error loading calendar bookings", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getTimeSlotOrder(String dateTime) {
        if (dateTime.contains("9am - 11am")) return 0;
        if (dateTime.contains("1pm - 3pm")) return 1;
        if (dateTime.contains("4pm - 6pm")) return 2;
        if (dateTime.contains("8pm - 10pm")) return 3;
        return Integer.MAX_VALUE;
    }
}
