package schutzengel.com.safelifemonitor.Core;

public class Definitions {

    public enum EventIdentifier {
        StartupCompleted,
        AlarmConfirmation,
        AlarmTrigger,
        DatabaseException,
        SensorException,
        ServerPingState
    }

    static public EventIdentifier[] EventIdentifierMap = {
        EventIdentifier.StartupCompleted,
        EventIdentifier.AlarmConfirmation,
        EventIdentifier.AlarmTrigger,
        EventIdentifier.DatabaseException,
        EventIdentifier.SensorException,
        EventIdentifier.ServerPingState
    };
}