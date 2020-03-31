package schutzengel.com.safelifemonitor.Workflows;

import schutzengel.com.safelifemonitor.Core.Observer.ISubject;

public interface IWorkflow extends ISubject {
    void start();
    void terminate();
}
