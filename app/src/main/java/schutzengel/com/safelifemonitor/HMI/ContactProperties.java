package schutzengel.com.safelifemonitor.HMI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import schutzengel.com.safelifemonitor.Database.IDatabase;
import schutzengel.com.safelifemonitor.R;

public class ContactProperties extends AppCompatActivity implements IActivity {
    @Override
    public void initialize() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_properties);
    }

    public void onWritePersistent(View view)
    {
        // Create and populate the properites
        schutzengel.com.safelifemonitor.Database.ContactProperties properties = new schutzengel.com.safelifemonitor.Database.ContactProperties();

        // Write persistent
        IDatabase database = schutzengel.com.safelifemonitor.Core.Factory.Factory.getInstance().getFactoryDatabase().getDatabase();
        database.set(properties);
    }
}
