package schutzengel.com.safelifemonitor.Workflows;

import schutzengel.com.safelifemonitor.Core.Observer.IEvent;

public class EventSensorException implements IEvent {
    public enum Exception {
        Undefined,
        Success,
        InitializationFailure,
        ReadFailure
    }
    protected Exception exception = Exception.Undefined;

    public EventSensorException(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return this.exception;
    }
}