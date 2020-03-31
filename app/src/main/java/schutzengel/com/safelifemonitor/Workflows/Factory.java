package schutzengel.com.safelifemonitor.Workflows;

import schutzengel.com.safelifemonitor.Core.Factory.IFactory;

public class Factory implements IFactory {
    private enum Type {
        Startup,
        Alarming,
        Monitoring,
        // Must be last definition
        Maximal
    }
    private IWorkflow[] workflows = null;

    public Factory() {
    }

    public void create() {
        this.workflows = new IWorkflow[Type.Maximal.ordinal()];
        this.workflows[Type.Startup.ordinal()] = new schutzengel.com.safelifemonitor.Workflows.Startup.Workflow();
        this.workflows[Type.Alarming.ordinal()] = new schutzengel.com.safelifemonitor.Workflows.Alarming.Workflow();
        this.workflows[Type.Monitoring.ordinal()] = new schutzengel.com.safelifemonitor.Workflows.Monitoring.Workflow();
    }

    public void initialize() {
    }

    public void startup() {
    }

    public void shutdown() {
    }

    public schutzengel.com.safelifemonitor.Workflows.Alarming.Workflow getAlarming() {
        return (schutzengel.com.safelifemonitor.Workflows.Alarming.Workflow)(this.workflows[Type.Alarming.ordinal()]);
    }

    public schutzengel.com.safelifemonitor.Workflows.Monitoring.Workflow getMonitoring() {
        return (schutzengel.com.safelifemonitor.Workflows.Monitoring.Workflow)( this.workflows[Type.Monitoring.ordinal()]);
    }

    public schutzengel.com.safelifemonitor.Workflows.Startup.Workflow getStartup() {
        return (schutzengel.com.safelifemonitor.Workflows.Startup.Workflow)( this.workflows[Type.Startup.ordinal()]);
    }
}