package schutzengel.com.safelifemonitor.HMI;

import android.os.Bundle;
import android.view.View;
import schutzengel.com.safelifemonitor.Database.IDatabase;
import schutzengel.com.safelifemonitor.R;

public class ApplicationProperties extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_properties);
        // Read the data
        IDatabase database = schutzengel.com.safelifemonitor.Core.Factory.Factory.getInstance().getFactoryDatabase().getDatabase();
        schutzengel.com.safelifemonitor.Database.ApplicationProperties applicationProperties = database.getApplicationProperties();
        // fill the widghes....
    }

    public void onWritePersistent(View view)
    {
        // Create and populate the properites
        schutzengel.com.safelifemonitor.Database.ApplicationProperties properties = new schutzengel.com.safelifemonitor.Database.ApplicationProperties();

        // Write persistent
        IDatabase database = schutzengel.com.safelifemonitor.Core.Factory.Factory.getInstance().getFactoryDatabase().getDatabase();
        database.set(properties);
    }
}
