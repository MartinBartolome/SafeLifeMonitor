package schutzengel.com.safelifemonitor;

import java.sql.Time;
import java.util.ArrayList;

public class ApplikationEinstellungen {

    public static final String tableName = "Application";
    public static final String col_Schwellwert= "SchwellwertBewegungssensor";
    public static final String col_MaxInactive = "MaximaleAnzahlInaktiveBewegungen";
    public static final String col_Intervall = "MonitorServiceIntervalInMillisekunden";
    public static final String col_Time1From = "Time1From";
    public static final String col_Time2From = "Time2From";
    public static final String col_Time3From = "Time3From";
    public static final String col_Time4From = "Time4From";
    public static final String col_Time1To = "Time1To";
    public static final String col_Time2To = "Time2To";
    public static final String col_Time3To = "Time3To";
    public static final String col_Time4To = "Time4To";

    protected int schwellwertBewegungssensor = 0;
    protected int maximaleAnzahlInaktiveBewegungen = 10;
    protected int monitorServiceIntervalInMillisekunden = 1000;
    ArrayList<String> Times = new ArrayList<>();

    public enum Field {
        SchwellwertBewegungssensor,
        MaximaleAnzahlInaktiveBewegungen,
        MonitorServiceIntervalInMillisekunden,
    }

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
    public void setTimes(ArrayList<String> times)
    {
        this.Times = times;
    }
    public ArrayList<String> getTimes()
    {
        return Times;
    }
}