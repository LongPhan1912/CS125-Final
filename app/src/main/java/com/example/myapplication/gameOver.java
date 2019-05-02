package com.example.myapplication;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class gameOver extends AppCompatActivity {
    int lastScore;
    int best1;
    TextView textResult;
    private static final String TAG = "MP5: Main";
    private static RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestQueue = Volley.newRequestQueue(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        //make text appear when click the Bored button
        Intent intent = getIntent();
        int score = intent.getIntExtra(MainActivity.final_Score, 0);
        TextView finalScore = findViewById(R.id.finalScore);
        textResult = findViewById(R.id.extra);
        textResult.setVisibility(View.GONE);
        Button bonusButton = findViewById(R.id.iAmBored);
        bonusButton.setOnClickListener(v -> {
            textResult.setVisibility(View.VISIBLE);
            textResult.setText("Learn to write with your nondominant hand");
            startAPICall();
        });

        finalScore.setText("" + score);

        if (textResult != null) {
            textResult.setVisibility(View.GONE);
        }
        SharedPreferences preferences = getSharedPreferences("PREFS", 0);

        //keep track of the final score
        //load old scores
        lastScore = preferences.getInt("lastScore", 0);
        best1 = preferences.getInt("best1", 0);

        //shift the rankings should there be a new high score
        if (lastScore > best1) {
            best1 = lastScore;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best1", best1);
            editor.apply();
        }
        //display the highest score
        TextView highestScore = findViewById(R.id.finalScoreNumber);
        highestScore.setText(best1 + "\n");

        //return to the game
        Button tryAgain = findViewById(R.id.tryAgain);
        tryAgain.setOnClickListener(v -> tryAgain());

        //go back to home page
        Button homePage = findViewById(R.id.homePage);
        homePage.setOnClickListener(v -> returnToHomePage());
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();



    }
    public void tryAgain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void returnToHomePage() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
    void startAPICall() {
        try {
            Log.d(TAG, "request made");
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "http://www.boredapi.com/api/activity/",
                    null,
                    this::apiCallDone, error -> Log.e(TAG, error.toString()));
            jsonObjectRequest.setShouldCache(false);
            requestQueue.add(jsonObjectRequest);
            if (requestQueue != null) {
                requestQueue.add(jsonObjectRequest);
            } else {
                Log.d(TAG, "no request queue");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle the response from our IP geolocation API.
     *
     * @param response response from our IP geolocation API.
     */
    void apiCallDone(final JSONObject response) {
        try {
            Log.d(TAG, "response received");
            //Log.d(TAG, response.toString());
            // Example of how to pull a field off the returned JSON object
            JSONObject responseJSON = new JSONObject(response.toString());
            String activity = responseJSON.get("activity").toString();
            textResult.setText(activity);
            Log.d(TAG, "test");
            Log.d(TAG, activity);
        } catch (JSONException ignored) { }
    }
}
