package schutzengel.com.safelifemonitor.Datenbank;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import schutzengel.com.safelifemonitor.GUI.HauptActivity;
import schutzengel.com.safelifemonitor.R;

public class ApplikationEinstellungen {
    public static final String TabellenName = "AnwendungsEinstellungen";
    public static final String col_Schwellwert = "SchwellwertBewegungssensor";
    public static final String col_MaxInactive = "MaximaleAnzahlInaktiveBewegungen";
    public static final String col_Intervall = "MonitorServiceIntervalInMillisekunden";
    public static final String col_MonitorEnabled = "MonitorEnabled";
    public static final String col_Zeit1Von = "Zeit1Von";
    public static final String col_Zeit2Von = "Zeit2Von";
    public static final String col_Zeit3Von = "Zeit3Von";
    public static final String col_Zeit4Von = "Zeit4Von";
    public static final String col_Zeit1Bis = "Zeit1Bis";
    public static final String col_Zeit2Bis = "Zeit2Bis";
    public static final String col_Zeit3Bis = "Zeit3Bis";
    public static final String col_Zeit4Bis = "Zeit4Bis";
    public static final String col_BenutzerName = "BenutzerName";
    protected int schwellwertBewegungssensor = 1;
    protected int maximaleAnzahlInaktiveBewegungen = 1800; // nach 30 min geht der alarm los
    protected int monitorServiceIntervalInMillisekunden = 1000; // jede sekunde
    protected int intervallSmsBenachrichtigungInMillisekunden = 300000; // nach 5 min wird der nächste Kontakt benachrichtigt
    protected Boolean istMonitorAktiv = true;
    protected ArrayList<String> zeiten = new ArrayList<>();
    protected ArrayList<Long> zeitenInSekunden = new ArrayList<>();
    protected String benutzerName = HauptActivity.context.getString(R.string.StandardBenutzername);

    /**
     * Holen des Schwellwerts des Bewegungssensors
     *
     * @return schwellwert
     */
    public int getSchwellwertBewegungssensor() {
        return this.schwellwertBewegungssensor;
    }

    /**
     * Setzen des Schwellwerts des Bewegungssensors
     *
     * @param schwellwert
     */
    public void setSchwellwertBewegungssensor(int schwellwert) {
        this.schwellwertBewegungssensor = schwellwert;
    }

    /**
     * Holen des Werts für die Maximale Anzahl Inaktiver Bewegungen
     *
     * @return Maximale Anzahl Inaktiver Bewegungen
     */
    public int getMaximaleAnzahlInaktiveBewegungen() {
        return this.maximaleAnzahlInaktiveBewegungen;
    }

    /**
     * Setzen des WErts für die Maximale Anzahl Inaktiver Bewegungen
     *
     * @param anzahl
     */
    public void setMaximaleAnzahlInaktiveBewegungen(int anzahl) {
        this.maximaleAnzahlInaktiveBewegungen = anzahl;
    }

    /**
     * Holen des Werts für den ServiceIntervall in Millisekunden (Standard 1 Sekunde / 1000ms)
     *
     * @return Monitor Service Intervall
     */
    public int getMonitorServiceInterval() {
        return this.monitorServiceIntervalInMillisekunden;
    }

    /**
     * Setzen des Monitor Service Intervalls in Millisekunden
     *
     * @param millisekunden
     */
    public void setMonitorServiceInterval(int millisekunden) {
        this.monitorServiceIntervalInMillisekunden = millisekunden;
    }

    /**
     * Holen des Benutzernamens
     *
     * @return Benutzername
     */
    public String getBenutzerName() {
        return this.benutzerName;
    }

    /**
     * Setzen des Benutzernamens
     *
     * @param BenutzerName
     */
    public void setBenutzerName(String BenutzerName) {
        this.benutzerName = BenutzerName;
    }

    /**
     * Holen der Eingebenenen Überwachungszeiten
     *
     * @return Überwachungszeiten
     */
    public ArrayList<String> getZeiten() {
        return this.zeiten;
    }

