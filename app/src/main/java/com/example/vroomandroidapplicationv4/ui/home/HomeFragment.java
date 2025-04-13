package com.example.vroomandroidapplicationv4.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.example.vroomandroidapplicationv4.HomeActivity;
import com.example.vroomandroidapplicationv4.LogInActivity;
import com.example.vroomandroidapplicationv4.R;
import com.example.vroomandroidapplicationv4.databinding.FragmentHomeBinding;
import com.example.vroomandroidapplicationv4.ui.home.relatedtorecyclerview.Booking;
import com.example.vroomandroidapplicationv4.ui.home.relatedtorecyclerview.BookingAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private DatabaseReference databaseRef;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        databaseRef = FirebaseDatabase.getInstance().getReference("Registered_Users");
        refreshBookings(root);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void refreshBookings(View root) {
        String name = "NameNotFound";

        // Access the 'HomeActivity' parent activity and retrieve user data
        HomeActivity activity = (HomeActivity) getActivity();
        if (activity != null) {
            name = activity.getIntent().getStringExtra("name");
            String address = activity.getIntent().getStringExtra("address");

            Log.d("Debug User Data", "Name: " + name);
            Log.d("Debug User Data", "Address: " + address);

            // Dynamically update tvGreeting TextView
            TextView greetingTextView = root.findViewById(R.id.tvGreeting);
            greetingTextView.setText("Hi, " + name + "!");
        }

        final String finalName = name;
        ArrayList<HashMap<String, Object>> bookings = new ArrayList<>();
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<HashMap<String, Object>> bookings = new ArrayList<>();

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String fetchedName = userSnapshot.child("name").getValue(String.class);

                    if (finalName != null && finalName.equals(fetchedName)) {
                        // Get bookings
                        DataSnapshot bookingsSnapshot = userSnapshot.child("bookings");
                        for (DataSnapshot booking : bookingsSnapshot.getChildren()) {
                            HashMap<String, Object> bookingData = (HashMap<String, Object>) booking.getValue();
                            bookings.add(bookingData);
                        }
                        break;
                    }
                }

                List<Booking> bookingList = new ArrayList<>();
                for (HashMap<String, Object> map : bookings) {
                    String instructor = (String) map.get("instructor");
                    int day = ((Long) map.get("day")).intValue();
                    int month = ((Long) map.get("month")).intValue();
                    int year = ((Long) map.get("year")).intValue();
                    String time = (String) map.get("time");
                    String vehicleClass = (String) map.get("vehicle_class");
                    String address = (String) map.get("address");
                    String drivingCenter = (String) map.get("driving_center");

                    bookingList.add(new Booking(instructor, day, month, year, time, vehicleClass, address, drivingCenter));
                }

                // Once you have your 'bookingList' built, sort it with a custom Comparator according to chronological order
                Collections.sort(bookingList, new Comparator<Booking>() {
                    @Override
                    public int compare(Booking b1, Booking b2) {
                        // First compare year
                        int yearComparison = Integer.compare(b1.getYear(), b2.getYear());
                        if (yearComparison != 0) {
                            return yearComparison;
                        }

                        // Then compare month
                        int monthComparison = Integer.compare(b1.getMonth(), b2.getMonth());
                        if (monthComparison != 0) {
                            return monthComparison;
                        }

                        // Then compare day
                        int dayComparison = Integer.compare(b1.getDay(), b2.getDay());
                        if (dayComparison != 0) {
                            return dayComparison;
                        }

                        // Finally, compare time slot
                        int timeSlotOrder1 = getTimeSlotOrder(b1.getTime());
                        int timeSlotOrder2 = getTimeSlotOrder(b2.getTime());
                        return Integer.compare(timeSlotOrder1, timeSlotOrder2);
                    }
                });

                // 📌 Access your two TextViews
                TextView tvUpcoming3 = root.findViewById(R.id.tvUpcomingLessons3);
                TextView tvUpcoming = root.findViewById(R.id.tvUpcomingLessons);

                // 📌 Handle condition to show/hide views based on bookingList size
                if (bookingList.isEmpty()) {
                    tvUpcoming3.setVisibility(View.GONE);
                    tvUpcoming.setVisibility(View.VISIBLE);
                } else {
                    tvUpcoming.setVisibility(View.GONE);
                    tvUpcoming3.setVisibility(View.VISIBLE);
                }

                RecyclerView recyclerView = root.findViewById(R.id.my_recycler_view); // add correct ID in XML
                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
                recyclerView.setAdapter(new BookingAdapter(bookingList));
            }
            @Override
            public void onCancelled (@NonNull DatabaseError error){
                Toast.makeText(requireContext(), "Database error occurred.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getTimeSlotOrder(String timeSlot) {
        switch (timeSlot) {
            case "9am - 11am":
                return 0;
            case "1pm - 3pm":
                return 1;
            case "4pm - 6pm":
                return 2;
            case "8pm - 10pm":
                return 3;
            default:
                // If there's an unexpected time slot, place it at the end (or handle it however you prefer)
                return Integer.MAX_VALUE;
        }
    }
}