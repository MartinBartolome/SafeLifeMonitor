package schutzengel.com.safelifemonitor.Workflows.Alarming;

import android.os.Bundle;
import android.os.Message;
import schutzengel.com.safelifemonitor.Core.Definitions;
import schutzengel.com.safelifemonitor.Core.Observer.Event;

public class EventAlarmConfirmation extends Event {
    protected String contactPropertiesKey = "";
    protected String message = "";

    protected enum Key {
        ContactPropertiesKey,
        Message
    }

    public EventAlarmConfirmation(Message message) {
        Bundle bundle = message.getData();
        this.contactPropertiesKey = bundle.getString(Key.ContactPropertiesKey.toString());
        this.message = bundle.getString(Key.Message.toString());
    }

    public EventAlarmConfirmation(String contactPropertiesKey, String message) {
        this.contactPropertiesKey = contactPropertiesKey;
        this.message = message;
    }

    public String getContactPropertiesKey() {
        return this.contactPropertiesKey;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public int getIdentifier() {
        return Definitions.EventIdentifier.AlarmConfirmation.ordinal();
    }

    @Override
    public Message toMessage() {
        Message message = super.toMessage();
        Bundle bundle = new Bundle();
        bundle.putString(Key.ContactPropertiesKey.toString(), this.contactPropertiesKey);
        bundle.putString(Key.Message.toString(), this.message);
        message.setData(bundle);
        return message;
    }
}