    /**
     * Setzen der Überwachungszeiten
     *
     * @param zeiten
     */
    public void setZeiten(ArrayList<String> zeiten) {
        this.zeiten = zeiten;
        this.zeitenInSekunden.clear();
        Calendar calendar = Calendar.getInstance();
        for (String zeit : this.zeiten) {
            String[] elements = zeit.split(":", 2);
            Integer hours = Integer.parseInt(elements[0]);
            Integer minutes = Integer.parseInt(elements[1]);
            Date today = new Date();
            today.setHours(hours);
            today.setMinutes(minutes);
            today.setSeconds(0);
            calendar.setTime(today);
            this.zeitenInSekunden.add(calendar.getTimeInMillis());
        }
    }

    /**
     * Holen des Wertes, ob das Überwachen Aktiv ist
     *
     * @return Monitor Aktiv?
     */
    public Boolean getMonitorAktiv() {
        return this.istMonitorAktiv;
    }

    /**
     * Setzen des Wertes, ob das Überwachen Aktiv ist
     *
     * @param aktiv
     */
    public void setMonitorAktiv(int aktiv) {
        if (aktiv == 1) {
            this.istMonitorAktiv = true;
        } else {
            this.istMonitorAktiv = false;
        }
    }

    /**
     * Hole Sekunden der Zeit 1 (Von)
     *
     * @return Sekunden Zeit 1 (Von)
     */
    public long getSekundenZeit1Von() {
        return this.zeitenInSekunden.get(0);
    }

    /**
     * Hole Sekunden der Zeit 1 (Bis)
     *
     * @return Sekunden Zeit 1 (Bis)
     */
    public long getSekundenZeit1Bis() {
        return this.zeitenInSekunden.get(1);
    }

    /**
     * Hole Sekunden der Zeit 2 (Von)
     *
     * @return Sekunden Zeit 2 (Von)
     */
    public long getSekundenZeit2Von() {
        return this.zeitenInSekunden.get(2);
    }

    /**
     * Hole Sekunden der Zeit 2 (Bis)
     *
     * @return Sekunden Zeit 2 (Bis)
     */
    public long getSekundenZeit2Bis() {
        return this.zeitenInSekunden.get(3);
    }

    /**
     * Hole Sekunden der Zeit 3 (Von)
     *
     * @return Sekunden Zeit 3 (Von)
     */
    public long getSekundenZeit3Von() {
        return this.zeitenInSekunden.get(4);
    }

    /**
     * Hole Sekunden der Zeit 3 (Bis)
     *
     * @return Sekunden Zeit 3 (Bis)
     */
    public long getSekundenZeit3Bis() {
        return this.zeitenInSekunden.get(5);
    }

    /**
     * Hole Sekunden der Zeit 4 (Von)
     *
     * @return Sekunden Zeit 4 (Von)
     */
    public long getSekundenZeit4Von() {
        return this.zeitenInSekunden.get(6);
    }

    /**
     * Hole Sekunden der Zeit 4 (Bis)
     *
     * @return Sekunden Zeit 4 (Bis)
     */
    public long getSekundenZeit4Bis() {
        return this.zeitenInSekunden.get(7);
    }

    /**
     * Holen des Wertes für den Intervall der SMS Benachrichtigungen
     *
     * @return Intervall der SMS Benachrichtigungen
     */
    public int getIntervallSmsBenachrichtigung() {
        return intervallSmsBenachrichtigungInMillisekunden;
    }

    /**
     * Setzen des WErtes für den Intervall der SMS Benachrichtigungen
     *
     * @param Millisekunden
     */
    public void setIntervallSmsBenachrichtigung(int Millisekunden) {
        this.intervallSmsBenachrichtigungInMillisekunden = Millisekunden;
    }

    /**
     * Abrufen des Textes, der als SMS verschickt werden Soll
     *
     * @return SMS Text
     */
    public String getSmsBenachrichtigungText() {
        StringBuilder Benachrichtigung = new StringBuilder();
        Benachrichtigung.append("SafeLife konnte keine Verbindung zu ");
        Benachrichtigung.append(this.benutzerName);
        Benachrichtigung.append(" herstellen. Du bist bei SafeLife als sein Notfallkontakt eingetragen. Bitte kümmere dich um ");
        Benachrichtigung.append(this.benutzerName);
        Benachrichtigung.append(" . Falls du diese Nachricht siehst, antworte mit 'OK'.");
        return Benachrichtigung.toString();
    }
}