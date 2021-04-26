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

        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();

        // Make intents
        loadLeaderboard = new Intent(this, FullLeaderboardActivity.class);
        loadCompete = new Intent(this, CompeteActivity.class);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == leaderboard.getId()){
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
