package schutzengel.com.safelifemonitor;

public class EreignisAlarmAusloesen extends Ereignis {
    @Override
    public EventIdentifier getIdentifier() {
        return EventIdentifier.AlarmAusloesen;
    }
}