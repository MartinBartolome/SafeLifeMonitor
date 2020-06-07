package schutzengel.com.safelifemonitor.service;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.Calendar;
import java.util.Timer;

import schutzengel.com.safelifemonitor.bewegungssensor.Bewegungssensor;
import schutzengel.com.safelifemonitor.datenbank.ApplikationEinstellungen;
import schutzengel.com.safelifemonitor.datenbank.Datenbank;
import schutzengel.com.safelifemonitor.datenbank.NotfallKontakt;
import schutzengel.com.safelifemonitor.smsclient.SmsClient;

@SuppressWarnings({"ALL", "SameReturnValue"})
public class MonitorService extends Service {
    public enum Zustand {
        Undefiniert,
        Ueberwachen,
        Alarmieren
    }

    public int anzahlInaktiveBewegungen = 0;
    private int tickZaehler = 0;
    private NotfallKontakt.Prioritaet prioritaetNotfallkontakt = NotfallKontakt.Prioritaet.Prioritaet_1;
    private Timer timer = null;
    private Messenger messenger = null;
    private IBinder binder = null;
    private Bewegungssensor bewegungssensor = null;
    private ApplikationEinstellungen applikationsEinstellungen = null;
    public Zustand zustand = Zustand.Undefiniert;

    public class Binder extends android.os.Binder {
        public MonitorService getMonitorService() {
            return MonitorService.this;
        }
    }

    /**
     * Objekte werden Initialisiert beim Erstellen des Services
     */
    @Override
    public void onCreate() {
        super.onCreate();
        this.binder = new Binder();
        this.timer = new Timer("myTimer");
        this.bewegungssensor = new Bewegungssensor(this);
    }

    /**
     * Objekte werden zerstört beim Zerstören des Services
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.zustand = Zustand.Undefiniert;
        this.messenger = null;
        this.timer.cancel();
        this.timer = null;
    }

    /**
     * Beim Start werden die Einstellungen geladen, der Status auf Überrwachen gesetzt und die Tickrate des Timers gesetzt
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        this.messenger = (Messenger) (extras.get("Messenger"));
        this.applikationsEinstellungen = Datenbank.getInstanz(getApplicationContext()).getApplikationsEinstellungen();
        transitionUeberwachen();
        this.timer.scheduleAtFixedRate(new TimerTask(), 0, Datenbank.getInstanz(getApplicationContext()).getApplikationsEinstellungen().getMonitorServiceInterval());
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * Monitor Service wird gebunden
     *
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    /**
     * Der Alarm wird ausgelöst und ein Ereignis gesendet
     */
    private void alarmAusloesen() {
        transitionAlarmieren();
        EreignisAlarmAusloesen ereignis = new EreignisAlarmAusloesen();
        sende(ereignis);
        Log.d("MonitorService", "Alarm wurde ausgelöst");
    }

    /**
     * Der Alarm wird aufgehoben und ein Ereignis gesendet
     */
    private void alarmAufheben() {
        transitionUeberwachen();
        EreignisAlarmAufheben ereignis = new EreignisAlarmAufheben();
        sende(ereignis);
        Log.d("MonitorService", "Alarm wurde aufgehoben");
    }

    /**
     * Ein Ereignis wird gesendet
     *
     * @param ereignis
     */
    private void sende(Ereignis ereignis) {
        try {
            this.messenger.send(ereignis.toMessage());
        } catch (RemoteException e) {
            Log.e("MonitorService", "Fehler beim senden des Ereignis. Fehlermeldung: " + e.getMessage());
        }
    }

