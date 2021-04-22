package com.example.outfit;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchCompActivity extends AppCompatActivity implements View.OnClickListener {

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
        jca = new JoinableCompetitionAdapter(this, temp);

        joinableCompetitions.setAdapter(jca);
        joinableCompetitions.setLayoutManager(new LinearLayoutManager(this));
        jca.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }
}
