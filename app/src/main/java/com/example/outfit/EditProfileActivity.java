package com.example.outfit;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class EditProfileActivity extends AppCompatActivity {

    ImageView picture;
    EditText firstName;
    EditText lastName;
    EditText username;
    EditText email;
    Button changeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        picture = (ImageView) findViewById(R.id.edit_picture);
        firstName = (EditText) findViewById(R.id.edit_firstName);
        lastName = (EditText) findViewById(R.id.edit_lastName);
        username = (EditText) findViewById(R.id.edit_username);
        email = (EditText) findViewById(R.id.edit_email);
        changeButton = (Button) findViewById(R.id.confirm_change);

    }

    /**
     * this method confirms all the changes made in edit_profile_activity
     * @param view
     */
    public void confirmChange(View view){

    }

    /**
     * change password
     */
    public void changePassword(View view){

    }
}