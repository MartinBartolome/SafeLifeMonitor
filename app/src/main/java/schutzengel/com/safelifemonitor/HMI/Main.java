package schutzengel.com.safelifemonitor.HMI;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import schutzengel.com.safelifemonitor.Core.Definitions;
import schutzengel.com.safelifemonitor.Core.Factory.Factory;
import schutzengel.com.safelifemonitor.Database.ContactProperties;
import schutzengel.com.safelifemonitor.R;
import schutzengel.com.safelifemonitor.Workflows.Alarming.EventAlarmConfirmation;
import schutzengel.com.safelifemonitor.Workflows.EventServerPingState;
import schutzengel.com.safelifemonitor.Workflows.EventDatabaseException;
import schutzengel.com.safelifemonitor.Workflows.EventSensorException;
import schutzengel.com.safelifemonitor.Workflows.Monitoring.EventTriggerAlarm;
import schutzengel.com.safelifemonitor.Workflows.Startup.EventStartupCompleted;

import static schutzengel.com.safelifemonitor.Database.ContactProperties.Priority.Prority_1;

public class Main extends Activity {
    public static Context context;
    private boolean firstrun = true;
    @Override
    protected void onStart() {
        super.onStart();
        context = this;
        setContentView(R.layout.main);
        runService(schutzengel.com.safelifemonitor.Workflows.Startup.Workflow.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Settings:
                Intent settings = new Intent(this, schutzengel.com.safelifemonitor.HMI.ContactProperties.class);
                this.startActivity(settings);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        if(firstrun) {
            SetContacts();
            firstrun = false;
        }
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

    private void SetContacts()
    {
        ArrayList<ContactProperties> contacts = Factory.getInstance().getFactoryDatabase().getDatabase().getContacts();
        if(contacts != null) {
            for (ContactProperties contact : contacts) {
                TextView TextViewContact;
                switch (contact.getPriority()) {
                    case Prority_1:
                        TextViewContact = findViewById(R.id.ContactName1);
                        break;
                    case Prority_2:
                        TextViewContact = findViewById(R.id.ContactName2);
                        break;
                    case Prority_3:
                        TextViewContact = findViewById(R.id.ContactName3);
                        break;
                    case Prority_4:
                        TextViewContact = findViewById(R.id.ContactName4);
                        break;
                    case Prority_5:
                        TextViewContact = findViewById(R.id.ContactName5);
                        break;
                    default:
                        continue;
                }
                TextViewContact.setText(contact.getDescription());
                TextViewContact.setCompoundDrawablesWithIntrinsicBounds(contact.getDrawable(), 0, 0, 0);
            }
        }
    }
}
