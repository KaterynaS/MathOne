package com.example.mathone;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.warkiz.widget.IndicatorSeekBar;

import java.text.MessageFormat;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private AppState appState;

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

    //seekbar
    private SeekBar seekBar;
    private IndicatorSeekBar indicatorSeekBar;
    private Button seekBarSubmit;

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

    int gameSpeed = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        questionTextView = findViewById(R.id.question_textview);
        scoreTextView = findViewById(R.id.score_text_view);
        levelTextView = findViewById(R.id.level_text_view);

        //if there is no question
        appState = AppState.getInstance();
        appState.setCurrentQuestion(updateQuestion());
        updateQuestionTextView();
    }

    private Question updateQuestion() {

        Question nextQuestion = new Question();
        nextQuestion = nextQuestion.generateQuestion(appState.getCurrentLevel());
        appState.setCurrentQuestion(nextQuestion);

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
                        //Log.d("OnCreate", "true pressed, answer is " + correctAns);
                        if(appState.getCurrentQuestion().isCorrect())
                        {
                            onCorrectAnswer().setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    updateQuestion();
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }
                        else
                        {
                            onWrongAnswer().setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    updateQuestion();
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }

                    }
                });

                falseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Log.d("OnCreate", "false pressed, answer is " + correctAns);
                        if(!appState.getCurrentQuestion().isCorrect())
                        {
                            onCorrectAnswer().setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    updateQuestion();
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }
                        else
                        {
                            onWrongAnswer().setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    updateQuestion();
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }
                    }

                });
                break;
            }

            case CHECKBOX:
            {
                layout.removeAllViews();
                child = getLayoutInflater().inflate(R.layout.checkbox_ui, null);
                layout.addView(child);
                checkCheckBoxInput();
                break;
            }

            case TYPEIN:
            {
                layout.removeAllViews();
                child = getLayoutInflater().inflate(R.layout.typein_ui, null);
                layout.addView(child);
                getUserTypeIn();
                break;
            }

            case SEEKBAR:
            {
                layout.removeAllViews();
                child = getLayoutInflater().inflate(R.layout.seekbar_ui, null);
                layout.addView(child);
                checkSeekBarInput();
                break;
            }

            default:
                child = getLayoutInflater().inflate(R.layout.checkbox_ui, null);
                layout.addView(child);
        }

        updateQuestionTextView();
        return appState.getCurrentQuestion();
    }

    private void checkCheckBoxInput() {

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
                isCorrectCheckboxChosen();
            }
        });
    }

    private void checkSeekBarInput() {

        //seekbar
        seekBarSubmit = findViewById(R.id.seekbar_submit_button);

        indicatorSeekBar = findViewById(R.id.indicator_seekbar);
        seekBarSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int a = indicatorSeekBar.getProgress();
                checkAnswer(a);
                updateQuestion();
            }
        });
    }

    private void getUserTypeIn() {

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
    }

    private void isCorrectCheckboxChosen() {

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

        if(appState.getCurrentQuestion().getCorrectAnswer() == userInput)
        {
            onCorrectAnswer().setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    updateQuestion();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        else
        {
            onWrongAnswer().setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    updateQuestion();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        updateScore();
    }

    private void updateScore() {

        int scoreToShow = appState.getCurrentScore();
        scoreTextView.setText("Score: " + scoreToShow);

        //next level?
        if(appState.getCurrentScore() >= appState.getCurrentLevel()*gameSpeed)
        {
            nextLevel();
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

        appState.increaseLevel();
        int lvl = appState.getCurrentLevel();
        String level = "Level: " + lvl;
        levelTextView.setText(level);
    }

    private void updateQuestionTextView() {
        questionTextView.setText(appState.getCurrentQuestion().getQuestionText());
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

    private Animation onWrongAnswer() {
        Toast.makeText(GameActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
        //shakeAnimation();
        updateScore();
        return shakeAnimation();
    }

    private Animation onCorrectAnswer() {
        Toast.makeText(GameActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
        appState.addToCurrentScore(1);
        updateScore();
        return fadeViewAnimation();
    }

    private Animation fadeViewAnimation() {
        final CardView cardView = findViewById(R.id.card_view);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);

        alphaAnimation.setDuration(500);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.RESTART);

        cardView.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        return alphaAnimation;

    }

    private Animation shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(GameActivity.this,
                R.anim.shake_animation);
        final CardView cardView = findViewById(R.id.card_view);
        cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        return shake;
    }

}
