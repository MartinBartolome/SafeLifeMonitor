package schutzengel.com.safelifemonitor;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class NotfallKontaktActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_properties);
    }

    public void onWritePersistent(View view)
    {
        // Create and populate the properites
        NotfallKontakt notfallKontakt = new NotfallKontakt();

        // Write persistent
        Datenbank.getInstance().set(notfallKontakt);
    }
}
