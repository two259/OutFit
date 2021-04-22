package com.example.outfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchCompActivity extends AppCompatActivity implements JoinableCompetitionAdapter.ClickJoinItemListener {

    RecyclerView joinableCompetitions;
    JoinableCompetitionAdapter jca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_competition);

        List<JoinableCompetitionObj> temp = new ArrayList<JoinableCompetitionObj>();
        JoinableCompetitionObj testComp1 = new JoinableCompetitionObj("Competition 1", "4/21/2021", "5/21/2021");
        JoinableCompetitionObj testComp2 = new JoinableCompetitionObj("Competition 2", "4/21/2021", "6/21/2021");
        temp.add(testComp1);
        temp.add(testComp2);

        joinableCompetitions = findViewById(R.id.searchRV);
        jca = new JoinableCompetitionAdapter(this, temp, this);

        joinableCompetitions.setAdapter(jca);
        joinableCompetitions.setLayoutManager(new LinearLayoutManager(this));
        jca.notifyDataSetChanged();
    }


    @Override
    public void clickedJoinItem(int position) {
        // Remember to get other info needed from the position index and send it.

        Intent loadJoinScreen = new Intent(this, JoinCompetitionActivity.class);
        this.startActivity(loadJoinScreen);
    }
}
