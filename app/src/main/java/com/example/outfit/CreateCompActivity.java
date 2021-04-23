package com.example.outfit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class CreateCompActivity extends AppCompatActivity implements View.OnClickListener {

    int numCompetitions;
    Intent loadHomeScreen;
    TextView competitionName;
    Button createCompetition;
    TextView competitionDescription;
    Intent loadSuccessScreen;
    TextView startDateText;
    TextView endDateText;
    // Need to add stuff for radio buttons.
    RadioButton walkButton;
    RadioButton runButton;
    RadioButton otherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_competition);


        competitionName = findViewById(R.id.competitionNameText);
        competitionDescription = findViewById(R.id.eventDescText);
        startDateText = findViewById(R.id.startDateText);
        endDateText = findViewById(R.id.endDateText);
        createCompetition = findViewById(R.id.createEventButton);

        createCompetition.setOnClickListener(this);

        walkButton = (RadioButton) findViewById(R.id.walkingButton);
        runButton = (RadioButton) findViewById(R.id.runningButton);
        otherButton = (RadioButton) findViewById(R.id.otherButton);

        loadHomeScreen = new Intent(this, HomeActivity.class);
        loadSuccessScreen = new Intent(this, CompCreateJoinSuccessActivity.class);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == createCompetition.getId()){
            // Firebase stuff.
            String compName = competitionName.getText().toString();
            if(compName.equals("")){
                Toast.makeText(this, "Please enter a valid competition name.", Toast.LENGTH_LONG).show();
                return;
            }
            String competitionType = "";
            if(walkButton.isChecked()){
                competitionType = "Walking";
            }
            else if(runButton.isChecked()){
                competitionType = "Running";
            }
            else if(otherButton.isChecked()){
                competitionType = "Other";
            }
            else {
                Toast.makeText(this, "Please select a competition type.", Toast.LENGTH_LONG).show();
                return;
            }

            String eventDesc = competitionDescription.getText().toString();

            String startDate = startDateText.getText().toString();
            if(!startDate.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")){
                Toast.makeText(this, "Please enter a valid start date.", Toast.LENGTH_LONG).show();
                return;
            }
            String endDate = endDateText.getText().toString();
            if(!endDate.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")){
                Toast.makeText(this, "Please enter a valid end date.", Toast.LENGTH_LONG).show();
                return;
            }
            Competition currCompetition = new Competition(compName, eventDesc, startDate, endDate, competitionType, 0);

            FirebaseDatabase.getInstance().getReference("numcompetitions").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {

                    }
                    else {
                        numCompetitions = Integer.valueOf(String.valueOf(task.getResult().getValue()));
                    }
                }
            });

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    currCompetition.setCompetitionID(numCompetitions);
                    FirebaseDatabase.getInstance().getReference("Competition")
                            .child(String.valueOf(numCompetitions))
                            .setValue(currCompetition).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(CreateCompActivity.this, "Successfully Created", Toast.LENGTH_LONG).show();
                                numCompetitions++;
                                FirebaseDatabase.getInstance().getReference("numcompetitions").setValue(numCompetitions);
                            }
                        }
                    });
                }
            }, 2000);
            currCompetition.setCompetitionID(numCompetitions);
            this.startActivity(loadSuccessScreen);
        }
    }
}
