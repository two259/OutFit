package com.example.outfit;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class CompeteActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {

    SensorManager stepSensor;
    TextView steps;
    Button startStop;
    boolean running = false;
    int totalSteps;
    MyAsyncTask myAsyncTask;
    int competitionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compete_activity);
        steps = findViewById(R.id.stepCounter);
        startStop = findViewById(R.id.startStopButton);
        totalSteps = 0;
        stepSensor = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        startStop.setOnClickListener(this);
        myAsyncTask = new MyAsyncTask();
        // Get competitionID.
        competitionID = getIntent().getIntExtra("CompID", 0);
    }

    @Override
    protected void onResume(){
        super.onResume();
        running = true;
        Sensor sensorCount = stepSensor.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if(sensorCount != null){
            stepSensor.registerListener(this, sensorCount, SensorManager.SENSOR_DELAY_UI);
        }
        else{
            Toast.makeText(this, "Sensor not found", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        running = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Count the steps.
        if(running && startStop.getText().toString().equals("Stop")){
            totalSteps = (int) (totalSteps + event.values[0]);
            steps.setText(String.valueOf(totalSteps) + " Steps");
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == startStop.getId()){
            if(startStop.getText().toString().equals("Start")){
                startStop.setText("Stop");
                startStop.setBackgroundColor(Color.RED);
            }
            else if(startStop.getText().toString().equals("Stop")){
                // Record the new steps and add to firebase.
                myAsyncTask.execute(totalSteps);

                // Reset data.
                totalSteps = 0;
                startStop.setText("Start");
                startStop.setBackgroundColor(0x8BC34A);
                steps.setText(0 + " Steps");
            }
        }
    }

    private class MyAsyncTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            int newSteps = params[0];
            FirebaseDatabase.getInstance().getReference("Competition").child(String.valueOf(competitionID)).child("userList").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    DataSnapshot snapshot = task.getResult();
                    String test = snapshot.getValue().toString();
                    int finder = findIndex(test);
                    if(finder == -1) return;
                    FirebaseDatabase.getInstance().getReference("Competition").child(String.valueOf(competitionID)).child("userList").child(String.valueOf(finder)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            DataSnapshot snapshot1 = task.getResult();
                            long currScore = (long) snapshot1.child("score").getValue();
                            currScore = currScore + newSteps;
                            FirebaseDatabase.getInstance().getReference("Competition").child(String.valueOf(competitionID)).child("userList").child(String.valueOf(finder)).child("score").setValue(currScore);
                        }
                    });
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            myAsyncTask = new MyAsyncTask();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        protected int findIndex(String record){
            String[] checker = record.split("\\}");
            for(int i = 0; i < checker.length - 1; i++){
                if(i == 0){
                    String[] splitter2 = checker[0].split(",");
                    String user = splitter2[1].substring(splitter2[1].indexOf("=") + 1);
                    if(user.equals(FirebaseAuth.getInstance().getUid())) return i;
                }
                else{
                    String[] splitter2 = checker[i].split(",");
                    String user = splitter2[2].substring(splitter2[2].indexOf("=") + 1);
                    if(user.equals(FirebaseAuth.getInstance().getUid())) return i;
                }
            }
            return -1;
        }
    }
}
