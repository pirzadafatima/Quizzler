package com.fp_5487.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuizQuestionActivity extends AppCompatActivity {

    Question[] question_bank = new Question[]{
            new Question(R.string.question1, R.array.question1_options, R.string.answer1),
            new Question(R.string.question2, R.array.question2_options, R.string.answer2),
            new Question(R.string.question3, R.array.question3_options, R.string.answer3),
            new Question(R.string.question4, R.array.question4_options, R.string.answer4),
            new Question(R.string.question5, R.array.question5_options, R.string.answer5),
            new Question(R.string.question6, R.array.question6_options, R.string.answer6),
            new Question(R.string.question7, R.array.question7_options, R.string.answer7),
            new Question(R.string.question8, R.array.question8_options, R.string.answer8),
            new Question(R.string.question9, R.array.question9_options, R.string.answer9),
            new Question(R.string.question10, R.array.question10_options, R.string.answer10),
            new Question(R.string.question11, R.array.question11_options, R.string.answer11),
            new Question(R.string.question12, R.array.question12_options, R.string.answer12),
            new Question(R.string.question13, R.array.question13_options, R.string.answer13),
            new Question(R.string.question14, R.array.question14_options, R.string.answer14),
            new Question(R.string.question15, R.array.question15_options, R.string.answer15),
            new Question(R.string.question16, R.array.question16_options, R.string.answer16),
            new Question(R.string.question17, R.array.question17_options, R.string.answer17),
            new Question(R.string.question18, R.array.question18_options, R.string.answer18),
            new Question(R.string.question19, R.array.question19_options, R.string.answer19),
            new Question(R.string.question20, R.array.question20_options, R.string.answer20)
    };

    int currentIndex = 0;
    int score = 0;
    int totalCorrect = 0;
    long timeLeftInMillis = 2 * 60 * 1000; // 20 minutes in milliseconds

    TextView timerTextView;
    TextView scoreTextView;

    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_question);

        timerTextView = findViewById(R.id.timerTextView);
        scoreTextView = findViewById(R.id.scoreTextView);

        startTimer();  // Start the countdown timer
        loadQuestion();  // Load the first question

        RadioGroup optionsGroup = findViewById(R.id.options_group);
        Button nextButton = findViewById(R.id.nextButton);
        Button prevButton = findViewById(R.id.prevButton);
        Button viewAnswerButton = findViewById(R.id.viewAnswerButton);
        Button endQuizButton = findViewById(R.id.endQuizButton);

        endQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endQuiz();
            }
        });

        // Next button logic
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedOptionId = optionsGroup.getCheckedRadioButtonId();

                if (selectedOptionId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedOptionId);
                    String selectedOption = selectedRadioButton.getText().toString();

                    String correctAnswer = getResources().getString(question_bank[currentIndex].getCorrectOptionResId());

                    // Check if the answer is correct
                    if (selectedOption.equals(correctAnswer)) {
                        score += 5;
                        totalCorrect++;
                        Toast.makeText(QuizQuestionActivity.this, "Correct! +5 points", Toast.LENGTH_SHORT).show();
                    } else {
                        score -= 1;
                        Toast.makeText(QuizQuestionActivity.this, "Incorrect! -1 point", Toast.LENGTH_SHORT).show();
                    }

                    Log.d("QuizQuestionActivity", "Current Score: " + score);
                } else {
                    Toast.makeText(QuizQuestionActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                }

                if (currentIndex < question_bank.length - 1) {
                    currentIndex++;
                    loadQuestion();
                } else {
                    Toast.makeText(QuizQuestionActivity.this, "No more questions!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentIndex > 0) {
                    currentIndex--;
                    loadQuestion();
                } else {
                    Toast.makeText(QuizQuestionActivity.this, "You are at the first question!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Question currentQuestion = question_bank[currentIndex];
                String correctAnswer = getResources().getString(currentQuestion.getCorrectOptionResId());

                score -= 1;
                Toast.makeText(QuizQuestionActivity.this, "Correct Answer: " + correctAnswer, Toast.LENGTH_LONG).show();
                scoreTextView.setText("Score: " + score);
            }
        });
    }

    private void loadQuestion() {
        Question currentQuestion = question_bank[currentIndex];

        String questionText = getResources().getString(currentQuestion.getQuestionTextResId());
        Log.d("QuizQuestionActivity", questionText);
        TextView questionTextView = findViewById(R.id.question_text);
        questionTextView.setText(questionText);

        String[] options = getResources().getStringArray(currentQuestion.getOptionsResId());
        RadioGroup optionsGroup = findViewById(R.id.options_group);
        optionsGroup.removeAllViews();  // Clear previous options

        for (int i = 0; i < options.length; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(options[i]);
            radioButton.setId(i);
            optionsGroup.addView(radioButton);
        }

        scoreTextView.setText("Score: " + score);
    }

    private void startTimer() {
        Log.d("Check", "starting timer");
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimer();
            }

            public void onFinish() {
                Toast.makeText(QuizQuestionActivity.this, "Time's up!", Toast.LENGTH_SHORT).show();
                endQuiz();
            }
        }.start();
    }

    private void updateTimer() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format("%02d:%02d", minutes, seconds);
        timerTextView.setText("Time: " + timeFormatted);
    }

    private void endQuiz() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        int totalQuestions = question_bank.length;

        // Send data to QuizResultActivity
        Intent intent = new Intent(QuizQuestionActivity.this, ResultsActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("totalQuestions", totalQuestions);
        intent.putExtra("totalCorrect", totalCorrect);
        startActivity(intent);

        finish(); // Optionally close this activity
    }
}
