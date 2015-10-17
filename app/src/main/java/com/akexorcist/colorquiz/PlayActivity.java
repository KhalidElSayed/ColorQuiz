package com.akexorcist.colorquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String PREF_SCORE = "score_pref";
    public static final String KEY_HIGH_SCORE = "high_score";

    Button btnTopColor;
    Button btnBottomColor;
    TextView tvScore;

    int colorTopButton;
    int colorBottomButton;

    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        tvScore = (TextView) findViewById(R.id.tvScore);

        btnTopColor = (Button) findViewById(R.id.btnTopColor);
        btnBottomColor = (Button) findViewById(R.id.btnBottomColor);

        btnTopColor.setOnClickListener(this);
        btnBottomColor.setOnClickListener(this);

        randomColor();
    }

    private void randomColor() {
        colorTopButton = getRandomColor();
        colorBottomButton = getRandomColor();

        btnTopColor.setBackgroundColor(colorTopButton);
        btnBottomColor.setBackgroundColor(colorBottomButton);
    }

    private int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnBottomColor) {
            clickColorButton(R.id.btnBottomColor);
        } else if (id == R.id.btnTopColor) {
            clickColorButton(R.id.btnTopColor);
        }
    }

    private void clickColorButton(int buttonId) {
        boolean correct = checkLightenColor(buttonId);
        if (correct) {
            score++;
            tvScore.setText(score + "");
            randomColor();
        } else {
            finish();
            checkHighScore();
        }
    }

    private boolean checkLightenColor(int buttonId) {
        float averageTopColor = getAverageColor(colorTopButton);
        float averageBottomColor = getAverageColor(colorBottomButton);
        Log.e("Check", "Top : " + averageTopColor);
        Log.e("Check", "Bottom : " + averageBottomColor);
        if (averageTopColor > averageBottomColor) {
            return buttonId == R.id.btnTopColor;
        } else if (averageTopColor < averageBottomColor) {
            return buttonId == R.id.btnBottomColor;
        }
        return true;
    }

    private float getAverageColor(int color) {
        int red = (color >> 4) & 0xFF;
        int green = (color >> 2) & 0xFF;
        int blue = color & 0xFF;
//        return (int) Math.sqrt(red * red * .241 + green * green * .691 + blue * blue * .068);
        return (red + green + blue) / 3;
    }

    private void checkHighScore() {
        int oldHighScore = getHighScore();
        if(score > oldHighScore) {
            saveHighScore(score);
        }
    }

    private int getHighScore() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_SCORE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_HIGH_SCORE, -1);
    }

    private void saveHighScore(int newScore) {
        SharedPreferences.Editor editor = getSharedPreferences(PREF_SCORE, Context.MODE_PRIVATE).edit();
        editor.putInt(KEY_HIGH_SCORE, newScore);
        editor.apply();
    }
}
