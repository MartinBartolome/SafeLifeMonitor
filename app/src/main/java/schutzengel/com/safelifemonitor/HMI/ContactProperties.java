package schutzengel.com.safelifemonitor.HMI;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.view.MotionEventCompat;

import java.util.ArrayList;

import schutzengel.com.safelifemonitor.Core.Factory.Factory;
import schutzengel.com.safelifemonitor.Database.IDatabase;
import schutzengel.com.safelifemonitor.R;

public class ContactProperties extends Activity {

    private ArrayList<schutzengel.com.safelifemonitor.Database.ContactProperties> Contacts = null;
    private int selectedContactindex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_properties);
        Contacts = Factory.getInstance().getFactoryDatabase().getDatabase().getContacts();
    }

    public void onWritePersistent(View view)
    {
        // Create and populate the properites
        schutzengel.com.safelifemonitor.Database.ContactProperties properties = new schutzengel.com.safelifemonitor.Database.ContactProperties();

        // Write persistent
        IDatabase database = schutzengel.com.safelifemonitor.Core.Factory.Factory.getInstance().getFactoryDatabase().getDatabase();
        database.set(properties);
    }

    public void LeftImage(View view)
    {
        ChangeImage(-1);
    }
    public void RightImage(View view)
    {
        ChangeImage(1);
    }

    private void ChangeImage(int direction)
    {
        ImageView image = findViewById(R.id.ContactImage);

        if(Contacts.get(selectedContactindex).getIcon().ordinal() == 0 && direction == -1)
        {
            Contacts.get(selectedContactindex).setIcon(schutzengel.com.safelifemonitor.Database.ContactProperties.Icon.values().length-1);
        }
        else if (Contacts.get(selectedContactindex).getIcon().ordinal() == schutzengel.com.safelifemonitor.Database.ContactProperties.Icon.values().length-1 && direction == 1)
        {
            Contacts.get(selectedContactindex).setIcon(0);
        }
        else {
            Contacts.get(selectedContactindex).setIcon(Contacts.get(selectedContactindex).getIcon().ordinal() + direction);
        }
        image.setImageResource(Contacts.get(selectedContactindex).getDrawable());
    }

    public void ChangeContact(View view)
    {
        EditText DescriptionEdit = findViewById(R.id.descriptionEdit);
        EditText TelephonEdit = findViewById(R.id.TelephoneEdit);
        ImageView IconImage = findViewById(R.id.ContactImage);

        Contacts.get(selectedContactindex).setAlarmTelephoneNumber(TelephonEdit.getText().toString());
        Contacts.get(selectedContactindex).setDescription(DescriptionEdit.getText().toString());

        int Priority = 0;
        switch (view.getId())
        {
            case R.id.ImagePriority1:
                Priority = 1;
                break;
            case R.id.ImagePriority2:
                Priority = 2;
                break;
            case R.id.ImagePriority3:
                Priority = 3;
                break;
            case R.id.ImagePriority4:
                Priority = 4;
                break;
            case R.id.ImagePriority5:
                Priority = 5;
                break;
        }

        for (schutzengel.com.safelifemonitor.Database.ContactProperties c:Contacts) {
            if(c.getPriority().ordinal() == Priority)
            {
                selectedContactindex = Contacts.indexOf(c);
            }
        }

        DescriptionEdit.setText(Contacts.get(selectedContactindex).getDescription());
        TelephonEdit.setText(Contacts.get(selectedContactindex).getAlarmTelephoneNumber());
        IconImage.setImageResource(Contacts.get(selectedContactindex).getDrawable());
    }

    public void Save(View view)
    {
        Factory.getInstance().getFactoryDatabase().getDatabase().set(Contacts);
        finish();
    }
    public void Close(View view)
    {
        finish();
    }
}
