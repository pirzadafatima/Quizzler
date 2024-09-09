package com.fp_5487.quizapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        TextView scoreTextView = findViewById(R.id.scoreTextView);
        TextView percentageTextView = findViewById(R.id.percentageTextView);
        TextView correctPercentageTextView = findViewById(R.id.correctPercentageTextView);
        Button finish = findViewById(R.id.finishButton);

        // Get data from intent
        int score = getIntent().getIntExtra("score", 0);
        int totalQuestions = getIntent().getIntExtra("totalQuestions", 0);
        int totalCorrect = getIntent().getIntExtra("totalCorrect", 0);

        // Calculate percentages
        double percentage = ((double) score / (totalQuestions * 5)) * 100;
        double correctPercentage = ((double) totalCorrect / totalQuestions) * 100;

        // Set the result values in the text views
        scoreTextView.setText("Score: " + score);
        percentageTextView.setText("Percentage: " + String.format("%.2f", percentage) + "%");
        correctPercentageTextView.setText("Correct Answer Percentage: " + String.format("%.2f", correctPercentage) + "%");

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ResultsActivity.this, "Congratulations! You have finished the quiz.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}