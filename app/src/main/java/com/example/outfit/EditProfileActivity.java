package com.example.outfit;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView picture;
    TextView firstName;
    TextView lastName;
    TextView username;
    Button changeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        picture = (ImageView) findViewById(R.id.edit_picture);
        firstName = findViewById(R.id.edit_firstName);
        lastName = findViewById(R.id.edit_lastName);
        username = findViewById(R.id.edit_username);
        changeButton = (Button) findViewById(R.id.confirm_change);
        changeButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if(v.getId() == changeButton.getId()){
            // First, make sure all fields are filled.
            if(firstName.getText().toString().equals("")){
                Toast.makeText(this, "Please enter a valid first name.", Toast.LENGTH_LONG).show();
                return;
            }
            if(lastName.getText().toString().equals("")){
                Toast.makeText(this, "Please enter a valid first name.", Toast.LENGTH_LONG).show();
                return;
            }
            if(username.getText().toString().equals("")){
                Toast.makeText(this, "Please enter a valid first name.", Toast.LENGTH_LONG).show();
                return;
            }

            // Update in firebase.
            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).child("firstName").setValue(firstName.getText().toString());
            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).child("lastName").setValue(lastName.getText().toString());
            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).child("userName").setValue(username.getText().toString());

            Toast.makeText(this, "Profile Successfully Updated.", Toast.LENGTH_LONG).show();
        }
    }
}