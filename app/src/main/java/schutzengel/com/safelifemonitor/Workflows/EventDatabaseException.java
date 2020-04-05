package schutzengel.com.safelifemonitor.Workflows;

import android.os.Bundle;
import android.os.Message;
import schutzengel.com.safelifemonitor.Core.Definitions;
import schutzengel.com.safelifemonitor.Core.Observer.Event;

public class EventDatabaseException extends Event {
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

    protected enum Key {
        Exception,
    }

    public EventDatabaseException(Message message) {
        Bundle bundle = message.getData();
        this.exception = Exception.valueOf(bundle.getString(Key.Exception.toString()));
    }

    public EventDatabaseException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public int getIdentifier() {
        return Definitions.EventIdentifier.DatabaseException.ordinal();
    }

    public Exception getException() {
        return this.exception;
    }

    @Override
    public Message toMessage() {
        Message message = super.toMessage();
        Bundle bundle = new Bundle();
        bundle.putString(Key.Exception.toString(), this.exception.toString());
        message.setData(bundle);
        return message;
    }
}