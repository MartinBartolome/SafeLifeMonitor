package schutzengel.com.safelifemonitor.Sensors;

public class Sensor implements ISensor {
    protected IDriver driver = null;
    protected Measurement previousMeasurement = null;

    public Sensor(IDriver driver) {
        this.driver = driver;
    }

    @Override
    public Boolean isMeasuring(long threshold) {
        Measurement measurement = this.driver.read();
        // To do run thecks....
        this.previousMeasurement = measurement;
        return false;
    }
}