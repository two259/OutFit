package com.example.outfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CreateCompActivity extends AppCompatActivity implements View.OnClickListener {

    Intent loadHomeScreen;
    TextView competitionName;
    Button createCompetition;
    TextView competitionDescription;
    Intent loadSuccessScreen;
    // Need to add stuff for radio buttons.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_competition);


        competitionName = findViewById(R.id.competitionNameText);
        competitionDescription = findViewById(R.id.eventDescText);
        createCompetition = findViewById(R.id.createEventButton);

        createCompetition.setOnClickListener(this);

        loadHomeScreen = new Intent(this, HomeActivity.class);
        loadSuccessScreen = new Intent(this, CompCreateJoinSuccessActivity.class);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == createCompetition.getId()){
            this.startActivity(loadSuccessScreen);
        }
    }
}
