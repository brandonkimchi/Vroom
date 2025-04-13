package com.example.vroomandroidapplicationv4.ui.quizzes;
// interface for questions, choices and answers, does not need to be touched to change questions
public interface QuestionAnswer {

    String[] getQuestions();
    String[][] getChoices();
    String[] getCorrectAnswers();

    /*public static String question[] ={
            "When approaching a roundabout you should",
            "There is a slow moving vehicle ahead of you, and you are approaching a zebra crossing,",
            "When you see a U-turn sign at the road junction",
            "Name the force that pushes your vehicle outwards when going around a bend",
            "What is the advantage of intermittent braking?"

    };
    public static String choices[][] ={
            {"Speed up", "Slow down", "Stop"},
            {"You may overtake before the crossing", "You may overtake only after the crossing", "You should never overtake"},
            {"You may U-turn", "You must U-turn", "You cannot U-turn"},
            {"Pushing", "Gravity", "Centrifugal"},
            {"Driver need not exert much force", "Tyres won't wear out fast", "Wheels will not be locked up"}
    };
    public static String correctAnswers [] ={
            "Slow down",
            "You may overtake only after the crossing",
            "You may U-turn",
            "Centrifugal",
            "Wheels will not be locked up"
    };*/
}

