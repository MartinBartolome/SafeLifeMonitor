package schutzengel.com.safelifemonitor.HMI;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.core.view.MotionEventCompat;

import schutzengel.com.safelifemonitor.Database.IDatabase;
import schutzengel.com.safelifemonitor.R;

public class ContactProperties extends Activity {

    private schutzengel.com.safelifemonitor.Database.ContactProperties contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_properties);
        this.contact = new schutzengel.com.safelifemonitor.Database.ContactProperties();
        contact.setIcon(schutzengel.com.safelifemonitor.Database.ContactProperties.Icon.Hospital);
    }

    public void onWritePersistent(View view)
    {
        // Create and populate the properites
        schutzengel.com.safelifemonitor.Database.ContactProperties properties = new schutzengel.com.safelifemonitor.Database.ContactProperties();

        // Write persistent
        IDatabase database = schutzengel.com.safelifemonitor.Core.Factory.Factory.getInstance().getFactoryDatabase().getDatabase();
        database.set(properties);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getActionMasked())
        {
            case 8:
                Toast.makeText(getApplicationContext(), "8", Toast.LENGTH_LONG).show();
                break;
            case 16:
                Toast.makeText(getApplicationContext(), "16", Toast.LENGTH_LONG).show();
                break;

        }
        return true;
    }
}
