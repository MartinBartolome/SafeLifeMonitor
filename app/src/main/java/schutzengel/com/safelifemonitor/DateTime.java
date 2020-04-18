package schutzengel.com.safelifemonitor;

public class DateTime {
    public static long getEpochTimestamp() {
        return System.currentTimeMillis() / 1000L;
    }
}
