 package schutzengel.com.safelifemonitor;

import android.os.Message;

public abstract  class Ereignis {
    abstract EventIdentifier getIdentifier();

    public enum EventIdentifier {
        AlarmAusloesen,
        AlarmAufheben,
    }

    static public EventIdentifier[] EventIdentifierMap = {
        EventIdentifier.AlarmAusloesen,
        EventIdentifier.AlarmAufheben,
    };

    Message toMessage()
    {
        Message message = Message.obtain();
        message.what = (getIdentifier().ordinal());
        return message;
    }
}