package schutzengel.com.safelifemonitor.Core.Observer;

import android.os.Message;

public interface IEvent {
    int getIdentifier();
    Message toMessage();
}