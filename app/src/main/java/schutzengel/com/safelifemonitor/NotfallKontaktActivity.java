package schutzengel.com.safelifemonitor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NotfallKontaktActivity extends AppCompatActivity {

    private ArrayList<NotfallKontakt> Kontakte = null;
    private int gewaehlterKontaktIndex = 0;

    EditText BeschreibungsEditor = null;
    EditText TelefonEditor = null;
    ImageView IconBild = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kontakt_eigenschaften);

        Kontakte = Datenbank.getInstanz().getNotfallKontakte();
        BeschreibungsEditor = findViewById(R.id.beschreibungseditor);
        TelefonEditor = findViewById(R.id.telefoneditor);
        IconBild = findViewById(R.id.kontaktbild);

        IconBild.setImageResource(Kontakte.get(gewaehlterKontaktIndex).getBild());
        BeschreibungsEditor.setText(Kontakte.get(gewaehlterKontaktIndex).getBeschreibung());
        TelefonEditor.setText(Kontakte.get(gewaehlterKontaktIndex).getAlarmTelefonNummer());
        IconBild.setImageResource(Kontakte.get(gewaehlterKontaktIndex).getBild());
    }

    public void onWritePersistent(View view)
    {
        Kontakte.get(gewaehlterKontaktIndex).setAlarmTelefonNummer(TelefonEditor.getText().toString());
        Kontakte.get(gewaehlterKontaktIndex).setBeschreibung(BeschreibungsEditor.getText().toString());

        Datenbank.getInstanz().set(Kontakte);
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
        try {
            if (Kontakte.get(gewaehlterKontaktIndex).getIcon().ordinal() == 0 && direction == -1) {
                Kontakte.get(gewaehlterKontaktIndex).setIcon(NotfallKontakt.Icon.values().length - 1);
            } else if (Kontakte.get(gewaehlterKontaktIndex).getIcon().ordinal() == NotfallKontakt.Icon.values().length - 1 && direction == 1) {
                Kontakte.get(gewaehlterKontaktIndex).setIcon(0);
            } else {
                Kontakte.get(gewaehlterKontaktIndex).setIcon(Kontakte.get(gewaehlterKontaktIndex).getIcon().ordinal() + direction);
            }
            IconBild.setImageResource(Kontakte.get(gewaehlterKontaktIndex).getBild());
        }
        catch (Exception e)
        {
            Log.e("NotfallKontaktActivity","Bild konnte nicht geändert werden. Fehlermeldung: " + e.getMessage());
        }
    }

    public void ChangeContact(View view)
    {
        try {
            Kontakte.get(gewaehlterKontaktIndex).setAlarmTelefonNummer(TelefonEditor.getText().toString());
            Kontakte.get(gewaehlterKontaktIndex).setBeschreibung(BeschreibungsEditor.getText().toString());

            int Prioritaet = 0;
            switch (view.getId()) {
                case R.id.BildPrioritaet1:
                    Prioritaet = NotfallKontakt.Prioritaet.Prioritaet_1.ordinal();
                    break;
                case R.id.BildPrioritaet2:
                    Prioritaet = NotfallKontakt.Prioritaet.Prioritaet_2.ordinal();
                    break;
                case R.id.BildPrioritaet3:
                    Prioritaet = NotfallKontakt.Prioritaet.Prioritaet_3.ordinal();
                    break;
                case R.id.BildPrioritaet4:
                    Prioritaet = NotfallKontakt.Prioritaet.Prioritaet_4.ordinal();
                    break;
                case R.id.BildPrioritaet5:
                    Prioritaet = NotfallKontakt.Prioritaet.Prioritaet_5.ordinal();
                    break;
            }

            for (NotfallKontakt k : Kontakte) {
                if (k.getPrioritaet().ordinal() == Prioritaet) {
                    gewaehlterKontaktIndex = Kontakte.indexOf(k);
                }
            }

            BeschreibungsEditor.setText(Kontakte.get(gewaehlterKontaktIndex).getBeschreibung());
            TelefonEditor.setText(Kontakte.get(gewaehlterKontaktIndex).getAlarmTelefonNummer());
            IconBild.setImageResource(Kontakte.get(gewaehlterKontaktIndex).getBild());
        }
        catch (Exception e)
        {
            Log.e("NotfallKontaktActivity","Kontakt konnte nicht geändert werden. Fehlermeldung: " + e.getMessage());
        }
    }
    public void Close(View view)
    {
        finish();
    }
}
