package com.example.vroomandroidapplicationv4.ui.search.relatedtocalenderview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.vroomandroidapplicationv4.HomeActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.example.vroomandroidapplicationv4.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TimeSelectionBottomSheet extends BottomSheetDialogFragment {

    private String selectedDate; // Store selected date
    private String selectedTime = "";
    private String instructorVehicleClass;
    private Button btnBook; // Book button reference
    private String instructorName;
    private String instructorAddress;
    private String instructorDrivingCenter;
    private String username;
    private DatabaseReference databaseRef;

    public TimeSelectionBottomSheet(String date) {
        this.selectedDate = date; // Receive date from calendar
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_time_selection, container, false);

        // Access the 'HomeActivity' parent activity and retrieve data
        HomeActivity activity = (HomeActivity) getActivity();
        if (activity != null) {
            username = activity.getIntent().getStringExtra("name");
            String address = activity.getIntent().getStringExtra("address");
            ArrayList<HashMap<String, Object>> bookings =
                    (ArrayList<HashMap<String, Object>>) activity.getIntent().getSerializableExtra("bookings");

            Log.d("SearchInstructorFragment", "Username: " + username);
            Log.d("SearchInstructorFragment", "Address: " + address);
            Log.d("SearchInstructorFragment", "Bookings: " + bookings);

            if (bookings != null) {
                for (int i = 0; i < bookings.size(); i++) {
                    Log.d("HomeActivity_DATA", "Booking " + i + ": " + bookings.get(i).toString());
                }
            } else {
                Log.d("HomeActivity_DATA", "No bookings received.");
            }
        }

        // Get the bundle from SearchCalenderFragment
        if (getArguments() != null) {
            // 🔵 Log all bundle contents
            for (String key : getArguments().keySet()) {
                Object value = getArguments().get(key);
                Log.d("BUNDLE_DEBUG", "Key: " + key + " | Value: " + String.valueOf(value));
            }

            selectedDate = getArguments().getString("selectedDate");
            instructorName = getArguments().getString("instructor_name");
            selectedDate = getArguments().getString("selectedDate");
            instructorVehicleClass = getArguments().getString("instructor_vehicle_class");
            instructorDrivingCenter = getArguments().getString("instructor_driving_center");
            instructorAddress = getArguments().getString("instructor_address");
        } else {
            Log.d("BUNDLE_DEBUG", "getArguments() returned null");
        }

        // Set selected date text
        TextView tvSelectedDate = view.findViewById(R.id.tvSelectedDate);

        // Time slot buttons
        Button btn9to11 = view.findViewById(R.id.btn_9to11);
        Button btn1to3 = view.findViewById(R.id.btn_1to3);
        Button btn4to6 = view.findViewById(R.id.btn_4to6);
        Button btn8to10 = view.findViewById(R.id.btn_8to10);
        btnBook = view.findViewById(R.id.btnBook); // Initialize book button

        // Hide book button initially
        btnBook.setVisibility(View.GONE);

        // Handle time slot selection
        View.OnClickListener timeSlotClickListener = v -> {
            v.setEnabled(false);
            selectedTime = ((Button) v).getText().toString(); // Save selected time globally
            Toast.makeText(getActivity(), "Selected: " + selectedTime, Toast.LENGTH_SHORT).show();

            // Show book button once a time is selected
            btnBook.setVisibility(View.VISIBLE);
        };

        btn9to11.setOnClickListener(timeSlotClickListener);
        btn1to3.setOnClickListener(timeSlotClickListener);
        btn4to6.setOnClickListener(timeSlotClickListener);
        btn8to10.setOnClickListener(timeSlotClickListener);

        // Book Button Click
        btnBook.setOnClickListener(v -> {
            // Example: "Thu Mar 13 00:00:00 GMT 2025"
            String[] parts = selectedDate.split(" ");
            if (parts.length >= 6) {
                // Extracting as "13 Mar 2025"
                String formattedDate = parts[2] + " " + parts[1] + " " + parts[5];

                // Full message: Date + Time
                String message = "Booking Confirmed for " + formattedDate + " at " + selectedTime;
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Date format unexpected!", Toast.LENGTH_SHORT).show();
            }
            addBookingToFirebase(username);
            dismiss(); // Close the bottom sheet
        });

        return view;
    }

    private void addBookingToFirebase(String loggedInUserName) {
        databaseRef = FirebaseDatabase.getInstance().getReference("Registered_Users");

        databaseRef.orderByChild("name").equalTo(username)
                .addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
                        Log.d("FIREBASE_SNAPSHOT", snapshot.toString()); // 🔵 Print the entire snapshot
                        if (snapshot.exists()) {
                            for (com.google.firebase.database.DataSnapshot userSnapshot : snapshot.getChildren()) {
                                DatabaseReference bookingsRef = userSnapshot.getRef().child("bookings");

                                // Parse date
                                String[] parts = selectedDate.split(" ");
                                int day = Integer.parseInt(parts[2]);
                                int month = convertMonthToNumber(parts[1]); // Helper to convert "Mar" -> 3
                                int year = Integer.parseInt(parts[5]);

                                Map<String, Object> newBooking = new HashMap<>();
                                newBooking.put("day", day);
                                newBooking.put("month", month);
                                newBooking.put("year", year);
                                newBooking.put("instructor", instructorName); // Replace with actual instructor
                                newBooking.put("vehicle_class", instructorVehicleClass); // Replace dynamically too
                                newBooking.put("time", selectedTime);
                                newBooking.put("address", instructorAddress);
                                newBooking.put("driving_center", instructorDrivingCenter);

                                // 🔵 Push new booking
                                bookingsRef.push().setValue(newBooking);
//                                        .addOnSuccessListener(unused -> Toast.makeText(getActivity(), "Added to Firebase!", Toast.LENGTH_SHORT).show())
//                                        .addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                            }
                        } else {
                            Log.d("Firebase Database", "You got an error!");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull com.google.firebase.database.DatabaseError error) {
                        Toast.makeText(getActivity(), "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Convert month abbreviation to int (e.g., "Mar" -> 3)
    private int convertMonthToNumber(String month) {
        switch (month) {
            case "Jan": return 1;
            case "Feb": return 2;
            case "Mar": return 3;
            case "Apr": return 4;
            case "May": return 5;
            case "Jun": return 6;
            case "Jul": return 7;
            case "Aug": return 8;
            case "Sep": return 9;
            case "Oct": return 10;
            case "Nov": return 11;
            case "Dec": return 12;
            default: return 0;
        }
    }
}
