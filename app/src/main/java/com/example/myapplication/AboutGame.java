package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_game);
        Button aboutPage = findViewById(R.id.returnHomeFromAboutPage);
        aboutPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToHomePage();
            }
        });
    }

    public void returnToHomePage() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}