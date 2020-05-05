package schutzengel.com.safelifemonitor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import static android.hardware.Sensor.TYPE_ACCELEROMETER;

public class Bewegungssensor implements SensorEventListener {
    private Boolean wurdeBewegt = false;
    private long schwellwert = 10000;
    private long letzteBewegungX = 0;
    private long letzteBewegungY = 0;

    public Bewegungssensor(Context context) {
        SensorManager sensorManager = (SensorManager)(context.getSystemService(Context.SENSOR_SERVICE));
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_NORMAL);
    }

    public synchronized Boolean wurdeBewegt(final long schwellwert) {
        this.schwellwert = schwellwert;
        Boolean wurdeBewegt = this.wurdeBewegt;
        this.wurdeBewegt = false;
        return wurdeBewegt;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
/*
        StringBuilder builder = new StringBuilder();
        builder.append("onSensorChanged: [");
        builder.append(event.values[0]);
        builder.append("],[");
        builder.append(event.values[1]);
        builder.append("],[");
        builder.append(event.values[2]);
        builder.append("]");
        Log.d("Bewegungssensor", " " +  builder.toString());
*/
        try {
            long aktuelleBewegungX = (long)(event.values[0] * this.schwellwert);
            long aktuelleBewegungY = (long)(event.values[1] * this.schwellwert);
            this.wurdeBewegt = ((letzteBewegungX != aktuelleBewegungX) || (letzteBewegungY != aktuelleBewegungY));
            letzteBewegungX = (long)(event.values[0] * this.schwellwert);
            letzteBewegungY = (long)(event.values[1] * this.schwellwert);
        } catch (Exception e) {
            this.wurdeBewegt = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}