package com.example.vroomandroidapplicationv4.ui.calender;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vroomandroidapplicationv4.ui.calender.relatedtorecyclerview.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalenderViewModel extends ViewModel {

    private final MutableLiveData<Map<CalendarDay, List<Event>>> eventsMapLiveData = new MutableLiveData<>(new HashMap<>());

    public LiveData<Map<CalendarDay, List<Event>>> getEventsMapLiveData() {
        return eventsMapLiveData;
    }

    public void fetchBookings(DatabaseReference databaseRef, String currentUserName) {
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<CalendarDay, List<Event>> resultMap = new HashMap<>();

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String fetchedName = userSnapshot.child("name").getValue(String.class);
                    if (currentUserName.equals(fetchedName)) {
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

                            if (!resultMap.containsKey(calendarDay)) {
                                resultMap.put(calendarDay, new ArrayList<>());
                            }
                            resultMap.get(calendarDay).add(event);
                        }
                        break;
                    }
                }

                eventsMapLiveData.setValue(resultMap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // handle error if needed
            }
        });
    }
}
