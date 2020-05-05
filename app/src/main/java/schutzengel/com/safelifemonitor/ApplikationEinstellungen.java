package schutzengel.com.safelifemonitor;

import java.util.ArrayList;
import java.util.Iterator;

public class ApplikationEinstellungen {
    public static final String tableName = "Application";
    public static final String col_Schwellwert= "SchwellwertBewegungssensor";
    public static final String col_MaxInactive = "MaximaleAnzahlInaktiveBewegungen";
    public static final String col_Intervall = "MonitorServiceIntervalInMillisekunden";
    public static final String col_MonitorEnabled = "MonitorEnabled";
    public static final String col_Time1From = "Time1From";
    public static final String col_Time2From = "Time2From";
    public static final String col_Time3From = "Time3From";
    public static final String col_Time4From = "Time4From";
    public static final String col_Time1To = "Time1To";
    public static final String col_Time2To = "Time2To";
    public static final String col_Time3To = "Time3To";
    public static final String col_Time4To = "Time4To";
    public static final String col_UserName = "UserName";
    protected int schwellwertBewegungssensor = 1;
    protected int maximaleAnzahlInaktiveBewegungen = 1800000; // nach 30 min geht der alarm los
    protected int monitorServiceIntervalInMillisekunden = 1000; // jede sekunde
    protected int intervallSmsBenachrichtigungInMillisekunden = 300000; // nach 5 min wird der nächste Kontakt benachrichtigt
    protected  Boolean istMonitorAktiv = true;
    protected ArrayList <String> zeiten = new ArrayList<>();
    protected ArrayList <Integer> zeitenInSekunden = new ArrayList<>();
    protected String userName = "Rüstiger Rentner";

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

    public void setMonitorServiceInterval(int milliseconds) {
        this.monitorServiceIntervalInMillisekunden = milliseconds;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
	
    public void setZeiten(ArrayList<String> zeiten)
    {
        this.zeiten = zeiten;
        this.zeitenInSekunden.clear();
        for (String zeit : this.zeiten) {
            String[] elements = zeit.split(":", 2);
            Integer hours = Integer.parseInt(elements[0]);
            Integer minutes = Integer.parseInt(elements[1]);
            this.zeitenInSekunden.add((hours * 60 + minutes) * 60);
        }
    }

    public ArrayList<String> getZeiten()
    {
        return this.zeiten;
    }

    public Boolean istMonitorAktiv() {
        return this.istMonitorAktiv;
    }
    public void setMonitorEnabled(int enabled) {
        if(enabled == 1) {
            this.istMonitorAktiv = true;
        }
        else
        {
            this.istMonitorAktiv = false;
        }
    }
    
    public int getSekundenTime1From() {
        return this.zeitenInSekunden.get(0);
    }

    public  int getSekundenTime1To() {
        return this.zeitenInSekunden.get(1);
    }

    public  int getSekundenTime2From() {
        return this.zeitenInSekunden.get(2);
    }

    public  int getSekundenTime2To() {
        return this.zeitenInSekunden.get(3);
    }

    public  int getSekundenTime3From() {
        return this.zeitenInSekunden.get(4);
    }

    public  int getSekundenTime3To() {
        return this.zeitenInSekunden.get(5);
    }

    public  int getSekundenTime4From() {
        return this.zeitenInSekunden.get(6);
    }

    public  int getSekundenTime4To() {
        return this.zeitenInSekunden.get(7);
    }

    public int getIntervallSmsBenachrichtigung() {
        return intervallSmsBenachrichtigungInMillisekunden;
    }
    public void setIntervallSmsBenachrichtigung(int milliseconds) {
        this.intervallSmsBenachrichtigungInMillisekunden = milliseconds;
    }

    public String getSmsBenachrichtigungText() {
        StringBuilder builder = new StringBuilder();
        builder.append("SafeLife konnte keine Verbindung zu ");
        builder.append(this.userName);
        builder.append(" herstellen. Du bist bei SafeLife als sein Notfallkontakt eingetragen. Bitte kümmere dich um ");
        builder.append(this.userName);
        builder.append(" . Falls du diese Nachricht siehst, antworte mit 'OK'.");
        return builder.toString();
    }
}