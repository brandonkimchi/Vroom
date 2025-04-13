package com.example.vroomandroidapplicationv4.ui.quizzes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// The questions, choices and answers for BTT
// Change and add questions done here
// New classes with updated questions can also be made as long as changes are made in QuestionFragment
public class BTTQA implements QuestionAnswer{

    private final String[] questions = {
            "When approaching a roundabout you should",
            "There is a slow moving vehicle ahead of you, and you are approaching a zebra crossing,",
            "When you see a U-turn sign at the road junction",
            "Name the force that pushes your vehicle outwards when going around a bend",
            "What is the advantage of intermittent braking?",
            "When should you use your horn?",
            "You are turning right at a junction. A pedestrian is crossing the road. What should you do?",
            "What does a flashing green man at a pedestrian crossing mean?",
            "On a two-lane carriageway, where should you normally drive?",
            "What should you do when you see an amber traffic light?",
            "When driving through a bend, you should",
            "When driving in fog, you should use",
            "What is the speed limit on expressways in Singapore?",
            "What should you do if your vehicle breaks down on an expressway?",
            "What does a red traffic light mean?"
    };

    private final String[][] choices = {
            {"Speed up", "Slow down", "Stop"},
            {"You may overtake before the crossing", "You may overtake only after the crossing", "You should never overtake"},
            {"You may U-turn", "You must U-turn", "You cannot U-turn"},
            {"Pushing", "Gravity", "Centrifugal"},
            {"Driver need not exert much force", "Tyres won't wear out fast", "Wheels will not be locked up"},
            {"When you are angry", "To alert others of danger", "In heavy traffic"},
            {"Proceed as you have right of way", "Sound horn to alert", "Wait until pedestrian has crossed"},
            {"You may start to cross", "Do not start to cross", "Cross quickly"},
            {"Left lane", "Right lane", "Any lane"},
            {"Prepare to stop", "Speed up", "Ignore it"},
            {"Accelerate through quickly", "Slow down before entering", "Brake during the bend"},
            {"High beam lights", "Fog lights or low beam", "Hazard lights"},
            {"60 km/h", "70 km/h", "90 km/h"},
            {"Call for towing service and wait in vehicle", "Push vehicle to the side and wait behind guardrail", "Try to fix car on the road"},
            {"Go", "Prepare to stop", "Stop and wait"}
    };

    private final String[] correctAnswers = {
            "Slow down",
            "You may overtake only after the crossing",
            "You may U-turn",
            "Centrifugal",
            "Wheels will not be locked up",
            "To alert others of danger",
            "Wait until pedestrian has crossed",
            "Do not start to cross",
            "Left lane",
            "Prepare to stop",
            "Slow down before entering",
            "Fog lights or low beam",
            "90 km/h",
            "Push vehicle to the side and wait behind guardrail",
            "Stop and wait"
    };

    private static final int QUESTIONS_PER_SESSION = 5;
    private final List<String> selectedQuestions = new ArrayList<>();
    private final List<String[]> selectedChoices = new ArrayList<>();
    private final List<String> selectedAnswers = new ArrayList<>();

    public BTTQA() {
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



