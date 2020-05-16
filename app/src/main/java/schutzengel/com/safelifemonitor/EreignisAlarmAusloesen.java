package schutzengel.com.safelifemonitor;

public class EreignisAlarmAusloesen extends Ereignis {
    /**
     * Lesen des eindeutigen Ereignisidentifiers
     *
     * @return EventIdentifier
     */
    @Override
    public EventIdentifier getIdentifier() {
        return EventIdentifier.AlarmAusloesen;
    }
}