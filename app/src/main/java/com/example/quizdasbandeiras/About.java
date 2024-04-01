package com.example.quizdasbandeiras;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class About extends AppCompatActivity {

    MaterialCardView developerCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);

        developerCard = findViewById(R.id.developerCard);


        developerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.instagram.com/_beaaamonteiro/");
                Intent likelng= new Intent(Intent.ACTION_VIEW,uri);
                likelng.setPackage("com.instagram.android");
                try{
                    startActivity(likelng);
                }catch (ActivityNotFoundException e){
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.instagram.com/_beaaamonteiro/")));
                }

            }
        });


    }
}