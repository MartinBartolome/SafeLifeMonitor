package schutzengel.com.safelifemonitor;

public class EreignisAlarmAufheben extends Ereignis {
    @Override
    public EventIdentifier getIdentifier() {
        return EventIdentifier.AlarmAufheben;
    }
}