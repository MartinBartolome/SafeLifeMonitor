package schutzengel.com.safelifemonitor.Core.DateTime;

public class Converter {
    public static long getEpochTimestam() {
        return System.currentTimeMillis() / 1000L;
    }
}
