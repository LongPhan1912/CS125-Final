package com.example.myapplication;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class Home extends AppCompatActivity {
    Button start;
    Button about;
    Button howToPlay;
    Button leaderBoard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Start Game button
        start = findViewById(R.id.startGame);
        if (start != null) {
            start.setOnClickListener(v -> startGame());
        }

        //About page button
        about = findViewById(R.id.aboutPage);
        if (about != null) {
            about.setOnClickListener(v -> openAboutPage());
        }
        howToPlay = findViewById(R.id.howToPlay);
        if (howToPlay != null) {
            howToPlay.setOnClickListener(v -> openHowToPlay());
        }
        leaderBoard = findViewById(R.id.leaderboard);
        if (leaderBoard != null) {
            leaderBoard.setOnClickListener(v -> openLeaderBoard());
        }
    }
    public void startGame() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void openAboutPage() {
        Intent intent = new Intent(this, AboutGame.class);
        startActivity(intent);
    }
    public void openHowToPlay() {
        Intent intent = new Intent(this, HowToPlay.class);
        startActivity(intent);
    }
    public void openLeaderBoard() {
        Intent intent = new Intent(this, LeaderBoard.class);
        startActivity(intent);
    }
}
