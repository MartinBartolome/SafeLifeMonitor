package schutzengel.com.safelifemonitor;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HauptActivity extends AppCompatActivity {
    private Intent monitorServiceIntent = null;
    private MonitorService monitorService = null;
    public static Context context;

    private ServiceConnection monitorServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MonitorService.Binder binder = (MonitorService.Binder)(service);
            monitorService = binder.getMonitorService();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != this.monitorServiceIntent) {
            stopService(this.monitorServiceIntent);
            this.monitorServiceIntent = null;
            this.monitorService = null;
        }
        if (null != this.monitorServiceConnection) {
            unbindService(this.monitorServiceConnection);
        }
        this.monitorServiceConnection = null;
    }

    protected Handler observer = new Handler() {
        public void handleMessage(Message message) {
            switch (Ereignis.EventIdentifierMap[message.what]) {
                case AlarmAufheben:
                    onEvent(new EreignisAlarmAufheben(message));
                    break;
                case AlarmAusloesen:
                    onEvent(new EreignisAlarmAusloesen());
                    break;
                default:
                    break;
            }
            super.handleMessage(message);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        context = this;
        setContentView(R.layout.main);
        SetContacts();
        // Start the service
        try {
            this.monitorServiceIntent = new Intent(this, MonitorService.class);
            this.monitorServiceIntent.putExtra("Messenger", new Messenger(this.observer));
            bindService(this.monitorServiceIntent, this.monitorServiceConnection, BIND_AUTO_CREATE);
            startService(this.monitorServiceIntent);
        } catch (Exception e) {
            String test = e.getMessage();
        }
    }

    private void onEvent(EreignisAlarmAufheben ereignisAlarmAufheben) {
        Toast.makeText(getApplicationContext(), ereignisAlarmAufheben.getText(), Toast.LENGTH_LONG).show();
    }

    private void onEvent(EreignisAlarmAusloesen event) {
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
            case R.id.ContactSettings:
                Intent ContactSettings = new Intent(this, schutzengel.com.safelifemonitor.NotfallKontaktActivity.class);
                this.startActivity(ContactSettings);
                return true;
            case R.id.ApplicationSettings:
                Intent AppSettings = new Intent(this, schutzengel.com.safelifemonitor.ApplikationEinstellungenActivity.class);
                this.startActivity(AppSettings);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void SetContacts()
    {
        ArrayList<NotfallKontakt> contacts = Datenbank.getInstance().getNotfallKontakte();
        if(contacts != null) {
            for (NotfallKontakt contact : contacts) {
                TextView TextViewContact;
                switch (contact.getPrioritaet()) {
                    case Prioritaet_1:
                        TextViewContact = findViewById(R.id.ContactName1);
                        break;
                    case Prioritaet_2:
                        TextViewContact = findViewById(R.id.ContactName2);
                        break;
                    case Prioritaet_3:
                        TextViewContact = findViewById(R.id.ContactName3);
                        break;
                    case Prioritaet_4:
                        TextViewContact = findViewById(R.id.ContactName4);
                        break;
                    case Prioritaet_5:
                        TextViewContact = findViewById(R.id.ContactName5);
                        break;
                    default:
                        continue;
                }
                TextViewContact.setText(contact.getBeschreibung());
                TextViewContact.setCompoundDrawablesWithIntrinsicBounds(contact.getSmallDrawable(), 0, 0, 0);
            }
        }
    }
}
