package schutzengel.com.safelifemonitor;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class ApplikationEinstellungenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_properties);
        // Read the data
        ApplikationEinstellungen applikationEinstellungen = Datenbank.getInstance().getApplikationEinstellungen();
        // fill the widghes....
    }

    public void onWritePersistent(View view)
    {
        // Create and populate the properites
        ApplikationEinstellungen applikationEinstellungen = new ApplikationEinstellungen();

        // Write persistent
        Datenbank.getInstance().set(applikationEinstellungen);
    }
}
