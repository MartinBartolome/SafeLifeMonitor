package schutzengel.com.safelifemonitor.Workflows.Alarming;

import schutzengel.com.safelifemonitor.Core.Observer.IEvent;
import schutzengel.com.safelifemonitor.Database.ContactProperties;

public class EventAlarmConfirmation implements IEvent {
    protected ContactProperties contactProperties = null;
    protected String message = "";

    public EventAlarmConfirmation(ContactProperties contactProperties, String message) {
        this.contactProperties = contactProperties;
        this.message = message;
    }

    public ContactProperties getContactProperties() {
        return this.contactProperties;
    }

    public String getMessage() {
        return this.message;
    }
}