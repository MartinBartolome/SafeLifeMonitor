package schutzengel.com.safelifemonitor;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;

public class MonitorService extends Service {
    private int numberOfInvalidMeasurements = 0;
    private Timer timer = null;
    private Messenger messenger = null;
    private IBinder binder = null;
    private Bewegungssensor bewegungssensor = null;
    private ApplikationEinstellungen applikationEinstellungen = null;

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
        this.bewegungssensor = new Bewegungssensor();
        this.applikationEinstellungen = Datenbank.getInstance().getApplikationEinstellungen();
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
        this.applikationEinstellungen = Datenbank.getInstance().getApplikationEinstellungen();
        ArrayList<String> times = this.applikationEinstellungen.getTimes();

        Date CurrentDate = new Date();
        SimpleDateFormat TimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat DateFormat = new SimpleDateFormat("dd/MM/yyyy ");

        try {
            Date From = TimeFormat.parse(DateFormat.format(CurrentDate) + times.get(0));
            Date To = TimeFormat.parse(DateFormat.format(CurrentDate) + times.get(1));

            if(From.before(CurrentDate) && To.after(CurrentDate))
                return true;

            From = TimeFormat.parse(DateFormat.format(CurrentDate) + times.get(2));
            To = TimeFormat.parse(DateFormat.format(CurrentDate) + times.get(3));

            if(From.before(CurrentDate) && To.after(CurrentDate))
                return true;

            From = TimeFormat.parse(DateFormat.format(CurrentDate) + times.get(4));
            To = TimeFormat.parse(DateFormat.format(CurrentDate) + times.get(5));

            if(From.before(CurrentDate) && To.after(CurrentDate))
                return true;

            From = TimeFormat.parse(DateFormat.format(CurrentDate) + times.get(6));
            To = TimeFormat.parse(DateFormat.format(CurrentDate) + times.get(7));

            if(From.before(CurrentDate) && To.after(CurrentDate))
                return true;
        } catch (Exception e) {
        }

        return false;
    }

    private Boolean readoutSensor() {

        try {
            if (this.bewegungssensor.wurdeBewegt(this.applikationEinstellungen.getSchwellwertBewegungssensor())) {
                this.numberOfInvalidMeasurements = 0;
            } else {
                return (this.numberOfInvalidMeasurements++ < this.applikationEinstellungen.getMaximaleAnzahlInaktiveBewegungen());
            }
        } catch (Exception exception) {
            // ToDo
            //abort(new EventSensorException(EventSensorException.Exception.ReadFailure));
        }


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
