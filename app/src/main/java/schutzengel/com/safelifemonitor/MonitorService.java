package schutzengel.com.safelifemonitor;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.RequiresApi;
import java.util.Timer;

public class MonitorService extends Service {
    public enum Zustand {
        Undefiniert,
        Ueberwachen,
        Alarmieren
    }

    private int anzahlInaktiveBewegungen = 0;
    private int tickZaehler = 0;
    private NotfallKontakt.Prioritaet prioritaetNotfallkontakt = NotfallKontakt.Prioritaet.Prioritaet_1;
    private Timer timer = null;
    private Messenger messenger = null;
    private IBinder binder = null;
    private Bewegungssensor bewegungssensor = null;
    private ApplikationEinstellungen applikationsEinstellungen = null;
    private Zustand zustand = Zustand.Undefiniert;
    private final int tagInSekunden = 86400;

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
        this.bewegungssensor = new Bewegungssensor(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.zustand = Zustand.Undefiniert;
        this.messenger = null;
        this.timer.cancel();
        this.timer = null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Bundle extras = intent.getExtras();
        this.messenger = (Messenger)(extras.get("Messenger"));
        this.applikationsEinstellungen = Datenbank.getInstanz().getApplikationsEinstellungen();
        transitionUeberwachen();
        this.timer.scheduleAtFixedRate(new TimerTask(), 0, Datenbank.getInstanz().getApplikationsEinstellungen().getMonitorServiceInterval());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    private void alarmAusloesen() {
        transitionAlarmieren();
        EreignisAlarmAusloesen ereignis = new EreignisAlarmAusloesen();
        sende(ereignis);
        Log.d("MonitorService","Alarm wurde ausgelöst");
    }

    private void alarmAufheben() {
        transitionUeberwachen();
        EreignisAlarmAufheben ereignis = new EreignisAlarmAufheben();
        sende(ereignis);
        Log.d("MonitorService","Alarm wurde aufgehoben");
    }

    private void sende(Ereignis ereignis) {
        try {
            this.messenger.send(ereignis.toMessage());
        } catch (RemoteException e) {
            Log.e("MonitorService", "Fehler beim senden des Ereignis. Fehlermeldung: " + e.getMessage());
        }
    }

    private class TimerTask extends java.util.TimerTask {
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void run() {
            Log.d("TimerTask", "Timer abgelaufen, Status [" + zustand.toString() + "]");
            switch (zustand) {
                case Alarmieren:
                    onAlarmieren();
                    break;
                case Ueberwachen:
                    onUeberwachen();
                    break;
                default:
                    break;
            }
            tickZaehler++;
        }
    }

    private void transitionUeberwachen() {
        this.zustand = Zustand.Ueberwachen;
        this.anzahlInaktiveBewegungen = 0;
        this.tickZaehler = 0;
    }

    private void transitionAlarmieren() {
        this.zustand = Zustand.Alarmieren;
        this.tickZaehler = 0;
        this.prioritaetNotfallkontakt = NotfallKontakt.Prioritaet.Prioritaet_1;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onUeberwachen() {
        this.applikationsEinstellungen = Datenbank.getInstanz().getApplikationsEinstellungen();
        // Wurde Geraet bewegt?
        Log.d("MonitorService","Anzahl der Inaktiven Bewegungen: " + this.anzahlInaktiveBewegungen);
        if (this.bewegungssensor.wurdeBewegt(this.applikationsEinstellungen.getSchwellwertBewegungssensor())) {
            this.anzahlInaktiveBewegungen = 0;
        } else {
            if (++this.anzahlInaktiveBewegungen >= this.applikationsEinstellungen.getMaximaleAnzahlInaktiveBewegungen()) {
                if (istInMoitorZeitraum()) {
                    alarmAusloesen();
                }
            }
        }
        if (!funktionsPruefungServer()) {
            alarmAusloesen();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Boolean istInMoitorZeitraum()
    {
        if (!this.applikationsEinstellungen.getMonitorAktiv()) {
            return false;
        }
        // 30 Sekunden Aufloesung genuegt
        if (0 == (this.tickZaehler % 30)) {
            return false;
        }
        final long jetzt = DateTime.getEpochTimestamp();
        final long mitternacht = DateTime.getTodayMidnightEpochTimestamp();
        return (!istImMonitorZeitraum(mitternacht, jetzt, this.applikationsEinstellungen.getSekundenZeit1Von(), this.applikationsEinstellungen.getSekundenZeit1Bis()) ||
            !istImMonitorZeitraum(mitternacht, jetzt, this.applikationsEinstellungen.getSekundenZeit2Von(), this.applikationsEinstellungen.getSekundenZeit2Bis()) ||
            !istImMonitorZeitraum(mitternacht, jetzt, this.applikationsEinstellungen.getSekundenZeit3Von(), this.applikationsEinstellungen.getSekundenZeit3Bis()) ||
            !istImMonitorZeitraum(mitternacht, jetzt, this.applikationsEinstellungen.getSekundenZeit4Von(), this.applikationsEinstellungen.getSekundenZeit4Bis()));
    }

    private Boolean istImMonitorZeitraum(final long mitternachtInSekunden, final long jetztSekunden, final long zeitraumVonSekunden, final long zeitraumBisSekunden) {
        int anzahlTage = 1;
        if (zeitraumVonSekunden <= zeitraumBisSekunden) {
            anzahlTage = 2;
        }
        return ((jetztSekunden >= (mitternachtInSekunden - (anzahlTage * this.tagInSekunden) + zeitraumVonSekunden)) && (jetztSekunden <= (mitternachtInSekunden - this.tagInSekunden + zeitraumBisSekunden)));
    }

    private void onAlarmieren() {
        this.applikationsEinstellungen = Datenbank.getInstanz().getApplikationsEinstellungen();
        // SMS senden?
        Log.d("MonitorService","Aktueller Tick:" + this.tickZaehler);
        if ((this.applikationsEinstellungen.getIntervallSmsBenachrichtigung() / this.applikationsEinstellungen.getMonitorServiceInterval()) == this.tickZaehler) {
            this.tickZaehler = 0;
            NotfallKontakt notfallKontakt = Datenbank.getInstanz().getNotfallKontakt(this.prioritaetNotfallkontakt);
            if (null != notfallKontakt) {
                SmsClient.senden(notfallKontakt.getAlarmTelefonNummer(), applikationsEinstellungen.getSmsBenachrichtigungText());
                this.prioritaetNotfallkontakt = NotfallKontakt.toPriority(this.prioritaetNotfallkontakt.ordinal() + 1);
            } else {
                this.prioritaetNotfallkontakt = NotfallKontakt.Prioritaet.Prioritaet_1;
            }
            return;
        }
        // Wurde Geraet bewegt?
        Log.d("MonitorService","Anzahl der Inaktiven Bewegungen: " + this.anzahlInaktiveBewegungen);
        if (this.bewegungssensor.wurdeBewegt(this.applikationsEinstellungen.getSchwellwertBewegungssensor())) {
            this.anzahlInaktiveBewegungen = 0;
            alarmAufheben();
            return;
        }
        // Wurde SMS Benarchrichtigung empfangen?
        if (SmsClient.wurdeEmpfangen()) {
            this.anzahlInaktiveBewegungen = 0;
            alarmAufheben();
            return;
        }
    }

    private Boolean funktionsPruefungServer() {
        // #MAIAL 21-APR-2020: Funkktion wird implementiert, sobald Server Teil bereit ist
        return true;
    }
}
