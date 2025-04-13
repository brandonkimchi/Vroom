package com.example.vroomandroidapplicationv4.ui.quizzes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// The questions, choices and answers for FTT
// Change and add questions done here
// New classes with updated questions can also be made as long as changes are made in QuestionFragment
public class FTTQA implements QuestionAnswer{

    private final String[] questions = {
            "Are motorists allowed to pick up or let down passengers on an expressway?",
            "When a cyclist turns his head to look behind, most likely",
            "Before joining an expressway, you should",
            "When the rear of your vehicle skids sideways, you should:",
            "What is an odometer used for?",
            "You are approaching a red traffic light. What should you do?",
            "When parking uphill on a two-way street with no curb, your front wheels should be:",
            "When may you use the right lane on an expressway?",
            "What should you do when your car starts to aquaplane?",
            "When passing a stationary bus at a bus stop, you should:",
            "What should you do when you hear the siren of an emergency vehicle?",
            "What is the minimum distance you should keep from the vehicle in front?",
            "When must you use dipped headlights?",
            "What is the use of a tachometer?",
            "What should you do when another vehicle is tailgating you?"
    };

    private final String[][] choices = {
            {"Yes", "No", "Yes, if no traffic police is nearby"},
            {"he is checking for a punctured tyre", "he intends to change direction", "he intends to stop"},
            {"signal your intention and accelerate", "signal your intention and slow down", "signal your intention and proceed at the same speed"},
            {"release the steering wheel and permit the car to correct itself.", "step hard on the brake", "steer in the direction of the skid"},
            {"Shows number of engine revolutions per minute", "Shows total traveled distance", "Shows speed of vehicle"},
            {"Speed up and clear the junction", "Stop and wait", "Honk and proceed"},
            {"Turned to the left", "Turned to the right", "Kept straight"},
            {"To overtake", "When the left lanes are blocked", "Anytime you want to drive faster"},
            {"Brake hard", "Ease off the accelerator", "Turn the steering wheel rapidly"},
            {"Speed up quickly", "Proceed cautiously", "Stop behind it and wait"},
            {"Speed up to avoid blocking", "Pull over and stop safely", "Ignore and continue driving"},
            {"At least one car length", "At least 2 seconds time gap", "As close as possible"},
            {"At all times", "Only during the day", "At night or during low visibility"},
            {"To check speed", "To measure engine temperature", "To monitor engine RPM"},
            {"Brake suddenly", "Move left and let them pass", "Drive slower in the middle"}
    };

    private final String[] correctAnswers = {
            "No",
            "he intends to change direction",
            "signal your intention and accelerate",
            "steer in the direction of the skid",
            "Shows total traveled distance",
            "Stop and wait",
            "Turned to the right",
            "When the left lanes are blocked",
            "Ease off the accelerator",
            "Proceed cautiously",
            "Pull over and stop safely",
            "At least 2 seconds time gap",
            "At night or during low visibility",
            "To monitor engine RPM",
            "Move left and let them pass"
    };

    private static final int QUESTIONS_PER_SESSION = 5;
    private final List<String> selectedQuestions = new ArrayList<>();
    private final List<String[]> selectedChoices = new ArrayList<>();
    private final List<String> selectedAnswers = new ArrayList<>();

    public FTTQA() {
        // Generate random indices
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < questions.length; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);

        for (int i = 0; i < QUESTIONS_PER_SESSION; i++) {
            int idx = indices.get(i);
            selectedQuestions.add(questions[idx]);
            selectedChoices.add(choices[idx]);
            selectedAnswers.add(correctAnswers[idx]);
        }
    }

    @Override
    public String[] getQuestions() {
        return selectedQuestions.toArray(new String[0]);
    }

    @Override
    public String[][] getChoices() {
        return selectedChoices.toArray(new String[0][0]);
    }

    @Override
    public String[] getCorrectAnswers() {
        return selectedAnswers.toArray(new String[0]);
    }



}



