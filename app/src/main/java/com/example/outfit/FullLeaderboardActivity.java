package com.example.outfit;

import android.os.Bundle;
import android.widget.AbsListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FullLeaderboardActivity extends AppCompatActivity {

    RecyclerView leaderRV;
    FullLeaderboardAdapter fla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_leaderboard);

        List<LeaderboardItem> temp = new ArrayList<LeaderboardItem>();

        leaderRV = findViewById(R.id.leader_recycler);
        fla = new FullLeaderboardAdapter(this, temp);
        leaderRV.setAdapter(fla);
        leaderRV.setLayoutManager(new LinearLayoutManager(this));
    }
}
