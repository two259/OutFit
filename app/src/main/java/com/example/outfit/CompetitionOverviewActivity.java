package com.example.outfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CompetitionOverviewActivity extends AppCompatActivity implements View.OnClickListener {

    TextView compName;
    TextView numMembers;
    TextView smallLeaderboard;
    Button leaderboard;
    Button compete;

    Intent loadLeaderboard;
    Intent loadCompete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.competition_overview);

        compName = findViewById(R.id.compNameText);
        numMembers = findViewById(R.id.numMembersText);
        smallLeaderboard = findViewById(R.id.topLeaders);
        leaderboard = findViewById(R.id.buttonLeaderboard);
        compete = findViewById(R.id.buttonCompete);

        leaderboard.setOnClickListener(this);
        compete.setOnClickListener(this);

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
            this.startActivity(loadCompete);
        }
    }
}
