package schutzengel.com.safelifemonitor.gui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import schutzengel.com.safelifemonitor.datenbank.Datenbank;
import schutzengel.com.safelifemonitor.datenbank.NotfallKontakt;
import schutzengel.com.safelifemonitor.service.MonitorService;
import schutzengel.com.safelifemonitor.R;
import schutzengel.com.safelifemonitor.service.Ereignis;

public class HauptActivity extends AppCompatActivity {

    private static final int SMS_SEND_PERMISSION_CODE = 100;
    private static final int SMS_READ_PERMISSION_CODE = 101;
    private static final int SMS_RECEIVE_PERMISSION_CODE = 102;
    private static final int READ_PHONE_STATE = 103;
    private Intent monitorServiceIntent = null;
    private MonitorService monitorService = null;
    public Context context = null;
    private boolean istAlarmGestartet = false;
    private MediaPlayer mediaPlayer = null;

    private ServiceConnection monitorServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MonitorService.Binder binder = (MonitorService.Binder) (service);
            monitorService = binder.getMonitorService();
            checkPermission(Manifest.permission.RECEIVE_SMS, SMS_RECEIVE_PERMISSION_CODE);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };

    /**
     * Beim Zerstören der App wird der monitorservice gestoppt
     */
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

    /**
     * Observer für Alarm.
     */
    @SuppressLint("HandlerLeak")
    protected final Handler observer = new Handler() {
        public void handleMessage(Message message) {
            onEvent((Ereignis.EventIdentifierMap[message.what]));
            super.handleMessage(message);
        }
    };

    /**
     * Beim Start der App werden die Kontakte Gesetzt
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();
        context = this;
        SetzeKontakte();
        Log.i("HauptActivity", "wurde gestartet");
    }

    /**
     * Beim erstellen der App wird dar Content gesetzt und der MonitorService gestartet
     *
     * @param savedInstanceState Gespeicherter Instanz Status
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.main);
        // Start the service
        try {
            this.monitorServiceIntent = new Intent(this, MonitorService.class);
            this.monitorServiceIntent.putExtra("Messenger", new Messenger(this.observer));
            bindService(this.monitorServiceIntent, this.monitorServiceConnection, BIND_AUTO_CREATE);
            startService(this.monitorServiceIntent);
            Log.i("HauptActivity", "wurde erstellt");
        } catch (Exception e) {
            Log.e("HauptActivity", "Fehler beim erstellen. Fehlermeldung: " + e.getMessage());
        }
    }

    /**
     * Der Mediaplayer wird gestartet oder gestoppt und der Sound wird auf Laut gestellt.
     *
     */
    private void onEvent(Ereignis.EventIdentifier e) {
        if(e == Ereignis.EventIdentifier.AlarmAufheben)
        {
            if (this.istAlarmGestartet) {
                this.mediaPlayer.stop();
                this.istAlarmGestartet = false;
                Log.i("HauptActivity", "Alarm wurde aufgehoben");
            }
        }
        else if(e == Ereignis.EventIdentifier.AlarmAusloesen)
        {
            if (!this.istAlarmGestartet) {
                this.mediaPlayer = MediaPlayer.create(context, R.raw.alarm);
                AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                for (int index = 0; index <= 20; index++) {
                    assert audio != null;
                    audio.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
                }
                this.mediaPlayer.setLooping(true);
                this.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    public void onPrepared(MediaPlayer player) {
                        mediaPlayer.start();
                    }
                });
                this.istAlarmGestartet = true;
                Log.i("HauptActivity", "Alarm wurde ausgelöst");
            }
        }
    }

    /**
     * Das Optionsmenü wird erstellt
     *
     * @param menu Optionsmenü
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    /**
     * Starten der Activitys aus dem Optionsmenü
     *
     * @param item Ausgewähltes optionsmenü
     * @return ob ein Menüitem ausgewählt wurde
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Kontakt_Einstellungen:
                Intent KontaktEinstellungen = new Intent(this, NotfallKontaktActivity.class);
                this.startActivity(KontaktEinstellungen);
                Log.i("HauptActivity", "Notfall Kontakt Aktivität wurde gestartet");
                return true;
            case R.id.Applikations_Einstellungen:
                Intent ApplikationsEinstellungen = new Intent(this, ApplikationEinstellungenActivity.class);
                this.startActivity(ApplikationsEinstellungen);
                Log.i("HauptActivity", "Applikations Einstellungen Aktivität wurde gestartet");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Setzen der Kontakte
     */
    private void SetzeKontakte() {
        ArrayList<NotfallKontakt> kontakte = Datenbank.getInstanz(getApplicationContext()).getNotfallKontakte();
        if (kontakte != null) {
            for (NotfallKontakt kontakt : kontakte) {
                TextView TextViewKontakt;
                switch (kontakt.getPrioritaet()) {
                    case Prioritaet_1:
                        TextViewKontakt = findViewById(R.id.KontaktName1);
                        break;
                    case Prioritaet_2:
                        TextViewKontakt = findViewById(R.id.KontaktName2);
                        break;
                    case Prioritaet_3:
                        TextViewKontakt = findViewById(R.id.KontaktName3);
                        break;
                    case Prioritaet_4:
                        TextViewKontakt = findViewById(R.id.KontaktName4);
                        break;
                    case Prioritaet_5:
                        TextViewKontakt = findViewById(R.id.KontaktName5);
                        break;
                    default:
                        continue;
                }
                TextViewKontakt.setText(kontakt.getBeschreibung());
                TextViewKontakt.setCompoundDrawablesWithIntrinsicBounds(kontakt.getkleinesBild(), 0, 0, 0);
            }
        }
        Log.i("HauptActivity", "Kontakte wurde gesetzt");
    }

    /**
     * Prüfen der Berechtigungen
     *
     * @param permission Berechtigung
     * @param requestCode Berechtigungscode
     */
    public void checkPermission(String permission, int requestCode) {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(monitorService, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(HauptActivity.this, new String[]{permission}, requestCode);
            Log.d("HauptActivity", "Berechtigung " + permission + " mit Code: " + requestCode + "wurde angefragt");
        }
    }

    /**
     * Prüfen ob die Berechtigung zugelassen wurde
     *
     * @param requestCode Berechtigungscode
     * @param permissions Berechtigung
     * @param grantResults gegebene Berechtigungen
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_READ_PERMISSION_CODE) {
            // Checking whether user granted the permission or not.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("HauptActivity", "Berechtigung mit dem code: " + requestCode + "wurde erteilt!");
                checkPermission(Manifest.permission.SEND_SMS, SMS_SEND_PERMISSION_CODE);
            } else {
                Log.w("HauptActivity", "Berechtigung mit dem code: " + requestCode + "wurde nicht erteilt!");
            }
        } else if (requestCode == SMS_RECEIVE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("HauptActivity", "Berechtigung mit dem code: " + requestCode + "wurde erteilt!");
                checkPermission(Manifest.permission.READ_SMS, SMS_READ_PERMISSION_CODE);
            } else {
                Log.w("HauptActivity", "Berechtigung mit dem code: " + requestCode + "wurde nicht erteilt!");
            }
        } else if (requestCode == SMS_SEND_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("HauptActivity", "Berechtigung mit dem code: " + requestCode + "wurde erteilt!");
                checkPermission(Manifest.permission.READ_PHONE_STATE, READ_PHONE_STATE);
            } else {
                Log.w("HauptActivity", "Berechtigung mit dem code: " + requestCode + "wurde nicht erteilt!");
            }
        } else if (requestCode == READ_PHONE_STATE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("HauptActivity", "Berechtigung mit dem code: " + requestCode + "wurde erteilt!");
            } else {
                Log.w("HauptActivity", "Berechtigung mit dem code: " + requestCode + "wurde nicht erteilt!");
            }
        }
    }
}