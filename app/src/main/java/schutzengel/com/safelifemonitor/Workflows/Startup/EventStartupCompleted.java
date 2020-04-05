package schutzengel.com.safelifemonitor.Workflows.Startup;

import schutzengel.com.safelifemonitor.Core.Definitions;
import schutzengel.com.safelifemonitor.Core.Observer.Event;

public class EventStartupCompleted extends Event {
    @Override
    public int getIdentifier() {
        return Definitions.EventIdentifier.StartupCompleted.ordinal();
    }
}