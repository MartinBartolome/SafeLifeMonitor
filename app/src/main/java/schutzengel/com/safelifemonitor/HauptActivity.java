package schutzengel.com.safelifemonitor;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class HauptActivity extends AppCompatActivity {
    private Intent monitorServiceIntent = null;
    private MonitorService monitorService = null;

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
}
