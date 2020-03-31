package schutzengel.com.safelifemonitor.HMI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import schutzengel.com.safelifemonitor.Core.Observer.IEvent;
import schutzengel.com.safelifemonitor.Core.Observer.IObserver;
import schutzengel.com.safelifemonitor.R;
import schutzengel.com.safelifemonitor.Workflows.Alarming.EventAlarmConfirmation;
import schutzengel.com.safelifemonitor.Workflows.EventConnectionToServer;
import schutzengel.com.safelifemonitor.Workflows.EventDatabaseException;
import schutzengel.com.safelifemonitor.Workflows.EventSensorException;
import schutzengel.com.safelifemonitor.Workflows.Factory;
import schutzengel.com.safelifemonitor.Workflows.Monitoring.EventTriggerAlarm;

public class Main extends AppCompatActivity implements IObserver, IActivity {
    @Override
    public void initialize() {
        Factory factory = schutzengel.com.safelifemonitor.Core.Factory.Factory.getInstance().getFactoryWorkflows();
        factory.getStartup().register(this);
        factory.getMonitoring().register(this);
        factory.getAlarming().register(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    }

    @Override
    public void update(IEvent event) {
        switch (event.getClass().getSimpleName()) {
            case "EventAlarmConfirmation":
                onEvent((EventAlarmConfirmation)(event));
                break;
            case "EventTriggerAlarm":
                onEvent((EventTriggerAlarm)(event));
                break;
            case "EventConnectionToServer":
                onEvent((EventConnectionToServer)(event));
                break;
            case "EventDatabaseException":
                onEvent((EventDatabaseException)(event));
                break;
            case "EventSensorException":
                onEvent((EventSensorException)(event));
                break;
            default:
                break;
        }
    }

    private void onEvent(EventAlarmConfirmation event) {

    }

    private void onEvent(EventTriggerAlarm event) {

    }

    private void onEvent(EventConnectionToServer event) {

    }

    private void onEvent(EventDatabaseException event) {

    }

    private void onEvent(EventSensorException event) {

    }
}
