package com.example.outfit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CreateCompActivity extends AppCompatActivity implements View.OnClickListener {

    int numCompetitions;
    Intent loadHomeScreen;
    TextView competitionName;
    Button createCompetition;
    TextView competitionDescription;
    Intent loadSuccessScreen;

    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDateDialog;
    DatePickerDialog.OnDateSetListener endDateDialog;
    EditText startDateText;
    EditText endDateText;
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
        startDateText = (EditText) findViewById(R.id.startDateText);
        endDateText = (EditText) findViewById(R.id.endDateText);
        createCompetition = findViewById(R.id.createEventButton);

        createCompetition.setOnClickListener(this);

        walkButton = (RadioButton) findViewById(R.id.walkingButton);
        runButton = (RadioButton) findViewById(R.id.runningButton);
        otherButton = (RadioButton) findViewById(R.id.otherButton);

        loadHomeScreen = new Intent(this, HomeActivity.class);
        loadSuccessScreen = new Intent(this, CompCreateJoinSuccessActivity.class);


        //set up a date dialog for start date
        startDateDialog = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                startDateText.setText(sdf.format(myCalendar.getTime()));
            }

        };

        //set up a date dialog for end date
        endDateDialog = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                endDateText.setText(sdf.format(myCalendar.getTime()));
            }

        };

    }

    /**
     * date picker will pop up for start date
     * @param view
     */
    public void clickStartDate(View view){
        new DatePickerDialog(CreateCompActivity.this, startDateDialog, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * date picker will pop up for end date
     * @param view
     */
    public void clickEndDate(View view){
        new DatePickerDialog(CreateCompActivity.this, endDateDialog, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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

            Toast.makeText(this, "Creating Event", Toast.LENGTH_SHORT).show();

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
                    List<UserInComp> tempList = currCompetition.getUserList();
                    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    UserInComp tempUser = new UserInComp(userID, 0);
                    tempList.add(tempUser);
                    FirebaseDatabase.getInstance().getReference("Competition")
                            .child(String.valueOf(numCompetitions))
                            .setValue(currCompetition).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(CreateCompActivity.this, "Successfully Created", Toast.LENGTH_LONG).show();
                                UserCompetitionsObj tempUserObj = new UserCompetitionsObj(currCompetition.getCompetitionName(), currCompetition.getStartDate(), currCompetition.getEndDate(), currCompetition.getCompetitionID());
                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).child("UserCompetitions").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        DataSnapshot snapshot = task.getResult();
                                        ArrayList<UserCompetitionsObj> temp = (ArrayList<UserCompetitionsObj>) snapshot.getValue();
                                        int index = 0;
                                        if(temp == null){
                                            index = 0;
                                        }
                                        else{
                                            index = temp.size();
                                        }
                                        FirebaseDatabase.getInstance().getReference("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("UserCompetitions").child(String.valueOf(index))
                                                .setValue(tempUserObj).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){

                                                }
                                            }
                                        });
                                    }
                                });
                                numCompetitions++;
                                FirebaseDatabase.getInstance().getReference("numcompetitions").setValue(numCompetitions);
                                CreateCompActivity.this.startActivity(loadSuccessScreen);
                            }
                        }
                    });
                }
            }, 2000);
        }
    }
}
