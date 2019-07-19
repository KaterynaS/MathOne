package com.example.mathone;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView questionTextView;
    private TextView scoreTextView;
    private TextView levelTextView;

    //truefalse
    private Button trueButton;
    private Button falseButton;

    //radio
    private Button submitButton;
    private RadioButton radioButtonOne;
    private RadioButton radioButtonTwo;
    private RadioButton radioButtonThree;
    private RadioButton radioButtonFour;

    //typein
    private TextView typeInInputTextView;
    private Button deleteButton;
    private Button submitTypeInButton;
    private Button numZeroButton;
    private Button numOneButton;
    private Button numTwoButton;
    private Button numThreeButton;
    private Button numFourButton;
    private Button numFiveButton;
    private Button numSixButton;
    private Button numSevenButton;
    private Button numEightButton;
    private Button numNineButton;
    String typeInDigitsString = "";
    int typeInInt = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        questionTextView = findViewById(R.id.question_textview);
        scoreTextView = findViewById(R.id.score_text_view);
        levelTextView = findViewById(R.id.level_text_view);


        Question currentQuestion = updateQuestion();
        updateQuestionTextView(currentQuestion);

    }

    private Question updateQuestion() {
        State appState = ((State)getApplicationContext());
        Question nextQuestion = new Question();
        nextQuestion = nextQuestion.generateQuestion(appState.getCurrentLevel());
        appState.setCurrentQuestion(nextQuestion);
        questionTextView.setText(nextQuestion.getQuestionText());


        //change answer ui depending on questionType
        LinearLayout layout = (LinearLayout)findViewById(R.id.ui_container);
        View child;

        switch (appState.getCurrentQuestion().getQuestionType())
        {
            case TRUEFALSE:
            {
                layout.removeAllViews();
                child = getLayoutInflater().inflate(R.layout.true_false_ui, null);
                layout.addView(child);

                //get user input
                trueButton = findViewById(R.id.true_button);
                falseButton = findViewById(R.id.false_button);

                //check users answer
                trueButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        State appState  = ((State)getApplicationContext());

                        //Log.d("OnCreate", "true pressed, answer is " + correctAns);
                        if(appState.getCurrentQuestion().isCorrect() == true)
                        {
                            userAnswerIsCorrect();
                        }
                        else
                        {
                            userAnswerIsWrong();
                        }
                        updateQuestion();
                    }
                });

                falseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        State appState  = ((State)getApplicationContext());

                        //Log.d("OnCreate", "false pressed, answer is " + correctAns);
                        if(appState.getCurrentQuestion().isCorrect() == false)
                        {
                            userAnswerIsCorrect();
                        }
                        else
                        {
                            userAnswerIsWrong();
                        }
                        updateQuestion();
                    }

                });
                break;
            }

            case CHECKBOX:
            {
                layout.removeAllViews();
                child = getLayoutInflater().inflate(R.layout.radiobutton_ui, null);
                layout.addView(child);

                submitButton = findViewById(R.id.answer_button);
                radioButtonOne = findViewById(R.id.choice_1_radio_button);
                radioButtonTwo = findViewById(R.id.choice_2_radio_button);
                radioButtonThree = findViewById(R.id.choice_3_radio_button);
                radioButtonFour = findViewById(R.id.choice_4_radio_button);

                String ansChOne = String.valueOf(appState.getCurrentQuestion().getAnswerChoices()[0]);
                radioButtonOne.setText(ansChOne);

                String ansChTwo = String.valueOf(appState.getCurrentQuestion().getAnswerChoices()[1]);
                radioButtonTwo.setText(ansChTwo);

                String ansChThree = String.valueOf(appState.getCurrentQuestion().getAnswerChoices()[2]);
                radioButtonThree.setText(ansChThree);

                String ansChFour = String.valueOf(appState.getCurrentQuestion().getAnswerChoices()[3]);
                radioButtonFour.setText(ansChFour);


                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isCorrectCheckboxChoosen();
                    }
                });


                break;
            }

            //todo TYPEIN
            case TYPEIN:
            {
                layout.removeAllViews();
                child = getLayoutInflater().inflate(R.layout.typein_ui, null);
                layout.addView(child);

                int userInput = getUserTypeIn();

                break;
            }

            default:
                child = getLayoutInflater().inflate(R.layout.radiobutton_ui, null);
                layout.addView(child);
        }

        return appState.getCurrentQuestion();
    }

    private int getUserTypeIn() {

        //todo place typeInInputTextView in a questionTextView after equal sign
        typeInInputTextView = findViewById(R.id.input_textview);
        deleteButton = findViewById(R.id.delete_button);
        submitTypeInButton = findViewById(R.id.submit_button);

        numZeroButton = findViewById(R.id.num_zero_button);
        numOneButton = findViewById(R.id.num_one_button);
        numTwoButton = findViewById(R.id.num_two_button);
        numThreeButton = findViewById(R.id.num_three_button);
        numFourButton = findViewById(R.id.num_four_button);
        numFiveButton = findViewById(R.id.num_five_button);
        numSixButton = findViewById(R.id.num_six_button);
        numSevenButton = findViewById(R.id.num_seven_button);
        numEightButton = findViewById(R.id.num_eight_button);
        numNineButton = findViewById(R.id.num_nine_button);


        deleteButton.setOnClickListener(this);
        submitTypeInButton.setOnClickListener(this);
        numZeroButton.setOnClickListener(this);
        numOneButton.setOnClickListener(this);
        numTwoButton.setOnClickListener(this);
        numThreeButton.setOnClickListener(this);
        numFourButton.setOnClickListener(this);
        numFiveButton.setOnClickListener(this);
        numSixButton.setOnClickListener(this);
        numSevenButton.setOnClickListener(this);
        numEightButton.setOnClickListener(this);
        numNineButton.setOnClickListener(this);

        return 0;
    }

    private void isCorrectCheckboxChoosen() {

        State appState = ((State)getApplicationContext());
        int corrAns = appState.getCurrentQuestion().getCorrectAnswer();

        RadioGroup rg = (RadioGroup) findViewById(R.id.radio_group);

        int selectedId = rg.getCheckedRadioButtonId();

        if (selectedId == -1)
        {
            Toast.makeText(GameActivity.this, "Choose something", Toast.LENGTH_SHORT).show();
        }
        else {
            RadioButton radioButton = (RadioButton)rg.findViewById(selectedId);
            String selectedRadioButtonValue = radioButton.getText().toString();

            checkAnswer(Integer.valueOf(selectedRadioButtonValue));
            rg.clearCheck();
            updateQuestion();
        }
    }

    private void checkAnswer(int userInput) {

        State appState = ((State)getApplicationContext());
        if(appState.getCurrentQuestion().getCorrectAnswer() == userInput)
        {
            userAnswerIsCorrect();
        }
        else
        {
            userAnswerIsWrong();
        }
        updateScore();
    }

    private void userAnswerIsWrong() {
        Toast.makeText(GameActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
        updateScore();
    }

    private void userAnswerIsCorrect() {
        State appState  = ((State)getApplicationContext());
        Toast.makeText(GameActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
        appState.addToCurrentScore(1);
        updateScore();
    }

    private void updateScore() {

        State appState  = ((State)getApplicationContext());
        int scoreToShow = appState.getCurrentScore();
        scoreTextView.setText("Score: " + scoreToShow);

        if(appState.getCurrentScore() >=55)
        {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(GameActivity.this);

            String message = appState.getUserName() + "You are super smart! Now have some rest ;-)";
            a_builder.setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("Rest",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            AlertDialog alert = a_builder.create();
            alert.show();
        }

        Log.d("GameActivity", "level: " + appState.getCurrentLevel());
        //next level?
        if(appState.getCurrentScore() >= appState.getCurrentLevel()*5)
        {
            nextLevel();

            Log.d("GameActivity", "level: " + appState.getCurrentLevel());
            //"next level" dialog
            AlertDialog.Builder a_builder = new AlertDialog.Builder(GameActivity.this);

            //random greeting message + name
            String[] greetingMessageTemplate =
                    {
                            "{0}, you did it!",
                            "You are smart!",
                            "Keep going, {0}!",
                            "Congratulations, {0}",
                            "WOW, it's impressive!",
                            "{0}, want more?",
                            "Go, Go, Go!",
                            "{0}, u r clever to the Moon and back",
                            "Smartest. Kid. Ever!",
                            "Well done, {0}",
                            "High five, {0}"
                    };

            Random random = new Random();
            int greetingMessageId = random.nextInt(greetingMessageTemplate.length);

            String template = greetingMessageTemplate[greetingMessageId];
            String greetingMessageText = MessageFormat.format(template, appState.getUserName());

            a_builder.setMessage(greetingMessageText)
                    .setCancelable(false)
                    .setPositiveButton("Next Level",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            updateQuestion();
                        }
                    });
            AlertDialog alert = a_builder.create();
            alert.show();

        }
    }

    private void nextLevel() {

        State appState  = ((State)getApplicationContext());
        appState.increaseLevel();
        int lvl = appState.getCurrentLevel();
        String level = "Level: " + lvl;
        levelTextView.setText(level);
    }

    private void updateQuestionTextView(Question currentQuestion) {
        questionTextView.setText(currentQuestion.getQuestionText());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.num_zero_button:
                addDigitToInputTextView(0);
                break;
            case R.id.num_one_button:
            addDigitToInputTextView(1);
                break;
            case R.id.num_two_button:
                addDigitToInputTextView(2);
                break;
            case R.id.num_three_button:
                addDigitToInputTextView(3);
                break;
            case R.id.num_four_button:
                addDigitToInputTextView(4);
                break;
            case R.id.num_five_button:
                addDigitToInputTextView(5);
                break;
            case R.id.num_six_button:
                addDigitToInputTextView(6);
                break;
            case R.id.num_seven_button:
                addDigitToInputTextView(7);
                break;
            case R.id.num_eight_button:
                addDigitToInputTextView(8);
                break;
            case R.id.num_nine_button:
                addDigitToInputTextView(9);
                break;
            case R.id.delete_button:
                deleteLastDigitFromInputTextView();
                break;
            case R.id.submit_button:
                submitInput();
                break;
            default:
                break;
        }
    }

    private void submitInput() {
        if(typeInDigitsString.length() < 1)
        {
            //todo why is it updating question?
            Toast.makeText(GameActivity.this,
                    "Please, enter your answer",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            typeInInt = Integer.valueOf(typeInDigitsString);
            checkAnswer(typeInInt);
            typeInDigitsString = "";
            typeInInt = 0;
            updateQuestion();
        }

    }

    private void deleteLastDigitFromInputTextView() {
        if(typeInDigitsString.length()>=1)
        {
            typeInDigitsString = typeInDigitsString.substring(0, typeInDigitsString.length() - 1);
            typeInInputTextView.setText(typeInDigitsString);
        }
    }

    private void addDigitToInputTextView(int i) {
        typeInDigitsString = typeInDigitsString + i;
        typeInInputTextView.setText(typeInDigitsString);
    }
}
