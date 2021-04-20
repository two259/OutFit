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

public class CompetitionsInActivity extends AppCompatActivity implements View.OnClickListener{

    Intent loadHomeScreen;
    BottomNavigationView bnv;
    RecyclerView rv;
    InCompetitionAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.competitions_in_recycler);
        bnv = findViewById(R.id.bottom_navigation);
        bnv.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        loadHomeScreen = new Intent(this, HomeActivity.class);
        rv = findViewById(R.id.in_competition_rec);

        // Dummy data for presentation
        List<UserCompetitionsObj> temp = new ArrayList<UserCompetitionsObj>();
        UserCompetitionsObj testComp1 = new UserCompetitionsObj("Competition 1", "4/21/2021", "5/21/2021");
        UserCompetitionsObj testComp2 = new UserCompetitionsObj("Competition 2", "4/21/2021", "6/21/2021");
        temp.add(testComp1);
        temp.add(testComp2);
        adapter = new InCompetitionAdapter(this, temp);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
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
            // Already at competitions. Do nothing
        }
        else if(id == 2){

        }
        else if(id == 3){

        }
    }
}
