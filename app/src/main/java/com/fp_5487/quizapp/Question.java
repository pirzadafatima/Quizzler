package com.fp_5487.quizapp;

public class Question {
    private int questionTextResId;
    private int optionsResId;
    private int correctOptionResId;

    // Constructor accepts resource IDs
    public Question(int questionTextResId, int optionsResId, int correctOptionResId) {
        this.questionTextResId = questionTextResId;
        this.optionsResId = optionsResId;
        this.correctOptionResId = correctOptionResId;
    }

    public int getQuestionTextResId() {
        return questionTextResId;
    }

    public int getOptionsResId() {
        return optionsResId;
    }

    public int getCorrectOptionResId() {
        return correctOptionResId;
    }
}
