package com.example.outfit;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CompeteActivity extends AppCompatActivity implements SensorEventListener {

    //Compete
    SensorManager stepSensor;
    TextView steps;
    Button startStop;
    boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.compete_activity);
        steps = (TextView) findViewById(R.id.stepCounter);
        startStop = (Button) findViewById(R.id.startStopButton);

        stepSensor = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    }

    @Override
    protected void onResume(){
        super.onResume();
        running = true;
        Sensor sensorCount = stepSensor.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (sensorCount != null){
            stepSensor.registerListener(this, sensorCount, SensorManager.SENSOR_DELAY_UI);
        }
        else{
            Toast.makeText(CompeteActivity.this, "Sensor Not Found", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        running = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running){
            steps.setText(String.valueOf(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
