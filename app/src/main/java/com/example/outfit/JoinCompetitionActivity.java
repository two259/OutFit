package com.example.outfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class JoinCompetitionActivity extends AppCompatActivity implements View.OnClickListener{

    Button joinButton;
    TextView eventName;
    TextView eventDescription;
    TextView numMembersBox;
    TextView datesBox;
    Intent loadSuccessScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_competition);

        joinButton = findViewById(R.id.joinEventButton);
        joinButton.setOnClickListener(this);

        eventName = findViewById(R.id.eventNameText);
        eventDescription = findViewById(R.id.eventDescriptionText);
        numMembersBox = findViewById(R.id.numberMembersText);
        datesBox = findViewById(R.id.eventDatesText);

        loadSuccessScreen = new Intent(this, CompCreateJoinSuccessActivity.class);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == joinButton.getId()){
            this.startActivity(loadSuccessScreen);
        }
    }
}
