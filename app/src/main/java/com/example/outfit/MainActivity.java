package com.example.outfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button loginButton;
    Button signUpButton;
    EditText usernameT;
    EditText passwordT;
    EditText steps;

    private FirebaseAuth mAuth;

    Intent loadRegistrationScreen;
    Intent loadHomeScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SensorManager deviceSensor = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor countSensor = deviceSensor.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);
        usernameT = findViewById(R.id.usernameText);
        passwordT = findViewById(R.id.passwordText);
        steps = findViewById(R.id.steps);

        signUpButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        loadRegistrationScreen = new Intent(this, RegistrationActivity.class);
        loadHomeScreen = new Intent(this, HomeActivity.class);
    }

    public void onClick(View view){
        if(view.getId() == signUpButton.getId()){
            this.startActivity(loadRegistrationScreen);
        }
        if(view.getId() == loginButton.getId()){
            String email = usernameT.getText().toString().trim();
            String password = passwordT.getText().toString().trim();
            mAuth = FirebaseAuth.getInstance();
            if (email.isEmpty()){
                usernameT.setError("Enter Valid Email");
                usernameT.requestFocus();
                return;
            }

            if (password.isEmpty()){
                passwordT.setError("Enter Valid Password");
                passwordT.requestFocus();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        MainActivity.this.startActivity(loadHomeScreen);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Incorrect Username Or Password", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }
}