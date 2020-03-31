package schutzengel.com.safelifemonitor.Sensors;

public interface ISensor {
    Boolean isMeasuring(long threshold);
}