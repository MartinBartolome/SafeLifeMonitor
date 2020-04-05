package schutzengel.com.safelifemonitor.Sensors;

public abstract class Driver implements IDriver {
    public abstract Measurement read();
}
