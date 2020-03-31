package schutzengel.com.safelifemonitor.Core.Factory;

public class Factory implements IFactory {
    private static Factory instance = null;

    private enum Type {
        Communications,
        Database,
        Sensors,
        Workflows,
        HMI,
        // Must be last definition
        Maximal
    }
    private IFactory[] factories = null;

    public static Factory getInstance() {
        if (null == instance) {
            instance = new Factory();
        }
        return instance;
    }

    private Factory() {
    }

    public void create() {
        this.factories = new IFactory[Type.Maximal.ordinal()];
        this.factories[Type.Communications.ordinal()] = new schutzengel.com.safelifemonitor.Communications.Factory();
        this.factories[Type.Database.ordinal()] = new schutzengel.com.safelifemonitor.Database.Factory();
        this.factories[Type.HMI.ordinal()] = new schutzengel.com.safelifemonitor.HMI.Factory();
        this.factories[Type.Sensors.ordinal()] = new schutzengel.com.safelifemonitor.Sensors.Factory();
        this.factories[Type.Workflows.ordinal()] = new schutzengel.com.safelifemonitor.Workflows.Factory();
        for (IFactory factory : this.factories) {
            factory.create();
        }
    }

    public void initialize() {
        for (IFactory factory : this.factories) {
            factory.initialize();
        }
    }

    public void startup() {
        for (IFactory factory : this.factories) {
            factory.startup();
        }
    }

    public void shutdown() {
        for (IFactory factory : this.factories) {
            factory.shutdown();
        }
    }

    public schutzengel.com.safelifemonitor.Communications.Factory getFactoryCommunications()
    {
        return (schutzengel.com.safelifemonitor.Communications.Factory)(this.factories[Type.Communications.ordinal()]);
    }

    public schutzengel.com.safelifemonitor.Database.Factory getFactoryDatabase() {
        return (schutzengel.com.safelifemonitor.Database.Factory)(this.factories[Type.Database.ordinal()]);
    }

    public schutzengel.com.safelifemonitor.HMI.Factory getFactoryHMI() {
        return (schutzengel.com.safelifemonitor.HMI.Factory)(this.factories[Type.HMI.ordinal()]);
    }

    public schutzengel.com.safelifemonitor.Sensors.Factory getFactorySensors() {
        return (schutzengel.com.safelifemonitor.Sensors.Factory)(this.factories[Type.Sensors.ordinal()]);
    }

    public schutzengel.com.safelifemonitor.Workflows.Factory getFactoryWorkflows() {
        return (schutzengel.com.safelifemonitor.Workflows.Factory)(this.factories[Type.Workflows.ordinal()]);
    }
}