package schutzengel.com.safelifemonitor.Service;

import schutzengel.com.safelifemonitor.Service.Ereignis;

public class EreignisAlarmAufheben extends Ereignis {
    /**
     * Lesen des eindeutigen Ereignisidentifiers
     *
     * @return EventIdentifier
     */
    @Override
    public EventIdentifier getIdentifier() {
        return EventIdentifier.AlarmAufheben;
    }
}