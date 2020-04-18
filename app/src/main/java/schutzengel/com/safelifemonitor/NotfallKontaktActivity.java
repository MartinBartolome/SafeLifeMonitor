package schutzengel.com.safelifemonitor;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NotfallKontaktActivity extends AppCompatActivity {

    private ArrayList<NotfallKontakt> Contacts = null;
    private int selectedContactindex = 0;

    EditText DescriptionEdit = null;
    EditText TelephonEdit = null;
    ImageView IconImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_properties);

        Contacts = Datenbank.getInstance().getNotfallKontakte();
        DescriptionEdit = findViewById(R.id.descriptionEdit);
        TelephonEdit = findViewById(R.id.TelephoneEdit);
        IconImage = findViewById(R.id.ContactImage);

        IconImage.setImageResource(Contacts.get(selectedContactindex).getDrawable());
        DescriptionEdit.setText(Contacts.get(selectedContactindex).getBeschreibung());
        TelephonEdit.setText(Contacts.get(selectedContactindex).getAlarmTelefonNummer());
        IconImage.setImageResource(Contacts.get(selectedContactindex).getDrawable());
    }

    public void onWritePersistent(View view)
    {
        Contacts.get(selectedContactindex).setAlarmTelefonNummer(TelephonEdit.getText().toString());
        Contacts.get(selectedContactindex).setBeschreibung(DescriptionEdit.getText().toString());

        Datenbank.getInstance().set(Contacts);
        finish();
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
        if(Contacts.get(selectedContactindex).getIcon().ordinal() == 0 && direction == -1)
        {
            Contacts.get(selectedContactindex).setIcon(NotfallKontakt.Icon.values().length-1);
        }
        else if (Contacts.get(selectedContactindex).getIcon().ordinal() == NotfallKontakt.Icon.values().length-1 && direction == 1)
        {
            Contacts.get(selectedContactindex).setIcon(0);
        }
        else {
            Contacts.get(selectedContactindex).setIcon(Contacts.get(selectedContactindex).getIcon().ordinal() + direction);
        }
        IconImage.setImageResource(Contacts.get(selectedContactindex).getDrawable());
    }

    public void ChangeContact(View view)
    {
        Contacts.get(selectedContactindex).setAlarmTelefonNummer(TelephonEdit.getText().toString());
        Contacts.get(selectedContactindex).setBeschreibung(DescriptionEdit.getText().toString());

        int Priority = 0;
        switch (view.getId())
        {
            case R.id.ImagePriority1:
                Priority = NotfallKontakt.Prioritaet.Prioritaet_1.ordinal();
                break;
            case R.id.ImagePriority2:
                Priority = NotfallKontakt.Prioritaet.Prioritaet_2.ordinal();
                break;
            case R.id.ImagePriority3:
                Priority = NotfallKontakt.Prioritaet.Prioritaet_3.ordinal();
                break;
            case R.id.ImagePriority4:
                Priority = NotfallKontakt.Prioritaet.Prioritaet_4.ordinal();
                break;
            case R.id.ImagePriority5:
                Priority = NotfallKontakt.Prioritaet.Prioritaet_5.ordinal();
                break;
        }

        for (NotfallKontakt c:Contacts) {
            if(c.getPrioritaet().ordinal() == Priority)
            {
                selectedContactindex = Contacts.indexOf(c);
            }
        }

        DescriptionEdit.setText(Contacts.get(selectedContactindex).getBeschreibung());
        TelephonEdit.setText(Contacts.get(selectedContactindex).getAlarmTelefonNummer());
        IconImage.setImageResource(Contacts.get(selectedContactindex).getDrawable());
    }
    public void Close(View view)
    {
        finish();
    }
}
