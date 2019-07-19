package com.example.mathone;

import android.util.Log;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Question {

    String questionText;
    int correctAnswer;
    QuestionType questionType;

    //for true false
    boolean isCorrect;

    //for checkbox
    int[] answerChoices;
    int correctAnswerIndex;



    //constructors
    public Question() {
    }

    public Question(String questionText, int correctAns) {
        this.questionText = questionText;
        this.correctAnswer = correctAns;
    }

    public Question(String questionText, int correctAns, QuestionType questionType) {
        this.questionText = questionText;
        this.correctAnswer = correctAns;
        this.questionType = questionType;
    }

    //for true false
    public Question(String questionText, int correctAns, QuestionType questionType,
                    boolean isExpressionCorrect) {
        this.questionText = questionText;
        this.correctAnswer = correctAns;
        this.questionType = questionType;
    }

    //for radiobutton
    public Question(String questionText, int correctAns, QuestionType questionType,
                    int[] answerChoices, int correctAnswerIndex) {
        this.questionText = questionText;
        this.correctAnswer = correctAns;
        this.questionType = questionType;
    }


    //setters
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public void setCorrectAnswer(int correctAns) {
        this.correctAnswer = correctAns;
    }

    //for true/false only
    public void setIsCorrect(boolean correct) {
        isCorrect = correct;
        Log.d("Question", "setIsCorrect: " + isCorrect);
    }

    //for CHECKBOX only
    public void setAnswerChoices(int[] answerChoices) {
        this.answerChoices = answerChoices;
    }

    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }

    //getters
    public String getQuestionText() {
        return questionText;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public int[] getAnswerChoices() {
        return answerChoices;
    }





    //generators
    public Question generateQuestion(int currentLvl)
    {
        //todo generate something depending on current lvl

        Question newQuestion;

        switch (currentLvl) {
            case 1:
                newQuestion = generatePlusOrMinusQuestion();
                newQuestion.setQuestionType(QuestionType.TRUEFALSE);
                //add answer to questionText

                int answerToShow = generateAnswerToShow(newQuestion.getCorrectAnswer());
                String newQuestionText = newQuestion.getQuestionText();
                newQuestionText = newQuestionText + answerToShow;
                newQuestion.setQuestionText(newQuestionText);
                Log.d("generateQuestion", "answerToShow = " + answerToShow +
                        "  correct answer = " + newQuestion.getCorrectAnswer());
                if(answerToShow != newQuestion.getCorrectAnswer()) {newQuestion.setIsCorrect(false);}
                else {newQuestion.setIsCorrect(true);}

                break;

            case 2:
                //minus or plus
                newQuestion = generatePlusOrMinusQuestion();
                newQuestion.setQuestionType(QuestionType.CHECKBOX);
                int corrAnsIndex = generateNumberInRange(0,3);
                int[] answerChoices = generateAnswerChoices(newQuestion.getCorrectAnswer(), corrAnsIndex);
                newQuestion.setAnswerChoices(answerChoices);
                newQuestion.setCorrectAnswerIndex(corrAnsIndex);
                break;

            case 3:
                newQuestion = generateMultiplicationQuestion(currentLvl);
                newQuestion.setQuestionType(QuestionType.TRUEFALSE);
                //add answer to questionText

                answerToShow = generateAnswerToShow(newQuestion.getCorrectAnswer());
                newQuestionText = newQuestion.getQuestionText();
                newQuestionText = newQuestionText + answerToShow;
                newQuestion.setQuestionText(newQuestionText);
                Log.d("generateQuestion", "answerToShow = " + answerToShow +
                        "  correct answer = " + newQuestion.getCorrectAnswer());
                if(answerToShow != newQuestion.getCorrectAnswer()) {newQuestion.setIsCorrect(false);}
                else {newQuestion.setIsCorrect(true);}

                break;

            case 4:
                //minus or plus
                newQuestion = generateMultiplicationQuestion(currentLvl);
                newQuestion.setQuestionType(QuestionType.CHECKBOX);
                corrAnsIndex = generateNumberInRange(0,3);
                answerChoices = generateAnswerChoices(newQuestion.getCorrectAnswer(), corrAnsIndex);
                newQuestion.setAnswerChoices(answerChoices);
                newQuestion.setCorrectAnswerIndex(corrAnsIndex);
                break;

            case 5:
                //minus or plus
                Random random = new Random();
                int a = random.nextInt(2);
                if(a == 0){newQuestion = generatePlusOrMinusQuestion();}
                else {newQuestion = generateMultiplicationQuestion(currentLvl);}
                newQuestion.setQuestionType(QuestionType.TYPEIN);
                corrAnsIndex = generateNumberInRange(0,3);
                answerChoices = generateAnswerChoices(newQuestion.getCorrectAnswer(), corrAnsIndex);
                newQuestion.setAnswerChoices(answerChoices);
                newQuestion.setCorrectAnswerIndex(corrAnsIndex);
                break;

            default:
                //minus or plus
                random = new Random();
                a = random.nextInt(2);
                if(a == 0){newQuestion = generatePlusOrMinusQuestion();}
                else {newQuestion = generateMultiplicationQuestion(currentLvl);}
                newQuestion.setQuestionType(QuestionType.TYPEIN);
                corrAnsIndex = generateNumberInRange(0,3);
                answerChoices = generateAnswerChoices(newQuestion.getCorrectAnswer(), corrAnsIndex);
                newQuestion.setAnswerChoices(answerChoices);
                newQuestion.setCorrectAnswerIndex(corrAnsIndex);
                break;
        }

        return newQuestion;
    }




    // for all
    private Question generateMinusQuestion()
    {
        int rangeMin = 0;
        int rangeMax = 9;

        int a = generateNumberInRange(rangeMin, rangeMax);
        int b = generateNumberInRange(rangeMin, rangeMax);

        int firstNumber;
        int secondNumber;

        //largerFirst
        if(b>a)
        {
            firstNumber = b;
            secondNumber = a;
        }
        else
        {
            firstNumber = a;
            secondNumber = b;
        }

        int realAnswer = firstNumber - secondNumber;

        String template = "{0} - {1} = ";
        String questionText = MessageFormat.format(template, firstNumber, secondNumber);

        return new Question(questionText, realAnswer);
    }

    private Question generateMultiplicationQuestion(int level)
    {
        int rangeMin = 0;
        //max is no more than 9
        int rangeMax;

        if(level <= 9) { rangeMax = level; }
        else rangeMax = 9;

        int firstNumber = generateNumberInRange(rangeMin, rangeMax);
        int secondNumber = generateNumberInRange(rangeMin, rangeMax);

        int realAnswer = firstNumber * secondNumber;

        String template = "{0} * {1} = ";
        String questionText = MessageFormat.format(template, firstNumber, secondNumber);

        return new Question(questionText, realAnswer);
    }

    private Question generatePlusQuestion() {
        int rangeMin = 0;
        int rangeMax = 9;

        int firstNumber = generateNumberInRange(rangeMin, rangeMax);
        int secondNumber = generateNumberInRange(rangeMin, rangeMax);

        int realAnswer = firstNumber + secondNumber;

        String template = "{0} + {1} = ";
        String questionText = MessageFormat.format(template, firstNumber, secondNumber);

        return new Question(questionText, realAnswer);
    }

    private Question generatePlusOrMinusQuestion()
    {
        Question newQuestion;
        Operand op = pickOperand();

        if(op == Operand.PLUS) { newQuestion = generatePlusQuestion(); }
        else {newQuestion = generateMinusQuestion();}

        return newQuestion;
    }

    private Operand pickOperand() {

        Operand op;
        Random random = new Random();
        int a = random.nextInt(2);
        if(a == 0){op = Operand.PLUS;}
        else {op = Operand.MINUS;}

        return op;
    }

    public int generateNumberInRange(int min, int max) {

        Random random = new Random();
        int a = random.nextInt((max - min) + 1) + min;

        return a;
    }




    //for true false
    private int generateAnswerToShow(int answer) {
        //generate answerToShow, which is true 50%

        int showTrueOrFalse = generateNumberInRange(0,1);
        int answerToShow;
        if(showTrueOrFalse == 1)
        {
            answerToShow = generateNumberInRange(0, 9);
        }
        else
        {
            answerToShow = answer;
        }

        return answerToShow;
    }



    //for CHECKBOX
    private int[] generateAnswerChoices(int correctAns, int correctAnswerID)
    {

        int[] answerChoices = new int[4];

        Integer[] arr = new Integer[9];
        for (int k = 0; k < arr.length; k++) {
            if(k==correctAns) {arr[k] = 9;}
            else { arr[k] = k;}
        }
        Collections.shuffle(Arrays.asList(arr));
        Arrays.toString(arr);

        for (int i = 0; i < answerChoices.length; i++) {
            if (i == correctAnswerID) {answerChoices[i] = correctAns;}
            else { answerChoices[i] = arr[i]; }
        }

        return answerChoices;
    }


    public boolean checkAnswer(int userInput)
    {
        boolean isTrue = false;

//        switch (qType)
//            case: TRUEFALSE,
//            CHECKBOX,
//            TYPEIN;

        if(userInput == correctAnswer)
        {
            isTrue = true;
        }

        return isTrue;
    }


}
