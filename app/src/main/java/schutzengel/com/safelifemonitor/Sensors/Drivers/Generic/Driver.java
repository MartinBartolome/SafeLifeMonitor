package schutzengel.com.safelifemonitor.Sensors.Drivers.Generic;

import schutzengel.com.safelifemonitor.Sensors.Measurement;

public class Driver extends schutzengel.com.safelifemonitor.Sensors.Driver {
    public Driver() {
    }

    @Override
    public Measurement read() {
        Measurement measurement = new Measurement(9999);
        // To dol.....
        return measurement;
    }
}
