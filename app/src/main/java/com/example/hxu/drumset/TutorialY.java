package com.example.hxu.drumset;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class TutorialY extends AppCompatActivity implements SensorEventListener {

    double x;

    private Sensor gyroscope;
    private SensorManager sm;
    double Threshold;

    MediaPlayer snare;
    MediaPlayer rim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_y);

        // create our sensor manager
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        // gyroscope sensor
        gyroscope = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);


        Threshold = 5.0;

        snare = MediaPlayer.create(getApplicationContext(), R.raw.cymbol);
        rim = MediaPlayer.create(getApplicationContext(), R.raw.rim);


    }

    protected void onResume() {
        super.onResume();
        // register sensor listener
        if (gyroscope != null) {
            sm.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            Toast.makeText(this, "Your gyroscope is not available", Toast.LENGTH_SHORT).show();
        }

    }

    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //TODO Auto-generated method stub
        // not in use
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        Log.d("sensorchanged", event.toString());

        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            x = event.values[0];
        }


        if (x >= Threshold) {
            play(snare);
        }
        if (x <= -1) {
            play(rim);
        }
    }

    private void play(MediaPlayer m) {
        if (m.isPlaying()) {
            m.pause();
            m.seekTo(0);
        }
        m.start();
    }

    public void nextTut(View view){
        Intent intent = new Intent(this, TutorialZ.class);
        startActivity(intent);
    }

}
