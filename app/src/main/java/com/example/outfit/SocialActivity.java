package com.example.outfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SocialActivity extends AppCompatActivity implements View.OnClickListener{
    Intent loadHomeScreen;
    Intent loadCompScreen;
    Intent loadProfileScreen;
    BottomNavigationView bnv;
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socialscreen);

        RecyclerView social_rec = findViewById(R.id.social_rec);

        users = new ArrayList<>();

        SocialScreenAdapter socialScreenAdapter = new SocialScreenAdapter(this, users);
        social_rec.setAdapter(socialScreenAdapter);
        social_rec.setLayoutManager(new LinearLayoutManager(this));

        bnv = findViewById(R.id.bottom_navigation);
        bnv.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bnv.setSelectedItemId(R.id.navigation_competitions);
        loadHomeScreen = new Intent(this, HomeActivity.class);
        loadCompScreen = new Intent(this,CompetitionsInActivity.class);
        loadProfileScreen = new Intent(this,ProfileActivity.class);
        //rv = findViewById(R.id.in_competition_rec);

        //searchBar = findViewById(R.id.searchBarComp);
        //searchButton = findViewById(R.id.searchButton);
        //createButton = findViewById(R.id.createCompButton);

        //searchButton.setOnClickListener(this);
        //createButton.setOnClickListener(this);

        // Dummy data for presentation
        /*List<UserCompetitionsObj> temp = new ArrayList<UserCompetitionsObj>();
        UserCompetitionsObj testComp1 = new UserCompetitionsObj("Competition 1", "4/21/2021", "5/21/2021");
        UserCompetitionsObj testComp2 = new UserCompetitionsObj("Competition 2", "4/21/2021", "6/21/2021");
        UserCompetitionsObj testComp3 = new UserCompetitionsObj("Competition 3", "4/25/2021", "4/28/2021");
        UserCompetitionsObj testComp4 = new UserCompetitionsObj("Competition 4", "4/12/2021", "6/12/2021");
        UserCompetitionsObj testComp5 = new UserCompetitionsObj("Competition 5", "4/01/2021", "5/01/2021");
        UserCompetitionsObj testComp6 = new UserCompetitionsObj("Competition 6", "5/01/2021", "7/01/2021");
        UserCompetitionsObj testComp7 = new UserCompetitionsObj("Competition 7", "4/11/2021", "5/30/2021");
        UserCompetitionsObj testComp8 = new UserCompetitionsObj("Competition 8", "3/01/2021", "6/01/2021");
        UserCompetitionsObj testComp9 = new UserCompetitionsObj("Competition 9", "5/21/2021", "6/21/2021");
        UserCompetitionsObj testComp10 = new UserCompetitionsObj("Competition 10", "4/21/2021", "6/21/2021");
        UserCompetitionsObj testComp11 = new UserCompetitionsObj("", "", "");
        UserCompetitionsObj testComp12 = new UserCompetitionsObj("", "", "");
        temp.add(testComp1);
        temp.add(testComp2);
        temp.add(testComp3);
        temp.add(testComp4);
        temp.add(testComp5);
        temp.add(testComp6);
        temp.add(testComp7);
        temp.add(testComp8);
        temp.add(testComp9);
        temp.add(testComp10);
        temp.add(testComp11);
        temp.add(testComp12);*/
        //adapter = new InCompetitionAdapter(this, temp);
        //rv.setAdapter(adapter);
        //rv.setLayoutManager(new LinearLayoutManager(this));
        //adapter.notifyDataSetChanged();

        //loadCreateComp = new Intent(this, CreateCompActivity.class);
    }

    @Override
    public void onClick(View view) {

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
                        startActivities(3);
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
            this.startActivity(loadCompScreen);
        }
        else if(id == 2){

        }
        else if(id == 3){
            this.startActivity(loadProfileScreen);
        }
    }
}
