package com.example.mathone;

import android.app.Application;

public class AppState extends Application {


    private static AppState instance = null;

    private int currentScore;
    private String userName;
    private int currentLevel;
    private Question currentQuestion = null;

    private AppState() {
        this.currentScore = 0;
        this.currentLevel = 1;
        currentQuestion = new Question();
    }


    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return(instance);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public String getUserName() {
        return userName;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void addToCurrentScore(int scoreToAdd)
    {
        currentScore = currentScore + scoreToAdd;
    }

    public void increaseLevel()
    {
        currentLevel = currentLevel + 1;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }
}

