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
import android.widget.ImageButton;

import com.example.vroomandroidapplicationv4.R;


public class QuizFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout manually (not using ViewBinding here)
        View root = inflater.inflate(R.layout.fragment_quiz, container, false);

        // Find the buttons
        ImageButton goToBTT = root.findViewById(R.id.startBTTButton);
        ImageButton goToFTT = root.findViewById(R.id.startFTTButton);

        // Set click listener to navigate
        goToBTT.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.btt_start);
        });

        goToFTT.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.ftt_start);
        });

        return root;

    }
}