    /**
     * Diese methode wird bei jedem Tick aufgerufen und anhand des Zustandes wird eine Bestimmte Methode aufgerufen
     */
    private class TimerTask extends java.util.TimerTask {
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void run() {
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

    /**
     * Der Zustand wird auf Überwachen gewechselt
     */
    private void transitionUeberwachen() {
        this.zustand = Zustand.Ueberwachen;
        this.anzahlInaktiveBewegungen = 0;
        this.tickZaehler = 0;
        Log.d("MonitorService","Zustand = Alarmieren");
    }


    /**
     * Der Zustand wird auf Alarmieren gesetzt.
     */
    private void transitionAlarmieren() {
        this.zustand = Zustand.Alarmieren;
        this.tickZaehler = 0;
        this.prioritaetNotfallkontakt = NotfallKontakt.Prioritaet.Prioritaet_1;
        Log.d("MonitorService","Zustand = Überwachen");
    }

    /**
     * Beim Überwachen werden zuerst die ApplikationsEinstellungen neu geladen (es könnte sich etwas geändert haben)
     * Anschliessend wird geprüft, ob sich das Gerät grade erst bewegt hat. Wenn dies nicht der Fall war, wird geschaut, ob sich dieses eine halbe stunde lang nicht
     * mehr Bewegt hat. Wenn das der Fall ist, wird der Alarm ausgelöst
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onUeberwachen() {
        this.applikationsEinstellungen = Datenbank.getInstanz(getApplicationContext()).getApplikationsEinstellungen();

        Log.d("MonitorService","istInMonitorZeitraum = " +istInMoitorZeitraum());
        // Wurde Geraet bewegt?
        Log.d("MonitorService", "Anzahl der Inaktiven Bewegungen: " + this.anzahlInaktiveBewegungen);
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

    /**
     * Überprüfung, ob die Aktuelle Zeit im Festgelegten Monitorzeitraum ist.
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Boolean istInMoitorZeitraum() {
        if (!this.applikationsEinstellungen.getMonitorAktiv()) {
            return false;
        }
        // 30 Sekunden Aufloesung genuegt
        if (0 == (this.tickZaehler % 30)) {
            return false;
        }
        return (istImMonitorZeitraum(this.applikationsEinstellungen.getSekundenZeit1Von(), this.applikationsEinstellungen.getSekundenZeit1Bis()) |
                istImMonitorZeitraum(this.applikationsEinstellungen.getSekundenZeit2Von(), this.applikationsEinstellungen.getSekundenZeit2Bis()) |
                istImMonitorZeitraum(this.applikationsEinstellungen.getSekundenZeit3Von(), this.applikationsEinstellungen.getSekundenZeit3Bis()) |
                istImMonitorZeitraum(this.applikationsEinstellungen.getSekundenZeit4Von(), this.applikationsEinstellungen.getSekundenZeit4Bis()));
    }

    /**
     * Überprüfung ob die übergebene Zeit im Monitor zeitraum ist
     *
     * @param zeitraumVonSekunden
     * @param zeitraumBisSekunden
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private Boolean istImMonitorZeitraum(final long zeitraumVonSekunden, final long zeitraumBisSekunden) {
        Calendar calendar = Calendar.getInstance();
        final long jetzt = calendar.getTimeInMillis();
        boolean case1 = zeitraumVonSekunden < jetzt;
        boolean case2 = zeitraumBisSekunden > jetzt;
        return case1 & case2;
    }

    /**
     * Wenn der Zustand auf Alarmieren gesetzt wurde, dann wird geschaut, ob die 5 Minuten zum versenden der SMS abgelaufen sind.
     * Ist dies der Fall, wird eine SMS an den jeweiligen Notfallkontakt versendet.
     * Während der 5 minuten, wird geprüft, ob das Gerät bewegt wurde.
     */
    private void onAlarmieren() {
        this.applikationsEinstellungen = Datenbank.getInstanz(getApplicationContext()).getApplikationsEinstellungen();
        // SMS senden?
        Log.d("MonitorService", "Aktueller Tick:" + this.tickZaehler);
        if ((this.applikationsEinstellungen.getIntervallSmsBenachrichtigung() / this.applikationsEinstellungen.getMonitorServiceInterval()) == this.tickZaehler) {
            this.tickZaehler = 0;
            NotfallKontakt notfallKontakt = Datenbank.getInstanz(getApplicationContext()).getNotfallKontakt(this.prioritaetNotfallkontakt);
            if (null != notfallKontakt) {
                SmsClient.senden(notfallKontakt.getAlarmTelefonNummer(), applikationsEinstellungen.getSmsBenachrichtigungText());
                this.prioritaetNotfallkontakt = NotfallKontakt.toPriority(this.prioritaetNotfallkontakt.ordinal() + 1);
            } else {
                this.prioritaetNotfallkontakt = NotfallKontakt.Prioritaet.Prioritaet_1;
            }
            return;
        }
        // Wurde Geraet bewegt?
        Log.d("MonitorService", "Anzahl der Inaktiven Bewegungen: " + this.anzahlInaktiveBewegungen);
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

    /**
     * Permanente prüfung mit dem Server wird 2021 eingefügt, wenn Infrastruktur bereit steht.
     *
     * @return
     */
    private Boolean funktionsPruefungServer() {
        // #MAIAL 21-APR-2020: Funkktion wird implementiert, sobald Server Teil bereit ist
        return true;
    }
}
