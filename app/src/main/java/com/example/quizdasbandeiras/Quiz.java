package com.example.quizdasbandeiras;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Quiz extends AppCompatActivity {
    TextView quiztext;
    RadioButton aans, bans, cans, dans;
    RadioGroup radioGroup;
    List<Questions> questionsItems;
    ImageView flagImage;
    int currentQuestion = 0;
    int correct = 0, wrong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        quiztext = findViewById(R.id.quizText);
        radioGroup = findViewById(R.id.radioGroup);
        aans = findViewById(R.id.aanswer);
        bans = findViewById(R.id.banswer);
        cans = findViewById(R.id.canswer);
        dans = findViewById(R.id.danswer);
        flagImage = findViewById(R.id.flagImage);

        loadAllQuestions();
        Collections.shuffle(questionsItems);
        setQuestionScreen(currentQuestion);
        updateFlagImage();

        findViewById(R.id.confirmanswer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton selectedRadioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                if (selectedRadioButton != null) {
                    String answer = selectedRadioButton.getText().toString();
                    if (questionsItems.get(currentQuestion).getCorrect().equals(answer)) {
                        correct++;
                    } else {
                        wrong++;
                    }

                    if (currentQuestion < questionsItems.size() - 1) {
                        currentQuestion++;
                        setQuestionScreen(currentQuestion);
                        updateFlagImage();
                        radioGroup.clearCheck();
                    } else {
                        Intent intent = new Intent(Quiz.this, Result.class);
                        intent.putExtra("correct", correct);
                        intent.putExtra("wrong", wrong);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                if (selectedRadioButton != null) {
                    selectedRadioButton.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_radioBtn)));
                }
            }
        });


    }

    private void setQuestionScreen(int currentQuestion) {
        quiztext.setText(questionsItems.get(currentQuestion).getQuestions());
        aans.setText(questionsItems.get(currentQuestion).getAnswer1());
        bans.setText(questionsItems.get(currentQuestion).getAnswer2());
        cans.setText(questionsItems.get(currentQuestion).getAnswer3());
        dans.setText(questionsItems.get(currentQuestion).getAnswer4());
    }

    private void updateFlagImage() {
        String flagFilename = questionsItems.get(currentQuestion).getFlagImagePath();
        int resID = getResources().getIdentifier(flagFilename, "drawable", getPackageName());
        flagImage.setImageResource(resID);
    }


    private void loadAllQuestions() {
        questionsItems = new ArrayList<>();
        String jsonquiz = loadJsonFromAsset("questions.json");
        try {
            JSONObject jsonObject = new JSONObject(jsonquiz);
            JSONArray questions = jsonObject.getJSONArray("questions");
            for (int i = 0; i < questions.length(); i++) {
                JSONObject question = questions.getJSONObject(i);

                String questionsString = question.getString("question");
                String answer1String = question.getString("answer1");
                String answer2String = question.getString("answer2");
                String answer3String = question.getString("answer3");
                String answer4String = question.getString("answer4");
                String correctString = question.getString("correct");
                String flagImagePath = question.getString("flagImagePath");

                questionsItems.add(new Questions(questionsString, answer1String, answer2String, answer3String, answer4String, correctString, flagImagePath));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String loadJsonFromAsset(String s) {
        String json = "";
        try {
            InputStream inputStream = getAssets().open(s);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");


        } catch (IOException e) {
            e.printStackTrace();

        }
        return json;
    }

    @Override
    public void onBackPressed() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.app_name)
                .setMessage("Tem certeza que deseja sair do Quiz em andamento?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Quiz.this, MainActivity.class));
                        finish();
                    }
                })
                .show();
    }
}
