package com.example.vroomandroidapplicationv4.ui.search;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vroomandroidapplicationv4.HomeActivity;
import com.example.vroomandroidapplicationv4.R;
import com.example.vroomandroidapplicationv4.databinding.FragmentSearchInstructorBinding;
import com.example.vroomandroidapplicationv4.ui.search.datastructuresandalgorithms.heapsortalgorithm;
import com.example.vroomandroidapplicationv4.ui.search.datastructuresandalgorithms.undirectedweightedgraphdatastructureanddijkstraalgorithm;
import com.example.vroomandroidapplicationv4.ui.search.relatedtorecyclerview.CustomAdapter;
import com.example.vroomandroidapplicationv4.ui.search.relatedtorecyclerview.Instructor;
import com.example.vroomandroidapplicationv4.ui.search.relatedtorecyclerview.Review;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.prolificinteractive.materialcalendarview.CalendarDay;

public class SearchInstructorFragment extends Fragment {

    private FragmentSearchInstructorBinding binding;
    private CustomAdapter customAdapter;
    private List<Instructor> instructorList;
    private ImageView ivClass2B, ivClass2A, ivClass2, ivClass3, ivClass3A;

    private String selectedDrivingCenter;
    private ArrayList<String> selectedVehicleClasses;
    private static final String TAG = "FirebaseDebug"; // Define custom tag


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_search_instructor, container, false);

        // Access the 'HomeActivity' parent activity and retrieve user data
        HomeActivity activity = (HomeActivity) getActivity();
        if (activity != null) {
            String name = activity.getIntent().getStringExtra("name");
            String address = activity.getIntent().getStringExtra("address");
            ArrayList<HashMap<String, Object>> bookings =
                    (ArrayList<HashMap<String, Object>>) activity.getIntent().getSerializableExtra("bookings");

            Log.d("SearchInstructorFragment", "Name: " + name);
            Log.d("SearchInstructorFragment", "Address: " + address);
            Log.d("SearchInstructorFragment", "Bookings: " + bookings);
        }

        // Retrieve the data from the Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            // Log all keys and values in the bundle for debugging
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                Log.d("BundleContents", "Key: " + key + ", Value: " + value);
            }

            // Retrieve specific values from the bundle
            selectedDrivingCenter = bundle.getString("selectedDrivingCenter");
            selectedVehicleClasses = bundle.getStringArrayList("selectedVehicleClasses");

            // Log the retrieved values separately
            Log.d("BundleContents", "Selected Driving Center: " + selectedDrivingCenter);
            Log.d("BundleContents", "Selected Vehicle Classes: " + selectedVehicleClasses);
        } else {
            Log.d("BundleContents", "Bundle is null");
        }


        /////////////////////////////////////////////////////////////////////////////////


        /////////////////////////////////////////////////////
        // Connecting to Firebase Realtime Database stuffs //
        /////////////////////////////////////////////////////
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        RecyclerView recyclerView = root.findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(customAdapter);
        instructorList = new ArrayList<>();

        // myRef.setValue("Hello, World!");
        Log.d(TAG, "Database write operation executed."); // Log the write operation

        // Reference to the TextView
        TextView instructorCountTextView = root.findViewById(R.id.textView4);

        // Read from the database and load data into the instructor list
        myRef.child("Instructors").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("FirebaseDatabase", "Database Dump: " + dataSnapshot.getValue());
                instructorList.clear(); // Clear list before adding new data

                for (DataSnapshot instructorSnapshot : dataSnapshot.getChildren()) {
                    String name = instructorSnapshot.child("Name").getValue(String.class);
                    String shortDescription = instructorSnapshot.child("Description").getValue(String.class);
                    String fullDescription = instructorSnapshot.child("Description").getValue(String.class);
                    String price = "$" + instructorSnapshot.child("Price").getValue(Integer.class) + "/lesson";
                    double rating = instructorSnapshot.child("Rating").getValue(Double.class);
                    String profilePicName = instructorSnapshot.child("Profile Picture File").getValue(String.class);
                    String vehicleClass = instructorSnapshot.child("Vehicle Class").getValue(String.class);
                    String address = instructorSnapshot.child("Address").getValue(String.class);
                    String drivingCenter = instructorSnapshot.child("Driving Center").getValue(String.class);

                    // 🔵 Deserialize Dates Unavailable
                    List<CalendarDay> datesUnavailable = new ArrayList<>();
                    DataSnapshot datesSnapshot = instructorSnapshot.child("Dates Unavailable");
                    if (datesSnapshot.exists()) {
                        for (DataSnapshot dateEntry : datesSnapshot.getChildren()) {
                            Map<String, Long> dateMap = (Map<String, Long>) dateEntry.getValue();
                            if (dateMap != null && dateMap.containsKey("year") && dateMap.containsKey("month") && dateMap.containsKey("day")) {
                                int year = dateMap.get("year").intValue();
                                int month = dateMap.get("month").intValue();
                                int day = dateMap.get("day").intValue();
                                datesUnavailable.add(CalendarDay.from(year, month - 1, day));
                            }
                        }
                    }

                    // 🔵 Deserialize Reviews Map<String, Review>
                    Map<String, Review> reviews = new HashMap<>();
                    DataSnapshot reviewsSnapshot = instructorSnapshot.child("Reviews");
                    if (reviewsSnapshot.exists()) {
                        for (DataSnapshot reviewEntry : reviewsSnapshot.getChildren()) {
                            String reviewerName = reviewEntry.getKey();
                            double reviewerRating = reviewEntry.child("Rating").getValue(Double.class);
                            String reviewerComment = reviewEntry.child("Review").getValue(String.class);
                            reviews.put(reviewerName, new Review(reviewerRating, reviewerComment));
                        }
                    }

                    // Load only the first 30 characters of description
                    if (shortDescription != null && shortDescription.length() > 30) {
                        shortDescription = shortDescription.substring(0, 30) + "..."; // Truncate and add ellipsis
                    }

                    // Filter: Check if instructor matches selected driving center
                    if (!drivingCenter.equals(selectedDrivingCenter)) {
                        continue; // Skip this instructor if driving center does not match
                    }

                    // 'Filter: Check if instructor's vehicle class is in the selected list' AND FOR 'Special condition: If selectedVehicleClasses is empty, accept all instructors from this driving center'
                    if (!selectedVehicleClasses.isEmpty() && !selectedVehicleClasses.contains(vehicleClass)) {
                        continue; // Skip this instructor if their vehicle class is not in the list
                    }

                    // Get profile picture resource ID dynamically (If images are in drawable folder)
                    int profilePicResId = getResources().getIdentifier(profilePicName, "drawable", requireActivity().getPackageName());

                    // Create Instructor object and add to list
                    Instructor instructor = new Instructor(name, shortDescription, fullDescription, price, rating, profilePicResId, vehicleClass, address, drivingCenter, datesUnavailable, reviews);
                    instructorList.add(instructor);

                    // 🔹 Sort Alphabetically by Name (Default Sorting)
                    Collections.sort(instructorList, new Comparator<Instructor>() {
                        @Override
                        public int compare(Instructor o1, Instructor o2) {
                            return o1.getName().compareToIgnoreCase(o2.getName()); // Alphabetical order
                        }
                    });

                    // Update TextView with the count of instructors
                    instructorCountTextView.setText(instructorList.size() + " instructors found");

                    // Notify RecyclerView adapter of data changes
                    customAdapter.notifyDataSetChanged();
                }

                // Notify RecyclerView adapter of data changes
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("FirebaseDatabase", "Failed to read database.", databaseError.toException());
            }
        });

        // 🔹 Initialize adapter and set it
        customAdapter = new CustomAdapter(instructorList, requireActivity());
        recyclerView.setAdapter(customAdapter);


        /////////////////////////////////////////////////////////////////////////////////


