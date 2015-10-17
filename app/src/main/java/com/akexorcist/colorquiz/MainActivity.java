package com.akexorcist.colorquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String PREF_SCORE = "score_pref";
    public static final String KEY_HIGH_SCORE = "high_score";

    Button btnStart;
    LinearLayout layoutHighScore;
    TextView tvHighScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvHighScore = (TextView) findViewById(R.id.tvHighScore);
        layoutHighScore = (LinearLayout) findViewById(R.id.layoutHighScore);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkHighScore();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }

    private void checkHighScore() {
        int highScore = getHighScore();
        if(highScore > 0) {
            layoutHighScore.setVisibility(View.VISIBLE);
            tvHighScore.setText(highScore + "");
        } else {
            layoutHighScore.setVisibility(View.GONE);
        }
    }

    private int getHighScore() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_SCORE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_HIGH_SCORE, -1);
    }
}
