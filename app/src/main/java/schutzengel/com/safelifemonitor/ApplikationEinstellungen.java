package schutzengel.com.safelifemonitor;

public class ApplikationEinstellungen {
    protected int schwellwertBewegungssensor = 0;
    protected int maximaleAnzahlInaktiveBewegungen = 10;
    protected int monitorServiceIntervalInMillisekunden = 1000;

    public static String getTableName() {
        return "Application";
    }

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
}