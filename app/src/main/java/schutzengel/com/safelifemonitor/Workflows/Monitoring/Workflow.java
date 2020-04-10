package schutzengel.com.safelifemonitor.Workflows.Monitoring;

import schutzengel.com.safelifemonitor.Database.ApplicationProperties;
import schutzengel.com.safelifemonitor.Sensors.ISensor;
import schutzengel.com.safelifemonitor.Workflows.EventSensorException;
import schutzengel.com.safelifemonitor.Workflows.EventServerPingState;

public class Workflow extends schutzengel.com.safelifemonitor.Core.Workflows.Workflow {
    protected ApplicationProperties applicationProperties = null;
    protected ISensor sensor = null;
    protected int numberOfInvalidMeasurements = 0;

    @Override
    protected void prepare() {
        super.prepare();
        schutzengel.com.safelifemonitor.Core.Factory.Factory factory = schutzengel.com.safelifemonitor.Core.Factory.Factory.getInstance();
        this.applicationProperties = factory.getFactoryDatabase().getDatabase().getApplicationProperties(); //führt Thread onPrepare() aus
        this.sensor = factory.getFactorySensors().getSensor();
    }

    @Override
    protected Boolean running() {
        if (shallReadoutSensor()) {
            if (!readoutSensor()) {
                abort(new EventTriggerAlarm());
            }
        }
        if (shallPingServer()) {
            pingServer();
        }
        wait(this.applicationProperties.getMonitoringInterval());
        return false;
    }

    private Boolean shallReadoutSensor() {
        // Check suspended time
        return true;
    }

    private Boolean readoutSensor() {
        try {
            if (this.sensor.isMeasuring(this.applicationProperties.getSensorMeasurementThreshold())) {
                this.numberOfInvalidMeasurements = 0;
            } else {
                return (this.numberOfInvalidMeasurements++ < this.applicationProperties.getMaximalNumberOfInvalidMeasurements());
            }
        } catch (Exception exception) {
            abort(new EventSensorException(EventSensorException.Exception.ReadFailure));
        }
        return true;
    }

    private Boolean shallPingServer() {
        // Check suspended time

        return false;
    }

    private void pingServer() {
        // To do...
        EventServerPingState event = new EventServerPingState(EventServerPingState.State.Failed);
        // notify(event);
    }
}