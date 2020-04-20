package schutzengel.com.safelifemonitor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Bewegungssensor implements SensorEventListener {
    Sensor ACCELEROMETER;

    float oldX = 0;
    float oldY = 0;
    float oldZ = 0;
    float newX = 0;
    float newY = 0;
    float newZ = 0;

    public Boolean wurdeBewegt(long schwellwert) {
        SensorManager sensorManager = (SensorManager) HauptActivity.context.getSystemService(Context.SENSOR_SERVICE);

        ACCELEROMETER = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, ACCELEROMETER, SensorManager.SENSOR_DELAY_NORMAL);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            if(oldX +schwellwert > newX || oldX - schwellwert < newX || oldY +schwellwert > newY || oldY - schwellwert < newY || oldZ +schwellwert > newZ || oldZ - schwellwert < newZ)
            {
                oldX = newX;
                oldY = newY;
                oldZ = newZ;
                return true;
            }
            else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        newX = event.values[0];
        newY = event.values[1];
        newZ = event.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}