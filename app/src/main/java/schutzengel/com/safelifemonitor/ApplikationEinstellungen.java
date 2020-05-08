package schutzengel.com.safelifemonitor;

import java.util.ArrayList;

public class ApplikationEinstellungen {
    public static final String TabellenName = "AnwendungsEinstellungen";
    public static final String col_Schwellwert= "SchwellwertBewegungssensor";
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
    protected int maximaleAnzahlInaktiveBewegungen = 1800000; // nach 30 min geht der alarm los
    protected int monitorServiceIntervalInMillisekunden = 1000; // jede sekunde
    protected int intervallSmsBenachrichtigungInMillisekunden = 300000; // nach 5 min wird der nächste Kontakt benachrichtigt
    protected  Boolean istMonitorAktiv = true;
    protected ArrayList <String> zeiten = new ArrayList<>();
    protected ArrayList <Integer> zeitenInSekunden = new ArrayList<>();
    protected String benutzerName = "Rüstiger Rentner";

    public int getSchwellwertBewegungssensor() {
        return this.schwellwertBewegungssensor;
    }
    public void setSchwellwertBewegungssensor(int schwellwert) {
        this.schwellwertBewegungssensor = schwellwert;
    }

    public int getMaximaleAnzahlInaktiveBewegungen() {
        return this.maximaleAnzahlInaktiveBewegungen;
    }
    public void setMaximaleAnzahlInaktiveBewegungen(int anzahl) {
        this.maximaleAnzahlInaktiveBewegungen = anzahl;
    }

    public int getMonitorServiceInterval() {
        return this.monitorServiceIntervalInMillisekunden;
    }
    public void setMonitorServiceInterval(int millisekunden) {
        this.monitorServiceIntervalInMillisekunden = millisekunden;
    }

    public String getBenutzerName() {
        return this.benutzerName;
    }
    public void setBenutzerName(String BenutzerName) {
        this.benutzerName = BenutzerName;
    }

    public ArrayList<String> getZeiten()
    {
        return this.zeiten;
    }
    public void setZeiten(ArrayList<String> zeiten)    {
        this.zeiten = zeiten;
        this.zeitenInSekunden.clear();
        for (String zeit : this.zeiten) {
            String[] elements = zeit.split(":", 2);
            Integer hours = Integer.parseInt(elements[0]);
            Integer minutes = Integer.parseInt(elements[1]);
            this.zeitenInSekunden.add((hours * 60 + minutes) * 60);
        }
    }

    public Boolean getMonitorAktiv() {
        return this.istMonitorAktiv;
    }
    public void setMonitorAktiv(int enabled) {
        if(enabled == 1) {
            this.istMonitorAktiv = true;
        }
        else
        {
            this.istMonitorAktiv = false;
        }
    }
    
    public int getSekundenZeit1Von() {
        return this.zeitenInSekunden.get(0);
    }
    public  int getSekundenZeit1Bis() {
        return this.zeitenInSekunden.get(1);
    }
    public  int getSekundenZeit2Von() {
        return this.zeitenInSekunden.get(2);
    }
    public  int getSekundenZeit2Bis() {
        return this.zeitenInSekunden.get(3);
    }
    public  int getSekundenZeit3Von() {
        return this.zeitenInSekunden.get(4);
    }
    public  int getSekundenZeit3Bis() {
        return this.zeitenInSekunden.get(5);
    }
    public  int getSekundenZeit4Von() {
        return this.zeitenInSekunden.get(6);
    }
    public  int getSekundenZeit4Bis() {
        return this.zeitenInSekunden.get(7);
    }

    public int getIntervallSmsBenachrichtigung() {
        return intervallSmsBenachrichtigungInMillisekunden;
    }
    public void setIntervallSmsBenachrichtigung(int milliseconds) {
        this.intervallSmsBenachrichtigungInMillisekunden = milliseconds;
    }

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