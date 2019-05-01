package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
//import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    //Questions
    Questions mQuestions;
    Button trueButton;
    Button falseButton;
    TextView questionPlaceholder;
    int questionsLength;
    ArrayList<Item> questionsList;

    private TextView scorer;

    int currentQuestion = 0;
    int keepScore = 0;
    boolean winner = false;

    public static final String final_Score = "com.example.myapplication.final_Score";


    //variables for the countdown timer
    private static final long START_TIME_IN_MILLIS = 5000;
    private TextView mTextViewCountDown;

    private CountDownTimer mCountDownTimer;
    boolean mTimerRunning = true;

    private long mTimeLeftInMIllis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionPlaceholder = findViewById(R.id.question);
        trueButton = findViewById(R.id.True);
        falseButton = findViewById(R.id.False);

        mQuestions = new Questions();
        questionsLength = mQuestions.mQuestions.length;

        questionsList = new ArrayList<>();

        //save all questions in the list
        for (int i = 0; i < questionsLength; i++) {
            questionsList.add(new Item(mQuestions.getQuestion(i), mQuestions.getCorrectAnswer(i)));
        }

        scorer = findViewById(R.id.updateScore);
        questionPlaceholder = findViewById(R.id.question);

        //shuffle the questions list
        Collections.shuffle(questionsList);

        //start the game
        setQuestion(currentQuestion);


        trueButton.setOnClickListener(v -> {
            if (checkQuestion(currentQuestion)) {
                resetTimer();
                startTimer();
                if (currentQuestion < questionsLength) {
                    currentQuestion++;
                    updateScore(keepScore);
                    setQuestion(currentQuestion);
                } else {
                    winner = true;
                    gameOver();
                }
            } else {
                gameOver();
            }
        });

        falseButton.setOnClickListener(v -> {
            if (!checkQuestion(currentQuestion) || !mTimerRunning) {
                currentQuestion++;
                updateScore(keepScore);
                resetTimer();
                startTimer();
                if (currentQuestion < questionsLength) {
                    setQuestion(currentQuestion);
                } else {
                    winner = true;
                    gameOver();
                }
            } else {
                gameOver();
            }
        });

        mTextViewCountDown = findViewById(R.id.timer);
        updateCountDownText();

    }
    //show the question on the screen
    private void setQuestion(int number) {
        questionPlaceholder.setText(questionsList.get(number).getQuestion());
    }

    //check if the answer is right
    private boolean checkQuestion(int number) {
        String answer = questionsList.get(number).getAnswer();
        return answer.equals("True");
    }
    public void gameOver() {
        if (winner) {
            Toast.makeText(this, "You Win!", Toast.LENGTH_SHORT).show();
            int endScore = Integer.parseInt(scorer.getText().toString());
            Intent intent = new Intent(this, gameOver.class);
            intent.putExtra(final_Score, endScore);
            startActivity(intent);
        } else {
            Toast.makeText(this, "You Lose!", Toast.LENGTH_SHORT).show();
            int endScore = Integer.parseInt(scorer.getText().toString());
            Intent intent = new Intent(this, gameOver.class);
            intent.putExtra(final_Score, endScore);
            startActivity(intent);
        }
    }
    @SuppressLint("SetTextI18n")
    private void updateScore(int currentScore) {
        keepScore++;
        scorer.setText("" + keepScore);
    }

    private void resetTimer() {
        mTimeLeftInMIllis = START_TIME_IN_MILLIS;
        updateCountDownText();

    }
    //aa
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMIllis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMIllis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMIllis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMIllis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
            }
        }.start();
        mTimerRunning = true;
    }
}
