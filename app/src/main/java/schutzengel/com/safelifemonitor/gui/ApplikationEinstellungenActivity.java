package schutzengel.com.safelifemonitor.gui;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

import schutzengel.com.safelifemonitor.datenbank.ApplikationEinstellungen;
import schutzengel.com.safelifemonitor.datenbank.Datenbank;
import schutzengel.com.safelifemonitor.R;

public class ApplikationEinstellungenActivity extends AppCompatActivity {
    private EditText zeitSetzer;
    private TimePickerDialog zeitPickerDialog;
    private EditText zeit1Von;
    private EditText zeit1Bis;
    private EditText zeit2Von;
    private EditText zeit2Bis;
    private EditText zeit3Von;
    private EditText zeit3Bis;
    private EditText zeit4Von;
    private EditText zeit4Bis;
    private SeekBar seekbarSchwellwert;
    private EditText smsIntervall;
    private CheckBox monitorAktiv;
    private EditText benutzerName;

    /**
     * Erstellen der Aktivität. Dabei werden Die Applikationseinstellungen geladen und für jeden Zeiteditor wird ein Listener gesetzt, dass bei Fokus ein TimePickerDialog geöffnet wird.
     * Dies erlaubt eine bequeme selektion der Zeit für den Benutzer.
     * Anschliessend werden die Einstellungen geladen
     *
     * @param savedInstanceState Gespeicherter Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applikations_eigenschaften);
        // Read the data
        ApplikationEinstellungen applikationsEinstellungen = Datenbank.getInstanz(getApplicationContext()).getApplikationsEinstellungen();
        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                ResetTimePicker();
                if (hasFocus) {
                    zeitSetzer = (EditText) view;
                    zeitPickerDialog.show();
                } else
                    zeitPickerDialog.hide();
            }
        };
        this.zeit1Von = findViewById(R.id.Zeit1_Von);
        this.zeit1Von.setOnFocusChangeListener(onFocusChangeListener);
        this.zeit1Bis = findViewById(R.id.Zeit1_Bis);
        this.zeit1Bis.setOnFocusChangeListener(onFocusChangeListener);
        this.zeit2Von = findViewById(R.id.Zeit2_Von);
        this.zeit2Von.setOnFocusChangeListener(onFocusChangeListener);
        this.zeit2Bis = findViewById(R.id.Zeit2_Bis);
        this.zeit2Bis.setOnFocusChangeListener(onFocusChangeListener);
        this.zeit3Von = findViewById(R.id.Zeit3_Von);
        this.zeit3Von.setOnFocusChangeListener(onFocusChangeListener);
        this.zeit3Bis = findViewById(R.id.Zeit3_Bis);
        this.zeit3Bis.setOnFocusChangeListener(onFocusChangeListener);
        this.zeit4Von = findViewById(R.id.Zeit4_Von);
        this.zeit4Von.setOnFocusChangeListener(onFocusChangeListener);
        this.zeit4Bis = findViewById(R.id.Zeit4_Bis);
        this.zeit4Bis.setOnFocusChangeListener(onFocusChangeListener);
        this.seekbarSchwellwert = findViewById(R.id.SensorAktivitaet);
        this.smsIntervall = findViewById(R.id.Wartezeit);
        this.monitorAktiv = findViewById(R.id.AktiviereMonitoring);
        this.benutzerName = findViewById(R.id.BenutzerName);
        FuelleZeiten(applikationsEinstellungen.getZeiten());
        this.seekbarSchwellwert.setProgress(applikationsEinstellungen.getSchwellwertBewegungssensor());
        this.smsIntervall.setText(String.format(Locale.GERMAN,"%d", applikationsEinstellungen.getIntervallSmsBenachrichtigung() / 1000 / 60));
        this.monitorAktiv.setChecked(applikationsEinstellungen.getMonitorAktiv());
        this.benutzerName.setText(applikationsEinstellungen.getBenutzerName());
    }

    /**
     * Zurücksetzen des TimePickers auf 00:00
     */
    private void ResetTimePicker() {
        this.zeitPickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTimeSet(TimePicker tpView, int hourOfDay, int minute) {
                zeitSetzer.setText(String.format(getApplicationContext().getString(R.string.ZeitFormat),hourOfDay,minute));
                zeitPickerDialog.updateTime(0, 0);
            }
        }, 0, 0, true);
    }

    /**
     * Zusammenfassen der Einstellungen und schreiben in die Datenbank
     *
     */
    public void onWritePersistent(View view) {
        if(view != null) {
            // Create and populate the properites
            ApplikationEinstellungen applikationEinstellungen = new ApplikationEinstellungen();
            ArrayList<String> zeiten = new ArrayList<>();
            zeiten.add(this.zeit1Von.getText().toString());
            zeiten.add(this.zeit1Bis.getText().toString());
            zeiten.add(this.zeit2Von.getText().toString());
            zeiten.add(this.zeit2Bis.getText().toString());
            zeiten.add(this.zeit3Von.getText().toString());
            zeiten.add(this.zeit3Bis.getText().toString());
            zeiten.add(this.zeit4Von.getText().toString());
            zeiten.add(this.zeit4Bis.getText().toString());
            applikationEinstellungen.setZeiten(zeiten);
            applikationEinstellungen.setSchwellwertBewegungssensor(this.seekbarSchwellwert.getProgress());
            applikationEinstellungen.setIntervallSmsBenachrichtigung(Integer.parseInt(smsIntervall.getText().toString()) * 60 * 1000);
            applikationEinstellungen.setMonitorAktiv(this.monitorAktiv.isChecked() ? 1 : 0);
            applikationEinstellungen.setBenutzerName(this.benutzerName.getText().toString());
            // Write persistent
            Datenbank.getInstanz(getApplicationContext()).set(applikationEinstellungen);
            finish();
        }
    }

    /**
     * Schliessen der Aktivität
     *
     */
    public void Close(View view) {
        if(view != null) {
            finish();
        }
    }

    /**
     * Ausfüllen der Zeiten von der ArrayList in die Editoren.
     *
     * @param Zeiten Array der Zeiten
     */
    private void FuelleZeiten(ArrayList<String> Zeiten) {
        int zaehler = 0;
        for (String zeit : Zeiten) {
            switch (zaehler) {
                case 0:
                    zeit1Von.setText(zeit);
                    break;
                case 1:
                    zeit1Bis.setText(zeit);
                    break;
                case 2:
                    zeit2Von.setText(zeit);
                    break;
                case 3:
                    zeit2Bis.setText(zeit);
                    break;
                case 4:
                    zeit3Von.setText(zeit);
                    break;
                case 5:
                    zeit3Bis.setText(zeit);
                    break;
                case 6:
                    zeit4Von.setText(zeit);
                    break;
                case 7:
                    zeit4Bis.setText(zeit);
                    break;
            }
            zaehler++;
        }
    }
}
