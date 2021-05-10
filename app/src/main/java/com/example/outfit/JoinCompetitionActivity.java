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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class JoinCompetitionActivity extends AppCompatActivity implements View.OnClickListener{

    // The user will use this activity to join new competitions.

    Button joinButton;
    TextView eventName;
    TextView eventDescription;
    TextView numMembersBox;
    TextView datesBox;
    Intent loadSuccessScreen;
    int compID;
    MyAsyncTask myAsyncTask;
    String compName;
    String startDate;
    String endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_competition);
        myAsyncTask = new MyAsyncTask();
        joinButton = findViewById(R.id.joinEventButton);
        joinButton.setOnClickListener(this);

        eventName = findViewById(R.id.eventNameText);
        eventDescription = findViewById(R.id.eventDescriptionText);
        numMembersBox = findViewById(R.id.numberMembersText);
        datesBox = findViewById(R.id.eventDatesText);

        eventName.setText("Event Name: "+getIntent().getStringExtra("compName"));
        compID = Integer.valueOf(getIntent().getStringExtra("compID"));
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        compName = getIntent().getStringExtra("compName");
        datesBox.setText(startDate +" - "+endDate);

        myAsyncTask.execute();

        loadSuccessScreen = new Intent(this, CompCreateJoinSuccessActivity.class);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == joinButton.getId()){
            UserCompetitionsObj temp = new UserCompetitionsObj(compName, startDate, endDate, compID);
            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).child("UserCompetitions").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    DataSnapshot snapshot = task.getResult();
                    ArrayList tempList = (ArrayList) snapshot.getValue();
                    if(tempList == null){
                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).child("UserCompetitions").child(String.valueOf(0)).setValue(temp);
                        FirebaseDatabase.getInstance().getReference("Competition").child(String.valueOf(compID)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                DataSnapshot dataSnapshot = task.getResult();
                                ArrayList temp2 = (ArrayList) dataSnapshot.child("userList").getValue();
                                int sz = temp2.size();
                                UserInComp tempuser = new UserInComp(FirebaseAuth.getInstance().getUid(), 0);
                                FirebaseDatabase.getInstance().getReference("Competition").child(String.valueOf(compID)).child("userList").child(String.valueOf(sz)).setValue(tempuser);
                            }
                        });
                    }
                    else{
                        int indexToAdd = tempList.size();
                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).child("UserCompetitions").child(String.valueOf(indexToAdd)).setValue(temp);
                        FirebaseDatabase.getInstance().getReference("Competition").child(String.valueOf(compID)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                DataSnapshot dataSnapshot = task.getResult();
                                ArrayList temp2 = (ArrayList) dataSnapshot.child("userList").getValue();
                                int sz = temp2.size();
                                UserInComp tempuser = new UserInComp(FirebaseAuth.getInstance().getUid(), 0);
                                FirebaseDatabase.getInstance().getReference("Competition").child(String.valueOf(compID)).child("userList").child(String.valueOf(sz)).setValue(tempuser);
                            }
                        });
                    }

                }
            });
            this.startActivity(loadSuccessScreen);
        }
    }

    private class MyAsyncTask extends AsyncTask<Integer, Integer, Void> {

        String desc;
        int numMembers;

        @Override
        protected Void doInBackground(Integer... params) {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            FirebaseDatabase.getInstance().getReference("Competition").child(String.valueOf(compID)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    DataSnapshot snapshot = task.getResult();
                    desc = snapshot.child("compDescription").getValue(String.class);
                    ArrayList test = (ArrayList) snapshot.child("userList").getValue();
                    numMembers = test.size();
                    countDownLatch.countDown();
                }
            });
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            eventDescription.setText("Event Description: "+desc);
            numMembersBox.setText("Number of Members: "+numMembers);
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
