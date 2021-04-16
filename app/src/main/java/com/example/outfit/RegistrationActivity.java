package com.example.outfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    Button registerButton;
    EditText firstnameText;
    EditText lastnameText;
    EditText usernameText;
    EditText emailText;
    EditText passwordText;
    EditText confirmPasswordText;

    Intent mainActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        registerButton = findViewById(R.id.registerButton);
        firstnameText = findViewById(R.id.firstnameRegText);
        lastnameText = findViewById(R.id.lastnameRegText);
        usernameText = findViewById(R.id.usernameRegText);
        emailText = findViewById(R.id.emailRegText);
        passwordText = findViewById(R.id.passwordText);
        confirmPasswordText = findViewById(R.id.confirmPasswordRegText);

        registerButton.setOnClickListener(this);

        mainActivityIntent = new Intent(this, MainActivity.class);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == registerButton.getId()){
            this.startActivity(mainActivityIntent);
        }
    }
}
