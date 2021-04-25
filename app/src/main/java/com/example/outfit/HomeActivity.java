package com.example.outfit;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, JoinableCompetitionAdapter.ClickJoinItemListener {

    BottomNavigationView bnv;
    Intent loadInCompetitionsScreen;
    Intent loadCreateComp;
    Button createCompetition;
    Intent profileActivity;
    RecyclerView homeRecycler;
    JoinableCompetitionAdapter jca;
    List<JoinableCompetitionObj> compList;
    MyAsyncTask myAsyncTask;
    int numCompetitions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        bnv = findViewById(R.id.bottom_navigation);
        bnv.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        myAsyncTask = new MyAsyncTask();

        compList = new ArrayList<JoinableCompetitionObj>();

        myAsyncTask.execute();

        homeRecycler = findViewById(R.id.home_recycler);
        jca = new JoinableCompetitionAdapter(this, compList, this);
        homeRecycler.setAdapter(jca);
        homeRecycler.setLayoutManager(new LinearLayoutManager(this));

        createCompetition = findViewById(R.id.createCompButtonHome);
        createCompetition.setOnClickListener(this);

        loadInCompetitionsScreen = new Intent(this, CompetitionsInActivity.class);
        loadCreateComp = new Intent(this, CreateCompActivity.class);

        profileActivity = new Intent(this, ProfileActivity.class);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == createCompetition.getId()){
            this.startActivity(loadCreateComp);
        }
    }

    public void startActivities(int id){
        if(id == 0){
            // Already home, do nothing.
        }
        else if(id == 1){
            this.startActivity(loadInCompetitionsScreen);
        }
        else if(id == 2){

        }
        else if(id == 3){
            this.startActivity(profileActivity);
        }
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if(item.getItemId() == R.id.navigation_home){ // Home tab
                        //System.out.println("Home");
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
                        startActivities(3);
                        return true;
                    }
                    return false;
                }
            };

    @Override
    public void clickedJoinItem(int position) {
        // Remember to get other info needed from the position index and send it.
        Intent loadJoinScreen = new Intent(this, JoinCompetitionActivity.class);
        JoinableCompetitionObj compToJoin = compList.get(position);
        loadJoinScreen.putExtra("compID", String.valueOf(compToJoin.getCompetitionID()));
        loadJoinScreen.putExtra("compName", compToJoin.getCompetitionName());
        loadJoinScreen.putExtra("startDate", compToJoin.getCompetitionStartDate());
        loadJoinScreen.putExtra("endDate", compToJoin.getCompetitionEndDate());
        this.startActivity(loadJoinScreen);
    }

    public void updateRecycler(){
        jca = new JoinableCompetitionAdapter(this, compList, this);
        homeRecycler.setAdapter(jca);
        homeRecycler.setLayoutManager(new LinearLayoutManager(this));
        jca.notifyDataSetChanged();
    }

    private class MyAsyncTask extends AsyncTask<Integer, Integer, Void> {


        @Override
        protected Void doInBackground(Integer... params) {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            FirebaseDatabase.getInstance().getReference("numcompetitions").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    numCompetitions = Integer.valueOf(String.valueOf(task.getResult().getValue()));
                    countDownLatch.countDown();
                }
            });
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i = 0; i < numCompetitions; i++){
                FirebaseDatabase.getInstance().getReference("Competition").child(String.valueOf(i)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        DataSnapshot dataSnapshot = task.getResult();
                        String compName = dataSnapshot.child("competitionName").getValue(String.class);
                        ArrayList test = (ArrayList) dataSnapshot.child("userList").getValue();
                        String compDesc = dataSnapshot.child("compDescription").getValue(String.class);
                        int compID = dataSnapshot.child("competitionID").getValue(Integer.class);
                        String compType = dataSnapshot.child("competitionType").getValue(String.class);
                        String startDate = dataSnapshot.child("startDate").getValue(String.class);
                        String endDate = dataSnapshot.child("endDate").getValue(String.class);
                        Competition tempComp = new Competition(compName, compDesc, startDate, endDate, compType, compID);
                        boolean check = checkInList(test);
                        if(!check){
                            JoinableCompetitionObj tempCompJoin = new JoinableCompetitionObj(compName, startDate, endDate, compID);
                            compList.add(tempCompJoin);
                            updateRecycler();
                        }
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            updateRecycler();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        protected boolean checkInList(ArrayList list){
            for(int i = 0; i < list.size(); i++){
                String name = list.get(i).toString();
                String editName = name.substring(name.indexOf("=") + 1, name.length() - 1);
                if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(editName)){
                    return true;
                }
            }
            return false;
        }
    }
}
