package com.xdev.math_quiz.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xdev.math_quiz.R;
import com.xdev.math_quiz.home.Home_page;

public class Score_Activity extends AppCompatActivity {
    TextView score;
    Button done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        score = findViewById(R.id.sa_score);
        done = findViewById(R.id.sa_done);

        String score_str =getIntent().getStringExtra("SCORE");
        score.setText(score_str);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Score_Activity.this, Home_page.class);
                Score_Activity.this.startActivity(intent);
                Score_Activity.this.finish();
            }
        });
    }
}