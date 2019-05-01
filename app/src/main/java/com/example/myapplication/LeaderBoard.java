package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LeaderBoard extends AppCompatActivity {
    int previousScore;
    int best1, best2, best3, best4, best5;
    Button homePage;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);

        //load old scores
        previousScore = preferences.getInt("lastScore", 0);
        best1 = preferences.getInt("best1", 0);
        best2 = preferences.getInt("best2", 0);
        best3 = preferences.getInt("best3", 0);
        best4 = preferences.getInt("best4", 0);
        best5 = preferences.getInt("best5", 0);

        //shift the rankings should there be a new high score
        if (previousScore > best5) {
            best5 = previousScore;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best5", best5);
            editor.apply();
        }
        if (previousScore > best4) {
            int tmp = best4;
            best4 = previousScore;
            best5 = tmp;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best5", best5);
            editor.putInt("best4", best4);
            editor.apply();
        }
        if (previousScore > best3) {
            int tmp = best3;
            best3 = previousScore;
            best4 = tmp;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best2", best4);
            editor.putInt("best1", best3);
            editor.apply();
        }
        if (previousScore > best2) {
            int tmp = best2;
            best2 = previousScore;
            best3 = tmp;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best2", best3);
            editor.putInt("best1", best2);
            editor.apply();
        }
        if (previousScore > best1) {
            int tmp = best1;
            best1 = previousScore;
            best2 = tmp;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best2", best2);
            editor.putInt("best1", best1);
            editor.apply();
        }
        //display the current and the 5 highest scores
        TextView allTheScores = findViewById(R.id.highScoreCompilation);
        allTheScores.setText("Your Score: " + previousScore + "\n" + "\n"
                + "1st place: " + best1 + "\n"
                + "2nd place: " + best2 + "\n"
                + "3rd place: " + best3 + "\n"
                + "4th place: " + best4 + "\n"
                + "5th place: " + best5);

        //go back to home page
        homePage = findViewById(R.id.returnHomeFromLeaderboard);
        if (homePage != null) {
            homePage.setOnClickListener(v -> returnToHomePage());
        }
    }
    public void returnToHomePage() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
