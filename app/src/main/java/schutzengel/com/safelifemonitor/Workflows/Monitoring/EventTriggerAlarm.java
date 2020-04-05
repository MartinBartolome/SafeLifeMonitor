package schutzengel.com.safelifemonitor.Workflows.Monitoring;

import schutzengel.com.safelifemonitor.Core.Definitions;
import schutzengel.com.safelifemonitor.Core.Observer.Event;

public class EventTriggerAlarm extends Event {
    @Override
    public int getIdentifier() {
        return Definitions.EventIdentifier.AlarmTrigger.ordinal();
    }
}