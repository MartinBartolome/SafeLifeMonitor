package schutzengel.com.safelifemonitor.Core.Observer;

import android.os.Message;

public abstract class Event implements IEvent {
    public abstract int getIdentifier();

    public Message toMessage() {
        Message message = Message.obtain();
        message.what = getIdentifier();
        return message;
    }
}
