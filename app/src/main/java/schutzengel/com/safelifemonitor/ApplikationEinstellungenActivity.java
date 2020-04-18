package schutzengel.com.safelifemonitor;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ApplikationEinstellungenActivity extends AppCompatActivity {

    EditText SetTime;
    TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_properties);
        // Read the data
        ApplikationEinstellungen applikationEinstellungen = Datenbank.getInstance().getApplikationEinstellungen();
        // fill the widghes....
        FillTime(applikationEinstellungen.getTimes());
    }

    public void onWritePersistent(View view)
    {
        // Create and populate the properites
        ApplikationEinstellungen applikationEinstellungen = new ApplikationEinstellungen();

        // Write persistent
        Datenbank.getInstance().set(applikationEinstellungen);
    }

    public void GetTime(View view)
    {
        SetTime = (EditText)view;
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker tpView, int hourOfDay, int minute) {
                SetTime.setText(hourOfDay+":"+minute);
            }
        },0,0,true);
        timePickerDialog.show();
    }

    public void Save(View view)
    {
        finish();
    }
    public void Close(View view)
    {
        finish();
    }

    private void FillTime(ArrayList<Time> times)
    {
        TextView Time1From = findViewById(R.id.Time1_from);
        TextView Time1To = findViewById(R.id.Time1_to);
        TextView Time2From = findViewById(R.id.Time2_from);
        TextView Time2To = findViewById(R.id.Time2_to);
        TextView Time3From = findViewById(R.id.Time3_from);
        TextView Time3To = findViewById(R.id.Time3_to);
        TextView Time4From = findViewById(R.id.Time4_from);
        TextView Time4To = findViewById(R.id.Time4_to);

        int counter = 0;
        for (Time t: times) {
            DateFormat format = new SimpleDateFormat("HH:mm");
            if(counter == 0)
                Time1From.setText(format.format(t.getTime()));

        }
    }
}
