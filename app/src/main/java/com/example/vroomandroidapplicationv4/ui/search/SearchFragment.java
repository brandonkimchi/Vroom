package com.example.vroomandroidapplicationv4.ui.search;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.example.vroomandroidapplicationv4.HomeActivity;
import com.example.vroomandroidapplicationv4.databinding.FragmentSearchBinding;
import com.example.vroomandroidapplicationv4.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private Set<String> selectedVehicleClasses = new HashSet<>();
    private String selectedDrivingCenter = "";  // Updated when a RecyclerView option is selected

    private ImageView ivClass2B, ivClass2A, ivClass2, ivClass3A, ivClass3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel SearchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSearch;
        SearchViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        ////////////////////////////////////////////////////////////////////////////////////////////////

        // Spinner 'View' (multiple choices dropdown) logic
        Spinner spinner = (Spinner) root.findViewById(R.id.spinnerCentre);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.drivingcentre_array,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        spinner.setAdapter(adapter);

        // Set an OnItemSelectedListener to handle item selection
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item from the spinner
                String selectedCenter = parent.getItemAtPosition(position).toString();

                // Update the selectedDrivingCenter variable
                setSelectedDrivingCenter(selectedCenter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle case where nothing is selected (optional)
                Log.d("SpinnerSelection", "Nothing selected");
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////

        // Initialize ImageViews
        ivClass2B = root.findViewById(R.id.ivClass2B);
        ivClass2A = root.findViewById(R.id.ivClass2A);
        ivClass2 = root.findViewById(R.id.ivClass2);
        ivClass3A = root.findViewById(R.id.ivClass3A);
        ivClass3 = root.findViewById(R.id.ivClass3);

        // Set Click Listeners
        setupVehicleClassSelection(ivClass2B, "2B");
        setupVehicleClassSelection(ivClass2A, "2A");
        setupVehicleClassSelection(ivClass2, "2");
        setupVehicleClassSelection(ivClass3A, "3A");
        setupVehicleClassSelection(ivClass3, "3");

        // Find the button and set its click listener
        Button button = root.findViewById(R.id.searchbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToSearchInstructorFragment(v);  // Call the fragment change method
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Method to change to another Fragment (this can be in the same Activity)
    public void changeToSearchInstructorFragment(View v) {
        // Create a new instance of the fragment
        SearchInstructorFragment fragment = new SearchInstructorFragment();

        // Create a Bundle to hold the data
        Bundle bundle = new Bundle();
        bundle.putString("selectedDrivingCenter", selectedDrivingCenter);
        bundle.putStringArrayList("selectedVehicleClasses", new ArrayList<>(selectedVehicleClasses));

        // Set the Bundle as arguments for the fragment
        fragment.setArguments(bundle);

        // Start a Fragment transaction to replace the current Fragment
        if (getActivity() instanceof HomeActivity) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment_activity_home, fragment);  // Use the ID of the container
            transaction.addToBackStack(null);  // Optional: adds fragment to the back stack
            transaction.commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Clear the selected vehicle classes set
        selectedVehicleClasses.clear();

        // Reset the color filter (highlight) for all ImageViews
        ivClass2B.setColorFilter(null);
        ivClass2A.setColorFilter(null);
        ivClass2.setColorFilter(null);
        ivClass3A.setColorFilter(null);
        ivClass3.setColorFilter(null);
    }

    private void setupVehicleClassSelection(ImageView imageView, String vehicleClass) {
        imageView.setOnClickListener(v -> {
            if (selectedVehicleClasses.contains(vehicleClass)) {
                selectedVehicleClasses.remove(vehicleClass);
                imageView.setColorFilter(null);  // Remove highlight
            } else {
                selectedVehicleClasses.add(vehicleClass);
                imageView.setColorFilter(Color.argb(150, 0, 51, 181)); // Highlight selection
            }
        });
    }

    public void setSelectedDrivingCenter(String center) {
        this.selectedDrivingCenter = center;
    }

}



