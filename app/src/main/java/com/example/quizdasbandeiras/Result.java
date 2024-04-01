package com.example.quizdasbandeiras;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class Result extends AppCompatActivity {

    MaterialCardView home;
    TextView correctt, wrongt, resultinfo, resultscore;
    ImageView resultImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        home = findViewById(R.id.returnHome);
        correctt = findViewById(R.id.correctScore);
        wrongt = findViewById(R.id.wrongScore);
        resultinfo = findViewById(R.id.resultInfo);
        resultscore = findViewById(R.id.resultScore);
        resultImage = findViewById(R.id.resultImage);

        int correct = getIntent().getIntExtra("correct", 0);
        int wrong = getIntent().getIntExtra("wrong", 0);
        int score = correct * 10;

        correctt.setText("" + correct);
        wrongt.setText("" + wrong);
        resultscore.setText("" + score);

        if (correct >= 0 && correct <= 2) {
            resultinfo.setText("Você não atingiu a pontuação mínima, refaça o teste!");
            resultImage.setImageResource(R.drawable.ic_sad);
        } else if (correct >= 3 && correct <= 5) {
            resultinfo.setText("Você deve tantar mais um pouco, não desista!");
            resultImage.setImageResource(R.drawable.ic_sad);
        } else if (correct >= 6 && correct <= 8) {
            resultinfo.setText("Mandou bem!");
            resultImage.setImageResource(R.drawable.ic_smile);
        } else {
            resultinfo.setText("Amassou no teste, parabéns!");
            resultImage.setImageResource(R.drawable.ic_smile);
        }
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Result.this,MainActivity.class));
                finish();
            }
        });

    }
    @Override
    public void onBackPressed(){

        startActivity(new Intent(Result.this,MainActivity.class));
        finish();
    }
}