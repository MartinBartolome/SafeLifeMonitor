package schutzengel.com.safelifemonitor.Sensors;

public class Measurement {
    private long value = 0;

    public enum Exception {
        Success,
        InitializationFailure,
        ReadFailure
    }
    private Exception exception = Exception.Success;

    public Measurement(long value) {
        this.value = value;
    }

    public Measurement(Exception exception) {
        this.exception = exception;
    }

    public long getValue() {
        return this.value;
    }

    public Exception getException() {
        return this.exception;
    }
}
