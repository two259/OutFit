package com.example.outfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button loginButton;
    Button signUpButton;
    EditText usernameText;
    EditText passwordText;

    Intent loadRegistrationScreen;
    Intent loadHomeScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);
        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);

        signUpButton.setOnClickListener(this);

        loadRegistrationScreen = new Intent(this, RegistrationActivity.class);
        //loadHomeScreen = new Intent(this, HomeActivity.class);
    }

    public void onClick(View view){
        if(view.getId() == signUpButton.getId()){
            this.startActivity(loadRegistrationScreen);
        }
    }
}