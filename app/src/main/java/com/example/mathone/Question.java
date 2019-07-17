package com.example.mathone;

import java.text.MessageFormat;
import java.util.Random;

public class Question {

    String questionText;
    int correctAnswer;
    QuestionType questionType;
    boolean isCorrect;

    public Question() {
    }

    public Question(String questionText, int correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    public Question(String questionText, int correctAnswer, QuestionType questionType) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.questionType = questionType;
    }

    //for true false
    public Question(String questionText, int correctAnswer, QuestionType questionType,
                    boolean isExpressionCorrect) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.questionType = questionType;
    }

    //for radiobutton
    public Question(String questionText, int correctAnswer, QuestionType questionType,
                    int[] answerChoices) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.questionType = questionType;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }



    public void setIsCorrect(boolean correct) {
        isCorrect = correct;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public String getQuestionText() {
        return questionText;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

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
                break;
            case 2:
                newQuestion = generateMinusQuestion();
                newQuestion.setQuestionType(QuestionType.TRUEFALSE);
                break;
            case 3:
                //minus or plus
                newQuestion = generateMinusQuestion();
                newQuestion.setQuestionType(QuestionType.CHECKBOX);
                break;
            default:
                //minus or plus
                newQuestion = generateMinusQuestion();
                newQuestion.setQuestionType(QuestionType.CHECKBOX);
                break;
        }

        return newQuestion;
    }

    private int generateAnswerToShow(int answer) {
        //generate answerToShow, which is true 50%
        int answerToShow = generateNumberInRange(0,1);
        if(answerToShow == 0)
        {
            answerToShow = answer;
            setIsCorrect(true);
        }
        else
        {
            answerToShow = generateNumberInRange(0, 9);
            setIsCorrect(false);
        }

        return answerToShow;
    }

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
        boolean answer = true;

        String template = "{0} - {1} = ";
        String questionText = MessageFormat.format(template, firstNumber, secondNumber);

        return new Question(questionText, realAnswer);
    }

    private Question generatePlusQuestion() {
        int rangeMin = 0;
        int rangeMax = 9;

        int firstNumber = generateNumberInRange(rangeMin, rangeMax);
        int secondNumber = generateNumberInRange(rangeMin, rangeMax);

        int realAnswer = firstNumber + secondNumber;
        boolean answer = true;

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
