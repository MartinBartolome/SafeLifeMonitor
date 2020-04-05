package schutzengel.com.safelifemonitor.Database;

public class ApplicationProperties {
    protected int sensorMeasurementThreshold = 0;
    protected int maximalNumberOfInvalidMeasurements = 10;
    protected int monitoringIntervalInMilliseconds = 1000;

    public int getReadShortMessagesInterval() {
        return this.readShortMessagesIntervalInMilliseconds;
    }

    public void setReadShortMessagesInterval(int interval) {
        this.readShortMessagesIntervalInMilliseconds = interval;
    }

    protected int readShortMessagesIntervalInMilliseconds = 0;

    public static String getTableName() {
        return "Application";
    }

    public enum Field {
        SensorMeasurementThreshold,
        MaximalNumberOfInvalidMeasurements,
        MonitoringIntervalInMilliseconds,
        ReadShortMessagesIntervalInMilliseconds,

    }

    public int getSensorMeasurementThreshold() {
        return this.sensorMeasurementThreshold;
    }

    public void setSensorMeasurementThreshold(int threshold) {
        this.sensorMeasurementThreshold = threshold;
    }

    public int getMaximalNumberOfInvalidMeasurements() {
        return this.maximalNumberOfInvalidMeasurements;
    }

    public void setMaximalNumberOfInvalidMeasurements(int number) {
        this.maximalNumberOfInvalidMeasurements = number;
    }

    public int getMonitoringInterval() {
        return this.monitoringIntervalInMilliseconds;
    }

    public void setMonitoringInterval(int milliseconds) {
        this.monitoringIntervalInMilliseconds = milliseconds;
    }
}