package schutzengel.com.safelifemonitor.Workflows;

import schutzengel.com.safelifemonitor.Core.Observer.IEvent;

public class EventDatabaseException implements IEvent {
    public enum Exception {
        Undefined,
        Success,
        OpenDatabaseFailure,
        ReadContactsPropertiesFailure,
        WriteContactsPropertiesFailure,
        ReadApplicationPropertiesFailure,
        WriteApplicationPropertiesFailure
    }
    protected Exception exception = Exception.Undefined;

    public EventDatabaseException(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return this.exception;
    }
}