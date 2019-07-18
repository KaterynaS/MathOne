package com.example.testworks;

import java.util.Random;

public class MyClass {

    static boolean isCorr;


    public static void main (String[] sa)
    {
        String[] greetingMessageTemplate =
                {
                        "{Name}, you did it!",
                        "You are smart!",
                        "Keep going, {Name}!",
                        "Congratulations, {Name}",
                        "WOW, it's impressive!",
                        "{Name}, want more?",
                        "Go, Go, Go!",
                        "{Name} u r clever to the Moon and back",
                        "Smartest. Kid. Ever!",
                        "Well done, {Name}",
                        "High five, {Name}"
                };

        System.out.println(greetingMessageTemplate.length);

        for (int i = 0; i < 20; i++) {

            Random random = new Random();
            int greetingMessageId = random.nextInt(greetingMessageTemplate.length + 1);
            System.out.print(greetingMessageId + "  ");

        }
    }

    public static void setIsCorrect(boolean a)
    {
        isCorr = a;
    }

    public static int generateAnswerToShow(int answer) {
        //generate answerToShow, which is true 50%

        int showTrueOrFalse = generateNumberInRange(0,1);
        int answerToShow;
        if(showTrueOrFalse == 1)
        {
            answerToShow = generateNumberInRange(0, 9);
            setIsCorrect(false);
        }
        else
        {
            setIsCorrect(true);
            answerToShow = answer;
        }

        return answerToShow;
    }

    public static int generateNumberInRange(int min, int max) {

        Random random = new Random();
        int a = random.nextInt((max - min) + 1) + min;

        return a;
    }


}
