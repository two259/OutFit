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
    BottomNavigationView bnv;

    // Need to add stuff for radio buttons.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_competition);

        bnv = findViewById(R.id.bottom_navigation);
        bnv.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bnv.setSelectedItemId(R.id.navigation_competitions);


        competitionName = findViewById(R.id.competitionNameText);
        competitionDescription = findViewById(R.id.eventDescText);
        createCompetition = findViewById(R.id.createEventButton);

        createCompetition.setOnClickListener(this);

        loadHomeScreen = new Intent(this, HomeActivity.class);

    }

    @Override
    public void onClick(View v) {

    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if(item.getItemId() == R.id.navigation_home){ // Home tab
                        //System.out.println("Home");
                        startActivities(0);
                        return true;
                    }
                    else if(item.getItemId() == R.id.navigation_competitions){ // Competitions tab
                        //System.out.println("Competitions");
                        startActivities(1);
                        return true;
                    }
                    else if(item.getItemId() == R.id.navigation_social){ // Social/Messages tab
                        //System.out.println("Social");
                        return true;
                    }
                    else if(item.getItemId() == R.id.navigation_profile){ // Profile tab
                        //System.out.println("Profile");
                        return true;
                    }
                    return false;
                }
            };

    public void startActivities(int id){
        if(id == 0){
            this.startActivity(loadHomeScreen);
        }
        else if(id == 1){
            // Already in competitions.
        }
        else if(id == 2){

        }
        else if(id == 3){

        }
    }
}
