package com.example.outfit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    Button registerButton;
    EditText firstnameText;
    EditText lastnameText;
    EditText usernameText;
    EditText emailText;
    EditText passwordText;
    EditText confirmPasswordText;

    Intent mainActivityIntent;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        registerButton = findViewById(R.id.registerButton);
        firstnameText = findViewById(R.id.firstnameRegText);
        lastnameText = findViewById(R.id.lastnameRegText);
        usernameText = findViewById(R.id.usernameRegText);
        emailText = findViewById(R.id.emailRegText);
        passwordText = findViewById(R.id.passwordRegText);
        confirmPasswordText = findViewById(R.id.confirmPasswordRegText);
        mAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(this);

        mainActivityIntent = new Intent(this, MainActivity.class);
    }



    @Override
    public void onClick(View view) {

        String first = firstnameText.getText().toString().trim();
        String last = lastnameText.getText().toString().trim();
        String user = usernameText.getText().toString().trim();
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        if (first.isEmpty()){
            firstnameText.setError("Enter Valid First Name");
            firstnameText.requestFocus();
            return;
        }
        if (last.isEmpty()){
            lastnameText.setError("Enter Valid Last Name");
            lastnameText.requestFocus();
            return;
        }
        if (user.isEmpty()){
            usernameText.setError("Enter Valid User Name");
            usernameText.requestFocus();
            return;
        }
        if (email.isEmpty()){
            emailText.setError("Enter Valid Email");
            emailText.requestFocus();
            return;
        }
        if (password.isEmpty()){
            passwordText.setError("Enter Valid Password");
            passwordText.requestFocus();
            return;
        }
        Toast.makeText(RegistrationActivity.this, "Fish", Toast.LENGTH_LONG).show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User newUser = new User(first, last, user, email, password);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegistrationActivity.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }
                });

        if(view.getId() == registerButton.getId()){
            this.startActivity(mainActivityIntent);
        }
    }
}
