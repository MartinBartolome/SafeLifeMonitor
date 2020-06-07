package schutzengel.com.safelifemonitor.service;

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