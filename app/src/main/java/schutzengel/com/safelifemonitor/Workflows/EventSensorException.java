package schutzengel.com.safelifemonitor.Workflows;

import android.os.Bundle;
import android.os.Message;

import schutzengel.com.safelifemonitor.Core.Definitions;
import schutzengel.com.safelifemonitor.Core.Observer.Event;

public class EventSensorException extends Event {
    public enum Exception {
        Undefined,
        Success,
        InitializationFailure,
        ReadFailure
    }
    protected Exception exception = Exception.Undefined;

    protected enum Key {
        Exception,
    }

    @Override
    public int getIdentifier() {
        return Definitions.EventIdentifier.SensorException.ordinal();
    }

    public EventSensorException(Message message) {
        Bundle bundle = message.getData();
        this.exception = EventSensorException.Exception.valueOf(bundle.getString(EventSensorException.Key.Exception.toString()));
    }

    public EventSensorException(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return this.exception;
    }

    @Override
    public Message toMessage() {
        Message message = super.toMessage();
        Bundle bundle = new Bundle();
        bundle.putString(EventDatabaseException.Key.Exception.toString(), this.exception.toString());
        message.setData(bundle);
        return message;
    }
}