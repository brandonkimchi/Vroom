package com.example.vroomandroidapplicationv4.ui.quizzes;

import static androidx.databinding.DataBindingUtil.findBinding;
import static androidx.databinding.DataBindingUtil.setContentView;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import android.graphics.Color;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.Button;

import com.example.vroomandroidapplicationv4.R;

import android.app.AlertDialog;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class QuestionFragment extends Fragment implements View.OnClickListener {

    TextView questionTextView;
    TextView totalQuestionTextView;
    Button option1, option2, option3;
    Button btn_submit;

    int score = 0;
    QuestionAnswer quiz;
    int totalQuestion;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the quiz type passed from previous fragment
        String quizType = getArguments() != null ? getArguments().getString("quizType") : "BTT";

        // Load the corresponding quiz
        if ("BTT".equalsIgnoreCase(quizType)) {
            quiz = new BTTQA();
        } else if ("FTT".equalsIgnoreCase(quizType)) {
            quiz = new FTTQA();
        } else {
            // fallback to BTT
            quiz = new BTTQA();
        }

        totalQuestion = quiz.getQuestions().length;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_question, container, false);

        totalQuestionTextView = root.findViewById(R.id.total_question);
        questionTextView = root.findViewById(R.id.question);
        option1 = root.findViewById(R.id.option1);
        option2 = root.findViewById(R.id.option2);
        option3 = root.findViewById(R.id.option3);
        btn_submit = root.findViewById(R.id.submitB);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        loadNewQuestion();

        return root;
    }

    private void loadNewQuestion() {
        if (currentQuestionIndex == totalQuestion) {
            finishQuiz();
            return;
        }

        totalQuestionTextView.setText("Question " + (currentQuestionIndex + 1) + " of " + totalQuestion);

        String questionText = "Q" + (currentQuestionIndex + 1) + ". " + quiz.getQuestions()[currentQuestionIndex];
        questionTextView.setText(questionText);
        option1.setText(quiz.getChoices()[currentQuestionIndex][0]);
        option2.setText(quiz.getChoices()[currentQuestionIndex][1]);
        option3.setText(quiz.getChoices()[currentQuestionIndex][2]);

        selectedAnswer = "";
        option1.setBackgroundColor(Color.WHITE);
        option1.setTextColor(Color.BLACK);
        option2.setBackgroundColor(Color.WHITE);
        option2.setTextColor(Color.BLACK);
        option3.setBackgroundColor(Color.WHITE);
        option3.setTextColor(Color.BLACK);
    }

    private void finishQuiz() {
        String passStatus = score >= totalQuestion * 0.6 ? "Good Job!" : "Do Better Next Time!";
        new AlertDialog.Builder(requireContext())
                .setTitle(passStatus)
                .setMessage("Your score is " + score + " out of " + totalQuestion)
                .setPositiveButton("Restart", ((dialog, i) -> restartQuiz()))
                .setNegativeButton("Back", (dialog, i) ->
                        NavHostFragment.findNavController(this).navigate(R.id.navigation_bar_quiz))
                .setCancelable(false)
                .show();
    }

    private void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;

        // ❗️ Re-randomize quiz here
        String quizType = getArguments() != null ? getArguments().getString("quizType") : "BTT";
        if ("BTT".equalsIgnoreCase(quizType)) {
            quiz = new BTTQA();
        } else if ("FTT".equalsIgnoreCase(quizType)) {
            quiz = new FTTQA();
        } else {
            quiz = new BTTQA(); // fallback
        }

        totalQuestion = quiz.getQuestions().length;
        totalQuestionTextView.setText("Question " + (currentQuestionIndex + 1) + " of " + totalQuestion);

        loadNewQuestion();
    }

    @Override
    public void onClick(View view) {
        // Reset all options to white background + black text
        option1.setBackgroundColor(Color.WHITE);
        option1.setTextColor(Color.BLACK);
        option2.setBackgroundColor(Color.WHITE);
        option2.setTextColor(Color.BLACK);
        option3.setBackgroundColor(Color.WHITE);
        option3.setTextColor(Color.BLACK);

        Button clickedButton = (Button) view;

        if (clickedButton.getId() == R.id.submitB) {
            if (!selectedAnswer.isEmpty()) {
                boolean isCorrect = selectedAnswer.equals(quiz.getCorrectAnswers()[currentQuestionIndex]);

                if (isCorrect) {
                    score++;
                    Toast.makeText(getContext(), "✅ Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    String correct = quiz.getCorrectAnswers()[currentQuestionIndex];
                    Toast.makeText(getContext(), "❌ Wrong! Correct: " + correct, Toast.LENGTH_SHORT).show();
                }

                currentQuestionIndex++;
                loadNewQuestion();
            }
        } else {
            // Save selected answer + visually highlight the clicked option
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.parseColor("#0D47A1")); // dark blue
            clickedButton.setTextColor(Color.WHITE);
        }
    }
}


