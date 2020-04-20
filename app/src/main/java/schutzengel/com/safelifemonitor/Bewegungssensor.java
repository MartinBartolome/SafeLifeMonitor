package schutzengel.com.safelifemonitor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Bewegungssensor implements SensorEventListener {
    Sensor ACCELEROMETER;

    public Boolean wurdeBewegt(long schwellwert) {
        SensorManager sensorManager = (SensorManager) HauptActivity.context.getSystemService(Context.SENSOR_SERVICE);

        ACCELEROMETER = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, ACCELEROMETER, SensorManager.SENSOR_DELAY_NORMAL);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){

        } else {
            // Failure! No ACCELEROMETER.
        }
        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float X = event.values[0];
        float Y = event.values[1];
        float Z = event.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}