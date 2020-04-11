package schutzengel.com.safelifemonitor.HMI;

import android.os.Message;
import android.widget.Toast;
import schutzengel.com.safelifemonitor.Core.Definitions;
import schutzengel.com.safelifemonitor.Workflows.Alarming.EventAlarmConfirmation;
import schutzengel.com.safelifemonitor.Workflows.Monitoring.EventServerPingState;
import schutzengel.com.safelifemonitor.Workflows.EventDatabaseException;
import schutzengel.com.safelifemonitor.Workflows.EventSensorException;
import schutzengel.com.safelifemonitor.Workflows.Monitoring.EventTriggerAlarm;
import schutzengel.com.safelifemonitor.Workflows.Startup.EventStartupCompleted;

public class Main extends Activity {
    @Override
    protected void onStart() {
        super.onStart();
        runService(schutzengel.com.safelifemonitor.Workflows.Startup.Workflow.class);
    }

    @Override
    protected void update(Message message) {
        switch (Definitions.EventIdentifierMap[message.what]) {
            case StartupCompleted:
                onEvent(new EventStartupCompleted());
                break;
            case AlarmConfirmation:
                onEvent(new EventAlarmConfirmation(message));
                break;
            case AlarmTrigger:
                onEvent(new EventTriggerAlarm());
                break;
            case ServerPingState:
                onEvent(new EventServerPingState(message));
                break;
            case DatabaseException:
                onEvent(new EventDatabaseException(message));
                break;
            case SensorException:
                onEvent(new EventSensorException(message));
                break;
            default:
                break;
        }
    }

    private void onEvent(EventStartupCompleted event) {
        runService(schutzengel.com.safelifemonitor.Workflows.Monitoring.Workflow.class);
    }

    private void onEvent(EventAlarmConfirmation event) {
        Toast.makeText(getApplicationContext(), event.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void onEvent(EventTriggerAlarm event) {
        setVisible(true);
        runService(schutzengel.com.safelifemonitor.Workflows.Alarming.Workflow.class);
    }

    private void onEvent(EventServerPingState event) {
        Toast.makeText(getApplicationContext(), event.getState().toString(), Toast.LENGTH_LONG).show();
    }

    private void onEvent(EventDatabaseException event) {
        Toast.makeText(getApplicationContext(), event.getException().toString(), Toast.LENGTH_LONG).show();
    }

    private void onEvent(EventSensorException event) {
        Toast.makeText(getApplicationContext(), event.getException().toString(), Toast.LENGTH_LONG).show();
    }
}
