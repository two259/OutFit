package com.example.outfit;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class SearchCompActivity extends AppCompatActivity implements JoinableCompetitionAdapter.ClickJoinItemListener {

    RecyclerView joinableCompetitions;
    JoinableCompetitionAdapter jca;
    int numCompetitions;
    MyAsyncTask myAsyncTask;
    String searchString;
    List<JoinableCompetitionObj> joinList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_competition);
        myAsyncTask = new MyAsyncTask();
        String searchText = getIntent().getStringExtra("Searched");
        searchString = searchText;
        joinList = new ArrayList<JoinableCompetitionObj>();

        myAsyncTask.execute();

        joinableCompetitions = findViewById(R.id.searchRV);
        jca = new JoinableCompetitionAdapter(this, joinList, this);

        joinableCompetitions.setAdapter(jca);
        joinableCompetitions.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void clickedJoinItem(int position) {
        // Remember to get other info needed from the position index and send it.
        Intent loadJoinScreen = new Intent(this, JoinCompetitionActivity.class);
        JoinableCompetitionObj compToJoin = joinList.get(position);
        loadJoinScreen.putExtra("compID", String.valueOf(compToJoin.getCompetitionID()));
        loadJoinScreen.putExtra("compName", compToJoin.getCompetitionName());
        loadJoinScreen.putExtra("startDate", compToJoin.getCompetitionStartDate());
        loadJoinScreen.putExtra("endDate", compToJoin.getCompetitionEndDate());
        this.startActivity(loadJoinScreen);
    }

    public void updateRecycler(){
        jca = new JoinableCompetitionAdapter(this, joinList, this);
        joinableCompetitions.setAdapter(jca);
        joinableCompetitions.setLayoutManager(new LinearLayoutManager(this));
        jca.notifyDataSetChanged();
    }

    private class MyAsyncTask extends AsyncTask<Integer, Integer, Void> {


        @Override
        protected Void doInBackground(Integer... params) {
            CountDownLatch countDownLatch = new CountDownLatch(1);

            if(searchString.equals("")){ // Blank search, return first several that the user isn't in.
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
                                joinList.add(tempCompJoin);
                                updateRecycler();
                            }
                        }
                    });
                }
            }
            else{ // Filter by the searched word.
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
                                if(compName.contains(searchString)){
                                    JoinableCompetitionObj tempCompJoin = new JoinableCompetitionObj(compName, startDate, endDate, compID);
                                    joinList.add(tempCompJoin);
                                    updateRecycler();
                                }
                            }
                        }

                    });
                }

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
                String[] scoreSplit = name.split(",");
                String editName = scoreSplit[1].substring(scoreSplit[1].indexOf("=") + 1, scoreSplit[1].length() - 1);
                if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(editName)){
                    return true;
                }
            }
            return false;
        }
    }
}
