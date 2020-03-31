package schutzengel.com.safelifemonitor.Sensors;

public abstract class Driver implements IDriver {
    protected Driver()  {
    }

    public abstract Measurement read();
}
