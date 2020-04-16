package schutzengel.com.safelifemonitor.HMI;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import schutzengel.com.safelifemonitor.Database.IDatabase;
import schutzengel.com.safelifemonitor.R;

public class ApplicationProperties extends Activity {

    EditText SetTime;
    TimePickerDialog timePickerDialog;
    schutzengel.com.safelifemonitor.Database.ApplicationProperties applicationProperties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_properties);

        // Read the data
        IDatabase database = schutzengel.com.safelifemonitor.Core.Factory.Factory.getInstance().getFactoryDatabase().getDatabase();
        applicationProperties = database.getApplicationProperties();
        applicationProperties.
    }

    public void onWritePersistent(View view)
    {
        // Create and populate the properites
        schutzengel.com.safelifemonitor.Database.ApplicationProperties properties = new schutzengel.com.safelifemonitor.Database.ApplicationProperties();

        // Write persistent
        IDatabase database = schutzengel.com.safelifemonitor.Core.Factory.Factory.getInstance().getFactoryDatabase().getDatabase();
        database.set(properties);
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
}
