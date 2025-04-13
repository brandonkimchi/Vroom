package com.example.vroomandroidapplicationv4.ui.quizzes;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.vroomandroidapplicationv4.R;

// helps to navigate from quiz main page to BTT quiz page
public class BTTStart extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_b_t_t_start, container, false);

        // Get buttons from layout
        ImageButton back = root.findViewById(R.id.backButton);
        Button startBTT = root.findViewById(R.id.startBTTQuizButton);

        // Back button
        back.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.navigation_bar_quiz);
        });

        // Start quiz button
        startBTT.setOnClickListener(v -> {
            // Create bundle and put quiz type
            Bundle bundle = new Bundle();
            bundle.putString("quizType", "BTT");

            // Navigate to the QuestionFragment with quizType as argument
            Navigation.findNavController(v).navigate(R.id.questionPage, bundle);
        });

        return root;
    }
}

/*public class BTTStart extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout manually (not using ViewBinding here)
        View root = inflater.inflate(R.layout.fragment_b_t_t_start, container, false);

        // Find the buttons
        ImageButton back = root.findViewById(R.id.backButton);
        Button startBTT = root.findViewById(R.id.startBTTQuizButton);

        // Set click listener to navigate
        startBTT.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.questionPage);
        });

        back.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.navigation_bar_quiz);
        });

        return root;
    }
}*/
