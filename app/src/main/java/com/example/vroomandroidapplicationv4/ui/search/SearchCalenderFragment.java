package com.example.vroomandroidapplicationv4.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.vroomandroidapplicationv4.R;
import com.example.vroomandroidapplicationv4.databinding.FragmentSearchCalenderBinding;
import com.example.vroomandroidapplicationv4.ui.search.relatedtocalenderview.DisabledDatesDecorator;
import com.example.vroomandroidapplicationv4.ui.search.relatedtocalenderview.TimeSelectionBottomSheet;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.util.Log;

public class SearchCalenderFragment extends Fragment {

    private FragmentSearchCalenderBinding binding;
    private CalendarDay selectedDate; // Store selected date

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SearchCalenderViewModel SearchCalenderViewModel =
                new ViewModelProvider(this).get(SearchCalenderViewModel.class);

        binding = FragmentSearchCalenderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 🔹 Retrieve instructor data from the Bundle
        Bundle bundle = getArguments();
        Set<CalendarDay> disabledDates = new HashSet<>();
        if (bundle != null) {
            String instructorName = bundle.getString("instructor_name", "N/A");
            int instructorImage = bundle.getInt("instructor_image", R.drawable.profile_zachary_lee); // Default image
            String instructorRating = bundle.getString("instructor_rating", "5.0");

            // Update UI elements with instructor data
            TextView nameTextView = root.findViewById(R.id.tvInstructorName);
            ImageView profileImageView = root.findViewById(R.id.ivInstructorProfile);
            TextView ratingTextView = root.findViewById(R.id.tvInstructorRating);

            nameTextView.setText(instructorName);
            profileImageView.setImageResource(instructorImage);
            ratingTextView.setText("⭐ " + instructorRating);

            // Retrieve unavailable dates from bundle and add dynamically
            ArrayList<CalendarDay> unavailableDates = bundle.getParcelableArrayList("dates_unavailable");
            if (unavailableDates != null) {
                Log.d("DEBUG", "Received unavailable dates from Bundle: " + unavailableDates);

                for (CalendarDay date : unavailableDates) {
                    // 🔹 Dynamically add each date in `CalendarDay.from(year, month, day)` format
                    disabledDates.add(CalendarDay.from(date.getYear(), date.getMonth(), date.getDay()));
                }
            }
        }


//        final TextView textView = binding.textSearchCalender;
//        SearchCalenderViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Find the back button by ID
        Button backButton = root.findViewById(R.id.back_button2);
        backButton.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        // Initialize CalendarView
        MaterialCalendarView calendarView = root.findViewById(R.id.calendarView);

        // 🔹 Define disabled dates
//        Set<CalendarDay> disabledDates = new HashSet<>();
//        disabledDates.add(CalendarDay.from(2025, 2, 10)); // March 10, 2025
//        disabledDates.add(CalendarDay.from(2025, 2, 15)); // March 15, 2025
//        disabledDates.add(CalendarDay.from(2025, 2, 20)); // March 20, 2025

        // 🔹 Apply decorator
        calendarView.addDecorator(new DisabledDatesDecorator(disabledDates));

        // Listen for date selection
        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            selectedDate = date; // Store selected date

            // Convert to string format (Aug 17, 2025)
            String formattedDate = date.getDate().toString();

            // Show Bottom Sheet
            showTimeSelectionBottomSheet(formattedDate);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Function to show bottom sheet dialog
    private void showTimeSelectionBottomSheet(String date) {
        Bundle dataBundle = getArguments();
        if (dataBundle == null) {
            dataBundle = new Bundle();
        }
        dataBundle.putString("selectedDate", date); // Add the selected date too

        // Log all keys and values inside the bundle
        for (String key : dataBundle.keySet()) {
            Object value = dataBundle.get(key);
            Log.d("BUNDLE_DEBUG", "Key: " + key + " | Value: " + String.valueOf(value));
        }

        TimeSelectionBottomSheet bottomSheet = new TimeSelectionBottomSheet(date);
        bottomSheet.setArguments(dataBundle);
        bottomSheet.show(getParentFragmentManager(), bottomSheet.getTag());
    }
}
