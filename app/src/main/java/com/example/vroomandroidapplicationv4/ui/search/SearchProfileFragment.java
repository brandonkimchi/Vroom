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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vroomandroidapplicationv4.HomeActivity;
import com.example.vroomandroidapplicationv4.R;
import com.example.vroomandroidapplicationv4.databinding.FragmentSearchProfileBinding;
import com.example.vroomandroidapplicationv4.ui.search.relatedtorecyclerview.CustomAdapter2;
import com.example.vroomandroidapplicationv4.ui.search.relatedtorecyclerview.Review;

import java.util.ArrayList;
import java.util.List;

public class SearchProfileFragment extends Fragment {

    private FragmentSearchProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SearchProfileViewModel SearchProfileViewModel =
                new ViewModelProvider(this).get(SearchProfileViewModel.class);

        binding = FragmentSearchProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        List<Review> reviewList = new ArrayList<>();
        ArrayList<String> reviewerNames = new ArrayList<String>();

        // Retrieve instructor data from Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            String instructorName = bundle.getString("instructor_name", "N/A");
            String instructorDescription = bundle.getString("instructor_description", "No description available.");
            int instructorImage = bundle.getInt("instructor_image", R.drawable.profile_zachary_lee); // Default image fallback
            String instructorRating = bundle.getString("instructor_rating", "N/A");

            // Update UI elements with instructor data
            TextView nameTextView = root.findViewById(R.id.tvInstructorName);
            TextView descriptionTextView = root.findViewById(R.id.tvAboutMe);
            TextView ratingTextView = root.findViewById(R.id.tvInstructorRating);
            ImageView profileImageView = root.findViewById(R.id.ivInstructorProfile);

            nameTextView.setText(instructorName);
            descriptionTextView.setText(instructorDescription);
            ratingTextView.setText("⭐ " + instructorRating); // Display star symbol before rating
            profileImageView.setImageResource(instructorImage);

            // 🔵 Handle the review data
            reviewerNames = bundle.getStringArrayList("reviewer_names");
            ArrayList<Double> ratings = (ArrayList<Double>) bundle.getSerializable("ratings");
            ArrayList<String> reviewTexts = bundle.getStringArrayList("review_texts");

            if (reviewerNames != null && ratings != null && reviewTexts != null) {
                for (int i = 0; i < reviewerNames.size(); i++) {
                    double rating = ratings.get(i);
                    String reviewText = reviewTexts.get(i);
                    String reviewerName = reviewerNames.get(i);
                    reviewList.add(new Review(rating, reviewText)); // optional: include name inside review
                }
            }
        }


        final TextView textView = binding.textSearchProfile;
        SearchProfileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Find the button and set its click listener
        Button bookSlotButton = root.findViewById(R.id.btnbookslot);
        bookSlotButton.setOnClickListener(v -> changeToSearchCalenderFragment(bundle));

        // Find the back button by ID
        Button backButton = root.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pop the current fragment from the back stack
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });

        // Dummy review data (You can replace this with Firebase data later)
//        List<Review> reviewList = new ArrayList<>();
//        reviewList.add(new Review("Jet Wei", "Very clear & patient instructor. Highly recommend!", 5.0));
//        reviewList.add(new Review("Carlo Cenina", "Great lessons, helped me pass quickly.", 4.5));
//        reviewList.add(new Review("Vernice Kah", "Amazing experience! The best!", 5.0));

        // // RecyclerView setup and Set adapter
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        CustomAdapter2 adapter = new CustomAdapter2(reviewerNames, reviewList);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Method to change to another Fragment (this can be in the same Activity), while passing the instructor data
    public void changeToSearchCalenderFragment(Bundle instructorData) {
        // Get the Activity context and make sure it's an instance of your hosting activity
        if (getActivity() instanceof HomeActivity) {
            // Start a Fragment transaction to replace the current Fragment
            SearchCalenderFragment fragment = new SearchCalenderFragment();
            fragment.setArguments(instructorData); // Pass the // Pass the instructor data

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment_activity_home, fragment);  // Use the ID of the container
            transaction.addToBackStack(null);  // Optional: adds fragment to the back stack
            transaction.commit();
        }
    }

}