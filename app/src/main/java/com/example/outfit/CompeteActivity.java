package com.example.outfit;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CompeteActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager stepSensor;
    TextView steps;
    Button startStop;
    boolean running = false;
    int totalSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compete_activity);
        steps = findViewById(R.id.stepCounter);
        startStop = findViewById(R.id.startStopButton);
        totalSteps = 0;
        stepSensor = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
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
        if(running){
            totalSteps = (int) (totalSteps + event.values[0]);
            steps.setText(String.valueOf(totalSteps));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
