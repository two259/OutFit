package com.example.outfit;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SocialActivity extends AppCompatActivity implements View.OnClickListener{
    Intent loadHomeScreen;
    Intent loadCompScreen;
    Intent loadProfileScreen;
    BottomNavigationView bnv;
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socialscreen);

        RecyclerView social_rec = findViewById(R.id.social_rec);

        users = new ArrayList<>();

        User userTemp = new User("test", "person", "test", "test@gmail.com", "testPassword");
        users.add(userTemp);

        SocialScreenAdapter socialScreenAdapter = new SocialScreenAdapter(this, users);
        social_rec.setAdapter(socialScreenAdapter);
        social_rec.setLayoutManager(new LinearLayoutManager(this));

        bnv = findViewById(R.id.bottom_navigation);
        bnv.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bnv.setSelectedItemId(R.id.navigation_social );
        loadHomeScreen = new Intent(this, HomeActivity.class);
        loadCompScreen = new Intent(this,CompetitionsInActivity.class);
        loadProfileScreen = new Intent(this,ProfileActivity.class);

    }

    @Override
    public void onClick(View view) {

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
                    //updateViews(username, email);
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
                        return true;
                    }
                    else if(item.getItemId() == R.id.navigation_profile){ // Profile tab
                        startActivities(3);
                        return true;
                    }
                    return false;
                }
            };

    public void startActivities(int id){
        if(id == 0){
            this.startActivity(loadHomeScreen);
        }
        else if(id == 1){
            this.startActivity(loadCompScreen);
        }
        else if(id == 2){

        }
        else if(id == 3){
            this.startActivity(loadProfileScreen);
        }
    }
}
