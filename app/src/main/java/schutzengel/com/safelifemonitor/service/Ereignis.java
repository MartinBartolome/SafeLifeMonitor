package schutzengel.com.safelifemonitor.service;

import android.os.Message;

public abstract class Ereignis {
    /**
     * Lesen des eindeutigen Ereignisidentifiers
     *
     * @return EventIdentifier
     */
    abstract EventIdentifier getIdentifier();

    /**
     * Enumeration der Ereignisidentifier
     */
    public enum EventIdentifier {
        AlarmAusloesen,
        AlarmAufheben,
    }

    /**
     * Mapping der Enumeratoren zu Indexes fuer switch() Abfragen
     */
    static public final EventIdentifier[] EventIdentifierMap = {
        EventIdentifier.AlarmAusloesen,
        EventIdentifier.AlarmAufheben,
    };

    /**
     * Konvertierung zu einem Message Objekt
     */
    Message toMessage() {
        Message message = Message.obtain();
        message.what = (getIdentifier().ordinal());
        return message;
    }
}