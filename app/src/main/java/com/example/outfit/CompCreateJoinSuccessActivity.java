package com.example.outfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CompCreateJoinSuccessActivity extends AppCompatActivity implements View.OnClickListener{

    Button backToHome;
    Button toCompList;
    Intent loadHomeScreen;
    Intent loadCompList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comp_join_create_success);

        backToHome = findViewById(R.id.buttonToHome);
        toCompList = findViewById(R.id.buttonToCompList);
        backToHome.setOnClickListener(this);
        toCompList.setOnClickListener(this);

        loadHomeScreen = new Intent(this, HomeActivity.class);
        loadCompList = new Intent(this, CompetitionsInActivity.class);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == backToHome.getId()){
            this.startActivity(loadHomeScreen);
        }
        else if(v.getId() == toCompList.getId()){
            this.startActivity(loadCompList);
        }
    }
}
