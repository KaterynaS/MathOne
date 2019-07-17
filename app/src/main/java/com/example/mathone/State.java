package com.example.mathone;

import android.app.Application;

public class State extends Application {

    private int currentScore;
    private String userName;
    private int currentLevel;
    private Question currentQuestion;

    public State() {
        this.currentScore = 0;
        this.currentLevel = 1;
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

    public Question getCurrentQuestion() {
        return currentQuestion;
    }
}

