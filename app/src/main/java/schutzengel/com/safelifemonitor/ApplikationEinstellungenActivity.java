package schutzengel.com.safelifemonitor;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ApplikationEinstellungenActivity extends AppCompatActivity {
    EditText ZeitSetzer;
    TimePickerDialog ZeitPickerDialog;
    EditText Zeit1Von;
    EditText Zeit1Bis;
    EditText Zeit2Von;
    EditText Zeit2Bis;
    EditText Zeit3Von;
    EditText Zeit3Bis;
    EditText Zeit4Von;
    EditText Zeit4Bis;
    SeekBar seekbarSchwellwert;
    EditText SmsIntervall;
    CheckBox MonitorAktiv;
    EditText BenutzerName;
    View.OnFocusChangeListener onFocusChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_properties);
        // Read the data
        ApplikationEinstellungen applikationsEinstellungen = Datenbank.getInstanz().getApplikationsEinstellungen();


        onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ResetTimePicker();
                if(hasFocus) {
                    ZeitSetzer = (EditText)v;
                    ZeitPickerDialog.show();
                }
                else
                    ZeitPickerDialog.hide();
            }
        };

        Zeit1Von = findViewById(R.id.Zeit1_Von);
        Zeit1Von.setOnFocusChangeListener(onFocusChangeListener);
        Zeit1Bis = findViewById(R.id.Zeit1_Bis);
        Zeit1Bis.setOnFocusChangeListener(onFocusChangeListener);
        Zeit2Von = findViewById(R.id.Zeit2_Von);
        Zeit2Von.setOnFocusChangeListener(onFocusChangeListener);
        Zeit2Bis = findViewById(R.id.Zeit2_Bis);
        Zeit2Bis.setOnFocusChangeListener(onFocusChangeListener);
        Zeit3Von = findViewById(R.id.Zeit1_Von);
        Zeit3Von.setOnFocusChangeListener(onFocusChangeListener);
        Zeit3Bis = findViewById(R.id.Zeit3_Bis);
        Zeit3Bis.setOnFocusChangeListener(onFocusChangeListener);
        Zeit4Von = findViewById(R.id.Zeit4_Von);
        Zeit4Von.setOnFocusChangeListener(onFocusChangeListener);
        Zeit4Bis = findViewById(R.id.Zeit4_Bis);
        Zeit4Bis.setOnFocusChangeListener(onFocusChangeListener);

        seekbarSchwellwert = findViewById(R.id.SensorAktivitaet);
        SmsIntervall = findViewById(R.id.Wartezeit);
        MonitorAktiv = findViewById(R.id.AktiviereMonitoring);
        BenutzerName = findViewById(R.id.BenutzerName);

        FuelleZeiten(applikationsEinstellungen.getZeiten());
        seekbarSchwellwert.setProgress(applikationsEinstellungen.getSchwellwertBewegungssensor());
        SmsIntervall.setText(Integer.toString(applikationsEinstellungen.getIntervallSmsBenachrichtigung() / 1000 / 60));
        MonitorAktiv.setActivated(applikationsEinstellungen.getMonitorAktiv());
        BenutzerName.setText(applikationsEinstellungen.getBenutzerName());
    }

    private void ResetTimePicker()
    {
        ZeitPickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker tpView, int hourOfDay, int minute) {
                ZeitSetzer.setText(String.format ("%02d", hourOfDay) + ":" + String.format ("%02d", minute));
                ZeitPickerDialog.updateTime(0,0);
            }
        }, 0, 0, true);
    }

    public void onWritePersistent(View view)
    {
        // Create and populate the properites
        ApplikationEinstellungen applikationEinstellungen = new ApplikationEinstellungen();

        ArrayList<String> Zeiten = new ArrayList<String>();
        Zeiten.add(Zeit1Von.getText().toString());
        Zeiten.add(Zeit1Bis.getText().toString());
        Zeiten.add(Zeit2Von.getText().toString());
        Zeiten.add(Zeit2Bis.getText().toString());
        Zeiten.add(Zeit3Von.getText().toString());
        Zeiten.add(Zeit3Bis.getText().toString());
        Zeiten.add(Zeit4Von.getText().toString());
        Zeiten.add(Zeit4Bis.getText().toString());

        applikationEinstellungen.setZeiten(Zeiten);

        applikationEinstellungen.setSchwellwertBewegungssensor(seekbarSchwellwert.getProgress());
        applikationEinstellungen.setIntervallSmsBenachrichtigung(Integer.parseInt(SmsIntervall.getText().toString())*60*1000);
        applikationEinstellungen.setMonitorAktiv(MonitorAktiv.isChecked() ? 1 : 0);
        applikationEinstellungen.setBenutzerName(BenutzerName.getText().toString());
        // Write persistent
        Datenbank.getInstanz().set(applikationEinstellungen);

        finish();
    }

    public void Close(View view)
    {
        finish();
    }

    private void FuelleZeiten(ArrayList<String> Zeiten)
    {
        int Zaehler = 0;
        for (String t: Zeiten) {
            if(Zaehler == 0)
                Zeit1Von.setText(t);
            if(Zaehler == 1)
                Zeit1Bis.setText(t);
            if(Zaehler == 2)
                Zeit2Von.setText(t);
            if(Zaehler == 3)
                Zeit2Bis.setText(t);
            if(Zaehler == 4)
                Zeit3Von.setText(t);
            if(Zaehler == 5)
                Zeit3Bis.setText(t);
            if(Zaehler == 6)
                Zeit4Von.setText(t);
            if(Zaehler == 7)
                Zeit4Bis.setText(t);
            Zaehler++;
        }
    }
}
