package schutzengel.com.safelifemonitor.Sensors;

import schutzengel.com.safelifemonitor.Core.Factory.IFactory;
import schutzengel.com.safelifemonitor.Sensors.Drivers.Generic.Driver;

public class Factory implements IFactory {
    protected ISensor sensor = null;

    public Factory() {
    }

    public void create() {
        IDriver driver = new Driver();
        this.sensor = new Sensor(driver);
    }

    public void initialize() {
    }

    public void startup() {
    }

    public void shutdown() {
    }

    public ISensor getSensor() {
        return this.sensor;
    }
}