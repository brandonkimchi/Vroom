package com.example.vroomandroidapplicationv4.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.vroomandroidapplicationv4.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

public class ReviewFragment extends Fragment {

    private EditText reviewInput;
    private RatingBar ratingBar;
    private Button submitButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        reviewInput = view.findViewById(R.id.review_input);
        ratingBar = view.findViewById(R.id.ratingBar);
        submitButton = view.findViewById(R.id.submitB);

        // 🟡 Get instructor name and student name from Bundle
        String instructorName = getArguments() != null ? getArguments().getString("instructor_name") : null;
        String studentName = getArguments() != null ? getArguments().getString("student_name") : null;

        // 🔘 On Submit
        submitButton.setOnClickListener(v -> {
            String reviewText = reviewInput.getText().toString();
            float rating = ratingBar.getRating();

            if (!reviewText.isEmpty() && instructorName != null && studentName != null) {
                DatabaseReference instructorsRef = FirebaseDatabase.getInstance().getReference("Instructors");

                instructorsRef.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        boolean found = false;
                        for (DataSnapshot snapshot : task.getResult().getChildren()) {
                            String nameInDb = snapshot.child("Name").getValue(String.class);
                            if (instructorName.equals(nameInDb)) {
                                String matchedInstructorId = snapshot.getKey();

                                // ✅ Sanitize student name to avoid invalid Firebase keys
                                String sanitizedStudentName = studentName.replaceAll("[.#$\\[\\]]", "_");

                                DatabaseReference reviewRef = instructorsRef
                                        .child(matchedInstructorId)
                                        .child("Reviews")
                                        .child(sanitizedStudentName);

                                Map<String, Object> reviewData = new HashMap<>();
                                reviewData.put("Rating", rating);
                                reviewData.put("Review", reviewText);

                                reviewRef.setValue(reviewData).addOnCompleteListener(reviewTask -> {
                                    if (reviewTask.isSuccessful()) {
                                        Toast.makeText(getContext(), "Review submitted!", Toast.LENGTH_SHORT).show();
                                        requireActivity().getSupportFragmentManager().popBackStack();
                                    } else {
                                        Toast.makeText(getContext(), "Failed to submit review.", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            Toast.makeText(getContext(), "Instructor not found in database", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Failed to retrieve instructors", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(getContext(), "Missing review, instructor name, or student name.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
