package com.example.myapplication;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class gameOver extends AppCompatActivity {
    int lastScore;
    int best1;
    TextView newView;
    private static final String TAG = "MP5: Main";
    private static RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        //keep track of the final score
        Intent intent = getIntent();
        int score = intent.getIntExtra(MainActivity.final_Score, 0);
        TextView finalScore = findViewById(R.id.finalScore);
        TextView bonus = findViewById(R.id.extra);
        bonus.setVisibility(View.GONE);
        Button bonusButton = findViewById(R.id.iAmBored);
        bonusButton.setOnClickListener(v -> {
            bonus.setVisibility(View.VISIBLE);
            bonus.setText("Memorize the fifty states and their capitals");
        });
        finalScore.setText("" + score);

        if (newView != null) {
            newView.setVisibility(View.GONE);
        }
        SharedPreferences preferences = getSharedPreferences("PREFS", 0);

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
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "http://www.boredapi.com/api/activity/",
                    null,
                    this::apiCallDone, error -> Log.e(TAG, error.toString()));
            jsonObjectRequest.setShouldCache(false);
            if (requestQueue != null) {
                requestQueue.add(jsonObjectRequest);
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
            Log.d(TAG, "response recvd");
            Log.d(TAG, response.toString());
            // Example of how to pull a field off the returned JSON object
            JSONObject responseJSON = new JSONObject(response.toString());
            JSONObject activityJSON = responseJSON.getJSONObject("activity");
            String activityString = activityJSON.toString();
            newView.setText(activityString);
            Log.d(TAG, "test");
            Log.d(TAG, responseJSON.toString());
        } catch (JSONException ignored) { }
    }
}