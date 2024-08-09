package com.example.vsga_2024;

import static java.lang.String.format;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.progressindicator.CircularProgressIndicator;

public class MainActivity extends AppCompatActivity  implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private TextView lightValueTextView;
    private TextView lightConditionTextView;
    private CircularProgressIndicator lightProgressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lightValueTextView = findViewById(R.id.lightValueTextView);
        lightConditionTextView = findViewById(R.id.lightConditionTextView);
        lightProgressIndicator = findViewById(R.id.lightProgressIndicator);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }

        // Set up the progress indicator
        lightProgressIndicator.setMax(1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float lightValue = event.values[0];
            lightValueTextView.setText(format("Nilai Cahaya: %.1f lux", lightValue));
            analyzeLight(lightValue);
            updateProgressIndicator(lightValue);
        }
    }

    @SuppressLint("SetTextI18n")
    private void analyzeLight(float lightValue) {
        String condition;
        if (lightValue < 10) {
            condition = "Sangat Gelap";
        } else if (lightValue < 50) {
            condition = "Gelap";
        } else if (lightValue < 200) {
            condition = "Redup";
        } else if (lightValue < 400) {
            condition = "Normal";
        } else if (lightValue < 1000) {
            condition = "Terang";
        } else {
            condition = "Sangat Terang";
        }
        lightConditionTextView.setText("Kondisi: " + condition);
    }

    private void updateProgressIndicator(float lightValue) {
        int progress = (int) Math.min(lightValue, 1000);
        lightProgressIndicator.setProgress(progress);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i){}

}