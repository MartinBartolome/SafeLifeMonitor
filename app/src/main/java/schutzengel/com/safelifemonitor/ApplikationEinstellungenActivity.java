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
    EditText SetTime;
    TimePickerDialog timePickerDialog;
    EditText Time1From;
    EditText Time1To;
    EditText Time2From;
    EditText Time2To;
    EditText Time3From;
    EditText Time3To;
    EditText Time4From;
    EditText Time4To;
    SeekBar seekbarSchwellwert;
    EditText SmsIntervall;
    CheckBox MonitorEnabled;
    EditText UserName;
    View.OnFocusChangeListener onFocusChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_properties);
        // Read the data
        ApplikationEinstellungen applikationEinstellungen = Datenbank.getInstance().getApplikationEinstellungen();


        onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ResetTimePicker();
                if(hasFocus) {
                    SetTime = (EditText)v;
                    timePickerDialog.show();
                }
                else
                    timePickerDialog.hide();
            }
        };

        Time1From = findViewById(R.id.Time1_from);
        Time1From.setOnFocusChangeListener(onFocusChangeListener);
        Time1To = findViewById(R.id.Time1_to);
        Time1To.setOnFocusChangeListener(onFocusChangeListener);
        Time2From = findViewById(R.id.Time2_from);
        Time2From.setOnFocusChangeListener(onFocusChangeListener);
        Time2To = findViewById(R.id.Time2_to);
        Time2To.setOnFocusChangeListener(onFocusChangeListener);
        Time3From = findViewById(R.id.Time3_from);
        Time3From.setOnFocusChangeListener(onFocusChangeListener);
        Time3To = findViewById(R.id.Time3_to);
        Time3To.setOnFocusChangeListener(onFocusChangeListener);
        Time4From = findViewById(R.id.Time4_from);
        Time4From.setOnFocusChangeListener(onFocusChangeListener);
        Time4To = findViewById(R.id.Time4_to);
        Time4To.setOnFocusChangeListener(onFocusChangeListener);

        seekbarSchwellwert = findViewById(R.id.sensoractivity);
        SmsIntervall = findViewById(R.id.waittime);
        MonitorEnabled = findViewById(R.id.ActivateMonitoring);
        UserName = findViewById(R.id.UserName);

        FillTime(applikationEinstellungen.getZeiten());
        seekbarSchwellwert.setProgress(applikationEinstellungen.getSchwellwertBewegungssensor());
        SmsIntervall.setText(Integer.toString(applikationEinstellungen.getIntervallSmsBenachrichtigung() / 1000 / 60));
        MonitorEnabled.setActivated(applikationEinstellungen.istMonitorAktiv());
        UserName.setText(applikationEinstellungen.getUserName());
    }

    private void ResetTimePicker()
    {
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker tpView, int hourOfDay, int minute) {
                SetTime.setText(String.format ("%02d", hourOfDay) + ":" + String.format ("%02d", minute));
                timePickerDialog.updateTime(0,0);
            }
        }, 0, 0, true);
    }

    public void onWritePersistent(View view)
    {
        // Create and populate the properites
        ApplikationEinstellungen applikationEinstellungen = new ApplikationEinstellungen();

        ArrayList<String> times = new ArrayList<String>();
        times.add(Time1From.getText().toString());
        times.add(Time1To.getText().toString());
        times.add(Time2From.getText().toString());
        times.add(Time2To.getText().toString());
        times.add(Time3From.getText().toString());
        times.add(Time3To.getText().toString());
        times.add(Time4From.getText().toString());
        times.add(Time4To.getText().toString());

        applikationEinstellungen.setZeiten(times);

        applikationEinstellungen.setSchwellwertBewegungssensor(seekbarSchwellwert.getProgress());
        applikationEinstellungen.setIntervallSmsBenachrichtigung(Integer.parseInt(SmsIntervall.getText().toString())*60*1000);
        applikationEinstellungen.setMonitorEnabled(MonitorEnabled.isChecked() ? 1 : 0);
        applikationEinstellungen.setUserName(UserName.getText().toString());
        // Write persistent
        Datenbank.getInstance().set(applikationEinstellungen);

        finish();
    }

    public void Close(View view)
    {
        finish();
    }

    private void FillTime(ArrayList<String> times)
    {
        int counter = 0;
        for (String t: times) {
            if(counter == 0)
                Time1From.setText(t);
            if(counter == 1)
                Time1To.setText(t);
            if(counter == 2)
                Time2From.setText(t);
            if(counter == 3)
                Time2To.setText(t);
            if(counter == 4)
                Time3From.setText(t);
            if(counter == 5)
                Time3To.setText(t);
            if(counter == 6)
                Time4From.setText(t);
            if(counter == 7)
                Time4To.setText(t);
            counter++;
        }
    }
}
