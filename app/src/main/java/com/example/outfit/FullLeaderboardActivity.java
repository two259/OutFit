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

        String extra = getIntent().getStringExtra("LeaderboardData");

        List<LeaderboardItem> temp = new ArrayList<LeaderboardItem>();

        String[] split = extra.split(",");
        for(int i = 0; i < split.length; i++){
            String[] split2 = split[i].split(" ");
            LeaderboardItem tempItem = new LeaderboardItem(split2[0], Integer.valueOf(split2[1]));
            temp.add(tempItem);
        }

        leaderRV = findViewById(R.id.leader_recycler);
        fla = new FullLeaderboardAdapter(this, temp);
        leaderRV.setAdapter(fla);
        leaderRV.setLayoutManager(new LinearLayoutManager(this));
    }
}
