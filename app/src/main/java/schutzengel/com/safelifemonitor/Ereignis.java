 package schutzengel.com.safelifemonitor;

import android.os.Message;

public abstract  class Ereignis {
    abstract int getIdentifier();

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
        message.what = (getIdentifier());
        return message;
    }
}