package com.example.mathone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    private TextView questionTextView;
    private TextView scoreTextView;
    private TextView levelTextView;

    private Button trueButton;
    private Button falseButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        questionTextView = findViewById(R.id.question_textview);
        scoreTextView = findViewById(R.id.score_text_view);
        levelTextView = findViewById(R.id.level_text_view);

        Question currentQuestion = new Question();
        State appState  = ((State)getApplicationContext());

        currentQuestion = currentQuestion.generateQuestion(appState.getCurrentLevel());
        updateQuestionTextView(currentQuestion);


        //change answer ui depending on questionType
        LinearLayout layout = (LinearLayout)findViewById(R.id.ui_container);
        View child;

        switch (currentQuestion.getQuestionType())
        {
            case TRUEFALSE:
                child = getLayoutInflater().inflate(R.layout.true_false_ui, null);
                //get user input
                trueButton = findViewById(R.id.true_button);
                falseButton = findViewById(R.id.false_button);

//                Log.d("OnCreate", "trueButton id = " + trueButton.toString());
//
//                trueButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(GameActivity.this, "correct", Toast.LENGTH_SHORT).show();
//                    }
//                });

//
//                final boolean correctAns = currentQuestion.getIsCorrect();
//
//                trueButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(correctAns == true)
//                        {
//                            Toast.makeText(GameActivity.this, "correct", Toast.LENGTH_SHORT).show();
//                        }
//                        else
//                        {
//                            Toast.makeText(GameActivity.this, "wrong", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//                falseButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(correctAns == true)
//                        {
//                            Toast.makeText(GameActivity.this, "wrong", Toast.LENGTH_SHORT).show();
//                        }
//                        else
//                        {
//                            Toast.makeText(GameActivity.this, "correct", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//




                break;
            case CHECKBOX:
                child = getLayoutInflater().inflate(R.layout.radiobutton_ui, null);
                break;
            default:
                child = getLayoutInflater().inflate(R.layout.radiobutton_ui, null);
        }
        layout.addView(child);


    }

    private void updateQuestionTextView(Question currentQuestion) {
        questionTextView.setText(currentQuestion.getQuestionText());
    }
}
