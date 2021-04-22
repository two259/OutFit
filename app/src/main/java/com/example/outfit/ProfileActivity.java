package com.example.outfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView bnv;
    Intent loadInCompetitionsScreen;
    Intent loadHomeScreen;

    TextView username;
    TextView userEmail;
    ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //navigation bar setup
        bnv = findViewById(R.id.bottom_navigation);
        bnv.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bnv.setSelectedItemId(R.id.navigation_profile);

        loadInCompetitionsScreen = new Intent(this, CompetitionsInActivity.class);
        loadHomeScreen = new Intent(this, HomeActivity.class);

        //screen view setup
        username = findViewById(R.id.username);
        userEmail = findViewById(R.id.userEmail);
        profileImage = findViewById(R.id.profileImage);

        //dummy data
        username.setText(String.format("%15s", "UserID: ") + "kenling");
        userEmail.setText(String.format("%15s", "UserEmail: ") + "kenling@vt.edu");

    }



    public void startActivities(int id){
        if(id == 0){
            this.startActivity(loadHomeScreen);
        }
        else if(id == 1){
            this.startActivity(loadInCompetitionsScreen);
        }
        else if(id == 2){

        }
        else if(id == 3){
            //in profile screen
        }
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
                        //startActivities(3);

                        return true;
                    }
                    return false;
                }
            };
}