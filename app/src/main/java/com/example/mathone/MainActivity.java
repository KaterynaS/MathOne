package com.example.mathone;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    private EditText userInputEditText;
    private Button imReadyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imReadyButton = findViewById(R.id.im_ready_button);

        imReadyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserName();
                State appState  = ((State)getApplicationContext());
                Log.d("OnClick", "input: " + appState.getUserName());
                letsBeginDialog();
            }
        });
        
    }

    private void getUserName() {
        userInputEditText = findViewById(R.id.enterNameEdittext);
        String userInput = String.valueOf(userInputEditText.getText());
        State appState  = ((State)getApplicationContext());
        appState.setUserName(userInput);
    }

    private void openGame() {
        Intent openLvlTwo = new Intent(this, GameActivity.class);
        startActivity(openLvlTwo);
    }

    private void letsBeginDialog() {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
        State appState  = ((State)getApplicationContext());
        String message = "Ok " + appState.getUserName() + ",\nLet's begin!";
        a_builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Go",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openGame();
                    }
                });
        AlertDialog alert = a_builder.create();
        alert.show();
    }
}
