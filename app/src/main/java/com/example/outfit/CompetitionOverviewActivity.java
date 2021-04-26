package com.example.outfit;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CompetitionOverviewActivity extends AppCompatActivity implements View.OnClickListener {

    TextView compName;
    TextView numMembers;
    TextView smallLeaderboard;
    Button leaderboard;
    Button compete;

    int competitionID;
    String competitionName;

    Intent loadLeaderboard;
    Intent loadCompete;

    MyAsyncTask myAsyncTask;

    List<UserForLeaderboards> fullParticipantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.competition_overview);

        compName = findViewById(R.id.compNameText);
        numMembers = findViewById(R.id.numMembersText);
        smallLeaderboard = findViewById(R.id.topLeaders);
        leaderboard = findViewById(R.id.buttonLeaderboard);
        compete = findViewById(R.id.buttonCompete);

        competitionID = getIntent().getIntExtra("CompID", 0);
        competitionName = getIntent().getStringExtra("CompName");
        compName.setText(competitionName);

        leaderboard.setOnClickListener(this);
        compete.setOnClickListener(this);

        fullParticipantList = new ArrayList<UserForLeaderboards>();

        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();

        // Make intents
        loadLeaderboard = new Intent(this, FullLeaderboardActivity.class);
        loadCompete = new Intent(this, CompeteActivity.class);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == leaderboard.getId()){
            String extra = "";
            for(int i = 0; i < fullParticipantList.size(); i++){
                UserForLeaderboards user = fullParticipantList.get(i);
                extra += user.getUserName() + " "+user.getScore()+",";
            }
            loadLeaderboard.putExtra("LeaderboardData", extra);
            this.startActivity(loadLeaderboard);
        }
        else if(v.getId() == compete.getId()){
            loadCompete.putExtra("CompID", competitionID);
            this.startActivity(loadCompete);
        }
    }

    public void updateNumMembersField(int newNum){
        numMembers.setText("Number of Members: "+newNum);
    }

    public void insertionSort(UserForLeaderboards user){
        boolean inserted = false;
        if(fullParticipantList.size() == 0){
            fullParticipantList.add(user);
        }
        else{
            for(int i = 0; i < fullParticipantList.size(); i++){
                if(fullParticipantList.get(i).getScore() <= user.getScore()){
                    fullParticipantList.add(i, user);
                    inserted = true;
                    break;
                }
            }
            if(!inserted){
                fullParticipantList.add(user);
            }
        }
    }

    public void updateLeaderboard(){
        String output;
        if(fullParticipantList.size() > 3){
            output = "Top 3 Leaderboard\n";
        }
        else{
            output = "Top "+fullParticipantList.size()+" Leaderboard\n";
        }
        for(int i = 0; i < fullParticipantList.size(); i++){
            UserForLeaderboards temp = fullParticipantList.get(i);
            output += "User: "+temp.getUserName()+" Score: "+temp.getScore()+"\n";
            if(i == 2) break;
        }
        smallLeaderboard.setText(output);
    }

    private class MyAsyncTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            FirebaseDatabase.getInstance().getReference("Competition").child(String.valueOf(competitionID)).child("userList").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    DataSnapshot snapshot = task.getResult();
                    ArrayList<UserInComp> temp = (ArrayList<UserInComp>) snapshot.getValue();
                    int numberMembers = temp.size();
                    updateNumMembersField(numberMembers);

                    for(int i = 0; i < numberMembers; i++){
                        FirebaseDatabase.getInstance().getReference("Competition").child(String.valueOf(competitionID)).child("userList").child(String.valueOf(i)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                DataSnapshot snapshot1 = task.getResult();
                                String userID = snapshot1.child("userID").getValue().toString();
                                long score = (long) snapshot1.child("score").getValue();
                                FirebaseDatabase.getInstance().getReference("Users").child(userID).child("userName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        String userName = task.getResult().getValue().toString();
                                        UserForLeaderboards user = new UserForLeaderboards(userName, (int) score);
                                        insertionSort(user);
                                        updateLeaderboard();
                                    }
                                });
                            }
                        });
                    }
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

    }
}