/*public class QuestionFragment extends Fragment implements View.OnClickListener {
    TextView questionTextView;
    TextView totalQuestionTextView;
    Button option1, option2, option3;
    Button btn_submit;

    int score = 0;
    public QuestionAnswer quiz;
    int totalQuestion = quiz.getQuestions().length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout manually (not using ViewBinding here)
        View root = inflater.inflate(R.layout.fragment_question, container, false);

        totalQuestionTextView = root.findViewById(R.id.total_question);
        questionTextView = root.findViewById(R.id.question);
        option1 = root.findViewById(R.id.option1);
        option2 = root.findViewById(R.id.option2);
        option3 = root.findViewById(R.id.option3);

        btn_submit = root.findViewById(R.id.submitB);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);

        btn_submit.setOnClickListener(this);

        totalQuestionTextView.setText("Total Question: " + totalQuestion);

        loadNewQuestion();
        return root;

    }

    public void setQuiz(QuestionAnswer quiz){
        this.quiz = quiz;
    }

    private void loadNewQuestion() {
        if (currentQuestionIndex == totalQuestion) {
            finishQuiz();
            return;
        }
        questionTextView.setText(quiz.getQuestions()[currentQuestionIndex]);  // displays the questions from the list in QuestionAnswer
        option1.setText(quiz.getChoices()[currentQuestionIndex][0]);
        option2.setText(quiz.getChoices()[currentQuestionIndex][1]);
        option3.setText(quiz.getChoices()[currentQuestionIndex][2]);

        selectedAnswer = ""; // initialise selected answer
    }

    private void finishQuiz() {
        String passStatus;
        if (score >= totalQuestion * 0.6) {
            passStatus = "Good Job!";
        } else {
            passStatus = "Do Better Next Time!";
        }
        new AlertDialog.Builder(requireContext())
                .setTitle(passStatus)
                .setMessage("Your score is " + score + " out of " + totalQuestion)
                .setPositiveButton("Restart", ((dialog, i) -> restartQuiz()))
                .setNegativeButton("Back", (dialog,i) ->{
                    NavHostFragment.findNavController(this).navigate(R.id.navigation_bar_quiz);
                })
                .setCancelable(false)
                .show();
    }

    private void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }

    public void onClick(View view) {
        option1.setBackgroundColor(Color.BLUE);
        option2.setBackgroundColor(Color.BLUE);
        option3.setBackgroundColor(Color.BLUE);

        Button clickedButton = (Button) view;
        if (clickedButton.getId() == R.id.submitB) {
            if (!selectedAnswer.isEmpty()) {
                // check if selected ans matches our correct answer
                if (selectedAnswer.equals(quiz.getCorrectAnswers()[currentQuestionIndex])) {
                    score++;
                }
                currentQuestionIndex++;
                loadNewQuestion();
            }
        } else {
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.GRAY);
        }
    }

}
*/

