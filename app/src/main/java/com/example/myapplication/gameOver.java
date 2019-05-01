package com.example.myapplication;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class gameOver extends AppCompatActivity {
    int lastScore;
    int best1, best2, best3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        //keep track of the final score
        Intent intent = getIntent();
        int score = intent.getIntExtra(MainActivity.final_Score, 0);
        TextView finalScore = findViewById(R.id.finalScore);
        finalScore.setText("" + score);

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);

        //load old scores
        lastScore = preferences.getInt("lastScore", 0);
        best1 = preferences.getInt("best1", 0);
        best2 = preferences.getInt("best2", 0);
        best3 = preferences.getInt("best3", 0);

        //shift the rankings should there be a new high score
        if (lastScore > best3) {
            best3 = lastScore;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best3", best3);
            editor.apply();
        }
        if (lastScore > best2) {
            int tmp = best2;
            best2 = lastScore;
            best3 = tmp;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best3", best3);
            editor.putInt("best2", best2);
            editor.apply();
        }
        if (lastScore > best1) {
            int tmp = best1;
            best1 = lastScore;
            best2 = tmp;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best2", best2);
            editor.putInt("best1", best1);
            editor.apply();
        }
        //display the highest score
        TextView highestScore = findViewById(R.id.finalScoreNumber);
        highestScore.setText(best1 + "\n");

        //return to the game
        Button tryAgain = findViewById(R.id.tryAgain);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryAgain();
            }
        });

        //go back to home page
        Button homePage = findViewById(R.id.homePage);
        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToHomePage();
            }
        });
    }
    public void tryAgain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void returnToHomePage() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}