package schutzengel.com.safelifemonitor;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import java.util.Timer;

public class MonitorService extends Service {
    private int numberOfInvalidMeasurements = 0;
    private Timer timer = null;
    private Messenger messenger = null;
    private IBinder binder = null;

    public class Binder extends android.os.Binder {
        public MonitorService getMonitorService() {
            return MonitorService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.binder = new Binder();
        this.timer = new Timer("myTimer");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.messenger = null;
        this.timer.cancel();
        this.timer = null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        this.timer.scheduleAtFixedRate(new TimerTask(), 0, Datenbank.getInstance().getApplikationEinstellungen().getMonitorServiceInterval());
        return this.binder;
    }

    private void sende(Ereignis ereignis) {
        try {
            this.messenger.send(ereignis.toMessage());
        } catch (RemoteException e) {
        }
    }

    private class TimerTask extends java.util.TimerTask {
        public void run() {
            Log.d("TimerTask", "Timer expired");
            if (shallReadoutSensor()) {
                if (!readoutSensor()) {
                   sende(new EreignisAlarmAusloesen());
                }
            }
            if (shallPingServer()) {
                pingServer();
            }
        }
    };

    private Boolean shallReadoutSensor() {
        // Check suspended time
        return true;
    }

    private Boolean readoutSensor() {
        /*
        try {
            if (this.sensor.isMeasuring(this.applikationEinstellungen.getSchwellwertBewegungssensor())) {
                this.numberOfInvalidMeasurements = 0;
            } else {
                return (this.numberOfInvalidMeasurements++ < this.applikationEinstellungen.getMaximaleAnzahlInaktiveBewegungen());
            }
        } catch (Exception exception) {
            abort(new EventSensorException(EventSensorException.Exception.ReadFailure));
        }

         */
        return true;
    }

    private Boolean shallPingServer() {
        // Check suspended time

        return false;
    }

    private void pingServer() {
        /*
        // To do...
        EventServerPingState event = new EventServerPingState(EventServerPingState.State.Failed);
        // notify(event);
        */
    }


    private Boolean readShortMessages() {
        /*
        // To do...
        Message message = this.smsClient.receive();
        // Parse content...
        Boolean isSuccess = false;
        // Success
        if (isSuccess) {
            // Get contact
            NotfallKontakt notfallKontakt = this.database.getContactProperties(message.getSubscriberNumber());
            EreignisAlarmAufheben event = new EreignisAlarmAufheben(notfallKontakt.getPrioritaet().toString(), message.getContent());
            notify(event);
            return true;
        }
        */
        return false;
    }
}