//        // Use the data as needed
//        if ((selectedDrivingCenter != null) && (selectedVehicleClasses != null)) {
//
//            RecyclerView recyclerView = root.findViewById(R.id.my_recycler_view);
//            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
//            recyclerView.setAdapter(customAdapter);
//
//            // 🔹 Initialize dataset
//            instructorList = new ArrayList<>();
//            instructorList.add(new Instructor("Vernice Kah", "Been teaching for more than 10 years.", "$75/lesson", 5.0, R.drawable.profile_vernice_kah));
//            instructorList.add(new Instructor("Carlo Cenina", "10 years of teaching experience.", "$78/lesson", 4.9, R.drawable.profile_carlo_cenina));
//            instructorList.add(new Instructor("Jet Wei", "Been teaching for more than 10 years.", "$70/lesson", 4.9, R.drawable.profile_jet_wei));
//
            // 🔹 Initialize adapter and set it
//            customAdapter = new CustomAdapter(instructorList, requireActivity());
//            recyclerView.setAdapter(customAdapter);
//        }

        // Reference to the TextView and set dynamic address
        TextView userAddressTextView = root.findViewById(R.id.textView3);

        if (activity != null) {
            String name = activity.getIntent().getStringExtra("name");
            String address = activity.getIntent().getStringExtra("address");
            ArrayList<HashMap<String, Object>> bookings =
                    (ArrayList<HashMap<String, Object>>) activity.getIntent().getSerializableExtra("bookings");

            Log.d("SearchInstructorFragment", "Name: " + name);
            Log.d("SearchInstructorFragment", "Address: " + address);
            Log.d("SearchInstructorFragment", "Bookings: " + bookings);

            if (address != null && !address.isEmpty()) {
                userAddressTextView.setText(" " + address);
            } else {
                userAddressTextView.setText("Not available");
            }
        }


        // Find the back button by ID
        Button backButton = root.findViewById(R.id.back_button3);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pop the current fragment from the back stack
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });


        // Get reference to the buttons
        Button btnSortByRating = root.findViewById(R.id.btnFilters); // Button for sorting by rating
        Button btnSortByPriceIncreasing = root.findViewById(R.id.btnFilters3); // Button for sorting by price
        Button btnSortByPriceDecreasing = root.findViewById(R.id.btnFilters2); // Button for sorting by price

        // Set click listener for sorting by rating
        btnSortByRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByRating();
            }
        });

        // Set click listener for sorting by price
        btnSortByPriceIncreasing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByPriceIncreasing();
            }
        });

        // Set click listener for sorting by price
        btnSortByPriceDecreasing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByPriceDecreasing();
            }
        });

        // Set click listener for sorting by distance
        Button btnSortByDistance = root.findViewById(R.id.btnFilters4);
        btnSortByDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByDistance();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void sortByRating() {
        if (instructorList != null && !instructorList.isEmpty()) {
            heapsortalgorithm.heapSortByRating(instructorList);
            customAdapter.notifyDataSetChanged();
            Log.d("SortAction", "Sorted by rating (High to Low) using HeapSort");
        }
    }

    public void sortByPriceIncreasing() {
        if (instructorList != null && !instructorList.isEmpty()) {
            heapsortalgorithm.heapSortByPrice(instructorList, false);
            customAdapter.notifyDataSetChanged();
            Log.d("SortAction", "Sorted by price (Low to High) using HeapSort");
        }
    }

    public void sortByPriceDecreasing() {
        if (instructorList != null && !instructorList.isEmpty()) {
            heapsortalgorithm.heapSortByPrice(instructorList, true);
            customAdapter.notifyDataSetChanged();
            Log.d("SortAction", "Sorted by price (High to Low) using HeapSort");
        }
    }

    public void sortByDistance() {
        if (instructorList != null && !instructorList.isEmpty()) {
            // Step 1: Get user location from HomeActivity
            HomeActivity activity = (HomeActivity) getActivity();
            if (activity == null) return;

            String userLocation = activity.getIntent().getStringExtra("address");
            if (userLocation == null) {
                Log.d("SortByDistance", "User location is null.");
                return;
            }

            // Step 2: Initialize graph and run Dijkstra
            undirectedweightedgraphdatastructureanddijkstraalgorithm graph = new undirectedweightedgraphdatastructureanddijkstraalgorithm();
            Map<String, Integer> distances = graph.dijkstra(userLocation);

            // Step 3: Sort instructors based on distance
            Collections.sort(instructorList, new Comparator<Instructor>() {
                @Override
                public int compare(Instructor i1, Instructor i2) {
                    Integer dist1 = distances.get(i1.getAddress());
                    if (dist1 == null) dist1 = Integer.MAX_VALUE;

                    Integer dist2 = distances.get(i2.getAddress());
                    if (dist2 == null) dist2 = Integer.MAX_VALUE;
                    return Integer.compare(dist1, dist2);
                }
            });

            // Step 4: Update UI
            customAdapter.notifyDataSetChanged();
            Log.d("SortAction", "Sorted by distance using Dijkstra");
        }
    }

}