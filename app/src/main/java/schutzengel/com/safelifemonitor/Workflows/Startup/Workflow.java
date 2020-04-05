package schutzengel.com.safelifemonitor.Workflows.Startup;

import schutzengel.com.safelifemonitor.Core.Factory.Factory;

public class Workflow extends schutzengel.com.safelifemonitor.Core.Workflows.Workflow {
    @Override
    protected Boolean running() {
        Factory factory = Factory.getInstance();
        factory.create();
        factory.initialize();
        factory.startup();
        EventStartupCompleted event = new EventStartupCompleted();
        notify(event);
        return true;
    }
}