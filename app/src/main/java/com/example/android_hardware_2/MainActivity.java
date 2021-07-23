package com.example.android_hardware_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    Sensor sensor;
    SensorManager sensorManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,sensor,sensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    float xOld=0;
    float yOld=0;
    float zOld=0;
    float threaShold=3000;
    long oldTime=0;
    @Override
    public void onSensorChanged(SensorEvent event) {
        float x =event.values[0];
        float y =event.values[1];
        float z =event.values[2];
        long currentTime= System.currentTimeMillis();
        if ((currentTime-oldTime)>100){

            long TimeDiff=currentTime-oldTime;
            oldTime=currentTime;

            float speed=Math.abs(x+y+z-xOld-yOld-zOld)/TimeDiff *10000;
            if (speed>threaShold){
                Vibrator v=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(500);
                Toast.makeText(getApplicationContext(),"Shook", Toast.LENGTH_LONG).show();;
            }


        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}