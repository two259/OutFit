package com.example.outfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{
    BottomNavigationView bnv;
    Intent loadInCompetitionsScreen;
    Intent loadHomeScreen;
    Intent loadEditProfile;
    Intent loadSocialScreen;

    TextView username;
    TextView userEmail;

    Button editProfileButton;

    MyAsyncTask myAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // navigation bar setup
        bnv = findViewById(R.id.bottom_navigation);
        bnv.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bnv.setSelectedItemId(R.id.navigation_profile);

        loadInCompetitionsScreen = new Intent(this, CompetitionsInActivity.class);
        loadHomeScreen = new Intent(this, HomeActivity.class);
        loadEditProfile = new Intent(this, EditProfileActivity.class);
        loadSocialScreen = new Intent(this, SocialActivity.class);

        // screen view setup
        username = findViewById(R.id.username);
        userEmail = findViewById(R.id.userEmail);

        editProfileButton = findViewById(R.id.editProfile);
        editProfileButton.setOnClickListener(this);

        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();

    }


    /**
     * switch screens through bottom navgation bar
     * @param id
     */
    public void startActivities(int id){
        if(id == 0){
            this.startActivity(loadHomeScreen);
        }
        else if(id == 1){
            this.startActivity(loadInCompetitionsScreen);
        }
        else if(id == 2){
            this.startActivity(loadSocialScreen);
        }
        else if(id == 3){
            //in profile screen
        }
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if(item.getItemId() == R.id.navigation_home){ // Home tab
                        //System.out.println("Home");
                        startActivities(0);
                        return true;
                    }
                    else if(item.getItemId() == R.id.navigation_competitions){ // Competitions tab
                        //System.out.println("Competitions");
                        startActivities(1);
                        return true;
                    }
                    else if(item.getItemId() == R.id.navigation_social){ // Social/Messages tab
                        //System.out.println("Social");
                        startActivities(2);
                        return true;
                    }
                    else if(item.getItemId() == R.id.navigation_profile){ // Profile tab
                        //System.out.println("Profile");
                        //startActivities(3);

                        return true;
                    }
                    return false;
                }
            };

    @Override
    public void onClick(View v) {
        if(v.getId() == editProfileButton.getId()){
            this.startActivity(loadEditProfile);
        }
    }

    public void updateViews(String user, String email){
        username.setText("UserName: "+user);
        userEmail.setText("Email: "+email);
    }

    private class MyAsyncTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    DataSnapshot snapshot = task.getResult();
                    String username = snapshot.child("userName").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    System.out.println("Here");
                    updateViews(username, email);
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }
    }
}