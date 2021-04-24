package com.example.outfit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CompetitionsInActivity extends AppCompatActivity implements InCompetitionAdapter.ClickInCompItemListener, View.OnClickListener {

    Intent loadHomeScreen;
    BottomNavigationView bnv;
    RecyclerView rv;
    InCompetitionAdapter adapter;
    Button searchButton;
    Button createButton;
    TextView searchBar;

    Intent loadCreateComp;
    Intent loadSearchComp;
    Intent loadProfile;

    List<UserCompetitionsObj> temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.competitions_in_recycler);
        bnv = findViewById(R.id.bottom_navigation);
        bnv.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bnv.setSelectedItemId(R.id.navigation_competitions);
        loadHomeScreen = new Intent(this, HomeActivity.class);
        rv = findViewById(R.id.in_competition_rec);

        searchBar = findViewById(R.id.searchBarComp);
        searchButton = findViewById(R.id.searchButton);
        createButton = findViewById(R.id.createCompButton);

        searchButton.setOnClickListener(this);
        createButton.setOnClickListener(this);

        // Dummy data for presentation
        temp = new ArrayList<UserCompetitionsObj>();
        /*
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
        temp.add(testComp12);
         */

        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("UserCompetitions").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {

                }
                else {
                    //temp = (List<Competition>) task.getResult().getValue(List<Competition>);
                    System.out.println(task.getResult().getValue().toString());
                }
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateRecycler();
            }
        }, 2000);

//        adapter = new InCompetitionAdapter(CompetitionsInActivity.this, temp, CompetitionsInActivity.this);
//        rv.setAdapter(adapter);
//        rv.setLayoutManager(new LinearLayoutManager(CompetitionsInActivity.this));
//        adapter.notifyDataSetChanged();
        loadCreateComp = new Intent(this, CreateCompActivity.class);
        loadSearchComp = new Intent(this, SearchCompActivity.class);
        loadProfile = new Intent(this, ProfileActivity.class);
        //System.out.println("List size: " +temp.size());
    }


    @Override
    public void onClick(View v) {
        if(searchButton.getId() == v.getId()){ // Search button
            this.startActivity(loadSearchComp);
        }
        else if(createButton.getId() == v.getId()){ // Create button
            this.startActivity(loadCreateComp);
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
            this.startActivity(loadProfile);
        }
    }

    @Override
    public void clickedInCompItem(int position) {
        // Remember to get other info needed from the position index and send it.

        Intent loadCompScreen = new Intent(this, JoinCompetitionActivity.class); // Change the class when its made.
        this.startActivity(loadCompScreen);
    }

    private void updateRecycler(){
        adapter = new InCompetitionAdapter(CompetitionsInActivity.this, temp, CompetitionsInActivity.this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(CompetitionsInActivity.this));
        adapter.notifyDataSetChanged();
    }
}
