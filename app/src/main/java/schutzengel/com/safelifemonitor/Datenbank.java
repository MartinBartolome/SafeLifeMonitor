package schutzengel.com.safelifemonitor;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class Datenbank extends SQLiteOpenHelper {
    SQLiteDatabase sqLiteDatenbank;
    static final String DB_NAME = "SafeLifeMonitor.db";
    static final int DB_VERSION = 1;
    private static final String ERSTELLE_TABELLE_KONTAKT = "create table " + NotfallKontakt.TabellenName + "(" +
            NotfallKontakt.col_Prioritaet + " INTEGER NOT NULL," +
            NotfallKontakt.col_Icon + " INTEGER NOT NULL, " +
            NotfallKontakt.col_Beschreibung + " TEXT," +
            NotfallKontakt.col_Telefon + " TEXT NOT NULL);";
    private static final String LOESCHE_TABELLE_KONTAKT = "DROP TABLE IF EXISTS " + NotfallKontakt.TabellenName;
    private static final String ERSTELLE_TABELLE_APPLIKATION = "create table " + ApplikationEinstellungen.TabellenName + "(" +
            ApplikationEinstellungen.col_MonitorEnabled + " INTEGER NOT NULL," +
            ApplikationEinstellungen.col_BenutzerName + " Text, " +
            ApplikationEinstellungen.col_Schwellwert + " INTEGER NOT NULL," +
            ApplikationEinstellungen.col_MaxInactive + " INTEGER NOT NULL, " +
            ApplikationEinstellungen.col_Intervall + " INTEGER NOT NULL, " +
            ApplikationEinstellungen.col_Zeit1Von + " TEXT, " +
            ApplikationEinstellungen.col_Zeit1Bis + " TEXT, " +
            ApplikationEinstellungen.col_Zeit2Von + " TEXT, " +
            ApplikationEinstellungen.col_Zeit2Bis + " TEXT, " +
            ApplikationEinstellungen.col_Zeit3Von + " TEXT, " +
            ApplikationEinstellungen.col_Zeit3Bis + " TEXT, " +
            ApplikationEinstellungen.col_Zeit4Von + " TEXT, " +
            ApplikationEinstellungen.col_Zeit4Bis + " TEXT );";
    private static final String LOESCHE_TABELLE_APPLIKATION = "DROP TABLE IF EXISTS " + ApplikationEinstellungen.TabellenName;
    private static Datenbank instanz = null;
    private ApplikationEinstellungen applikationsEinstellungen = null;

    public static Datenbank getInstanz() {
        if (null == instanz) {
            instanz = new Datenbank();
        }
        return instanz;
    }

    public Datenbank() {
        super(HauptActivity.context, DB_NAME, null, DB_VERSION);
        this.applikationsEinstellungen = new ApplikationEinstellungen();
    }

    public ApplikationEinstellungen getApplikationsEinstellungen() {
        try {
            if (sqLiteDatenbank == null) {
                sqLiteDatenbank = getReadableDatabase();
            }
            String[] Tabellenspalten = new String[]{
                    ApplikationEinstellungen.col_MonitorEnabled,
                    ApplikationEinstellungen.col_BenutzerName,
                    ApplikationEinstellungen.col_Schwellwert,
                    ApplikationEinstellungen.col_MaxInactive,
                    ApplikationEinstellungen.col_Intervall,
                    ApplikationEinstellungen.col_Zeit1Von,
                    ApplikationEinstellungen.col_Zeit1Bis,
                    ApplikationEinstellungen.col_Zeit2Von,
                    ApplikationEinstellungen.col_Zeit2Bis,
                    ApplikationEinstellungen.col_Zeit3Von,
                    ApplikationEinstellungen.col_Zeit3Bis,
                    ApplikationEinstellungen.col_Zeit4Von,
                    ApplikationEinstellungen.col_Zeit4Bis
            };

            Cursor c = sqLiteDatenbank.query(ApplikationEinstellungen.TabellenName, Tabellenspalten, null, null,
                    null, null, null);
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                this.applikationsEinstellungen.setMonitorAktiv(c.getInt(c.getColumnIndex(ApplikationEinstellungen.col_MonitorEnabled)));
                this.applikationsEinstellungen.setBenutzerName(c.getString(c.getColumnIndex(ApplikationEinstellungen.col_BenutzerName)));
                this.applikationsEinstellungen.setSchwellwertBewegungssensor(c.getInt(c.getColumnIndex(ApplikationEinstellungen.col_Schwellwert)));
                this.applikationsEinstellungen.setMaximaleAnzahlInaktiveBewegungen(c.getInt(c.getColumnIndex(ApplikationEinstellungen.col_MaxInactive)));
                this.applikationsEinstellungen.setMonitorServiceInterval(c.getInt(c.getColumnIndex(ApplikationEinstellungen.col_Intervall)));
                ArrayList<String> Zeiten = new ArrayList<>();
                Zeiten.add((c.getString(c.getColumnIndex(ApplikationEinstellungen.col_Zeit1Von))));
                Zeiten.add((c.getString(c.getColumnIndex(ApplikationEinstellungen.col_Zeit1Bis))));
                Zeiten.add((c.getString(c.getColumnIndex(ApplikationEinstellungen.col_Zeit2Von))));
                Zeiten.add((c.getString(c.getColumnIndex(ApplikationEinstellungen.col_Zeit2Bis))));
                Zeiten.add((c.getString(c.getColumnIndex(ApplikationEinstellungen.col_Zeit3Von))));
                Zeiten.add((c.getString(c.getColumnIndex(ApplikationEinstellungen.col_Zeit3Bis))));
                Zeiten.add((c.getString(c.getColumnIndex(ApplikationEinstellungen.col_Zeit4Von))));
                Zeiten.add((c.getString(c.getColumnIndex(ApplikationEinstellungen.col_Zeit4Bis))));
                this.applikationsEinstellungen.setZeiten(Zeiten);

                StringBuilder Einstellungen = new StringBuilder();
                Einstellungen.append("Monitoring Erlaubt? " + this.applikationsEinstellungen.getMonitorAktiv() + System.getProperty("line.separator"));
                Einstellungen.append("Username: " + this.applikationsEinstellungen.getBenutzerName() + System.getProperty("line.separator"));
                Einstellungen.append("Schwellwert: " + this.applikationsEinstellungen.getSchwellwertBewegungssensor() + System.getProperty("line.separator"));
                Einstellungen.append("Maximale Anzahl Inaktive Bewegungen: " + this.applikationsEinstellungen.getMaximaleAnzahlInaktiveBewegungen() + System.getProperty("line.separator"));
                Einstellungen.append("MonitorServiceInterval: " + this.applikationsEinstellungen.getMonitorServiceInterval() + System.getProperty("line.separator"));
                Einstellungen.append("Zeit1 Von-bis: " + this.applikationsEinstellungen.getZeiten().get(0) + "-" + this.applikationsEinstellungen.getZeiten().get(1) + System.getProperty("line.separator"));
                Einstellungen.append("Zeit2 Von-bis: " + this.applikationsEinstellungen.getZeiten().get(2) + "-" + this.applikationsEinstellungen.getZeiten().get(3) + System.getProperty("line.separator"));
                Einstellungen.append("Zeit3 Von-bis: " + this.applikationsEinstellungen.getZeiten().get(4) + "-" + this.applikationsEinstellungen.getZeiten().get(5) + System.getProperty("line.separator"));
                Einstellungen.append("Zeit4 Von-bis: " + this.applikationsEinstellungen.getZeiten().get(6) + "-" + this.applikationsEinstellungen.getZeiten().get(7) + System.getProperty("line.separator"));

                Log.d("Datenbank","Lesen von der Datenbank: " + Einstellungen.toString());
            }
        } catch (Exception e) {
            Log.e("Datenbank","Fehler beim Lesen der Applikationseinstellungen. Fehlermeldung: " + e.getMessage());
            return null;
        }
        return this.applikationsEinstellungen;
    }

    public void set(ApplikationEinstellungen applikationsEinstellungen) {
        try {

            if (sqLiteDatenbank == null || sqLiteDatenbank.isReadOnly())
                sqLiteDatenbank = getWritableDatabase();
            ContentValues contentValue = new ContentValues();
            contentValue.put(ApplikationEinstellungen.col_MonitorEnabled, applikationsEinstellungen.getMonitorAktiv());
            contentValue.put(ApplikationEinstellungen.col_BenutzerName, applikationsEinstellungen.getBenutzerName());
            contentValue.put(ApplikationEinstellungen.col_Schwellwert, applikationsEinstellungen.getSchwellwertBewegungssensor());
            contentValue.put(ApplikationEinstellungen.col_MaxInactive, applikationsEinstellungen.getMaximaleAnzahlInaktiveBewegungen());
            contentValue.put(ApplikationEinstellungen.col_Intervall, applikationsEinstellungen.getMonitorServiceInterval());

            int zaehler = 0;
            for (String t : applikationsEinstellungen.getZeiten()) {
                if (zaehler == 0)
                    contentValue.put(ApplikationEinstellungen.col_Zeit1Von, t);
                if (zaehler == 1)
                    contentValue.put(ApplikationEinstellungen.col_Zeit1Bis, t);
                if (zaehler == 2)
                    contentValue.put(ApplikationEinstellungen.col_Zeit2Von, t);
                if (zaehler == 3)
                    contentValue.put(ApplikationEinstellungen.col_Zeit2Bis, t);
                if (zaehler == 4)
                    contentValue.put(ApplikationEinstellungen.col_Zeit3Von, t);
                if (zaehler == 5)
                    contentValue.put(ApplikationEinstellungen.col_Zeit3Bis, t);
                if (zaehler == 6)
                    contentValue.put(ApplikationEinstellungen.col_Zeit4Von, t);
                if (zaehler == 7)
                    contentValue.put(ApplikationEinstellungen.col_Zeit4Bis, t);
                zaehler++;
            }
            sqLiteDatenbank.update(ApplikationEinstellungen.TabellenName, contentValue, null, null);
            this.applikationsEinstellungen = applikationsEinstellungen;

            StringBuilder Einstellungen = new StringBuilder();
            Einstellungen.append("Monitoring Erlaubt? " + this.applikationsEinstellungen.getMonitorAktiv() + System.getProperty("line.separator"));
            Einstellungen.append("Username: " + this.applikationsEinstellungen.getBenutzerName() + System.getProperty("line.separator"));
            Einstellungen.append("Schwellwert: " + this.applikationsEinstellungen.getSchwellwertBewegungssensor() + System.getProperty("line.separator"));
            Einstellungen.append("Maximale Anzahl Inaktive Bewegungen: " + this.applikationsEinstellungen.getMaximaleAnzahlInaktiveBewegungen() + System.getProperty("line.separator"));
            Einstellungen.append("MonitorServiceInterval: " + this.applikationsEinstellungen.getMonitorServiceInterval() + System.getProperty("line.separator"));
            Einstellungen.append("Zeit1 Von-bis: " + this.applikationsEinstellungen.getZeiten().get(0) + "-" + this.applikationsEinstellungen.getZeiten().get(1) + System.getProperty("line.separator"));
            Einstellungen.append("Zeit2 Von-bis: " + this.applikationsEinstellungen.getZeiten().get(2) + "-" + this.applikationsEinstellungen.getZeiten().get(3) + System.getProperty("line.separator"));
            Einstellungen.append("Zeit3 Von-bis: " + this.applikationsEinstellungen.getZeiten().get(4) + "-" + this.applikationsEinstellungen.getZeiten().get(5) + System.getProperty("line.separator"));
            Einstellungen.append("Zeit4 Von-bis: " + this.applikationsEinstellungen.getZeiten().get(6) + "-" + this.applikationsEinstellungen.getZeiten().get(7) + System.getProperty("line.separator"));

            Log.d("Datenbank","Schreiben in die Datenbank: " + Einstellungen.toString());

        }
        catch (Exception e)
        {
            Log.e("Datenbank","Fehler beim schreiben in die Datenbank. Fehlermeldung: " + e.getMessage());
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            sqLiteDatenbank = db;
            sqLiteDatenbank.execSQL(ERSTELLE_TABELLE_KONTAKT);
            Log.i("Datenbank","Tabelle Kontakt erstellt");
            for (int i = 0; i < 5; i++) {
                sqLiteDatenbank.execSQL("INSERT INTO " + NotfallKontakt.TabellenName + " VALUES(" + i + ",0,NULL,0123456789);");
                Log.i("Datenbank","Dummy Kontakt erstellt");
            }
            sqLiteDatenbank.execSQL(ERSTELLE_TABELLE_APPLIKATION);
            Log.i("Datenbank","Applikationsdatenbank erstellt");
            sqLiteDatenbank.execSQL("INSERT INTO " + ApplikationEinstellungen.TabellenName + " VALUES (0,'Rüstiger Rentner',1," + applikationsEinstellungen.getMaximaleAnzahlInaktiveBewegungen() + "," + applikationsEinstellungen.getMonitorServiceInterval() + ",'00:00','00:00','00:00','00:00','00:00','00:00','00:00','00:00' )");
            Log.i("Datenbank","Eintrag eingefügt");
        }
        catch (Exception e)
        {
            Log.e("Datenbank","Fehler beim Erstellen der Datenbanken. Fehlermeldung:" + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(LOESCHE_TABELLE_KONTAKT);
        db.execSQL(LOESCHE_TABELLE_APPLIKATION);
        onCreate(db);
        Log.i("Datenbank","Upgrade der Datenbank");
    }


    public void set(NotfallKontakt contact) {
        try {
            if (sqLiteDatenbank == null || sqLiteDatenbank.isReadOnly())
                sqLiteDatenbank = getWritableDatabase();
            ContentValues contentValue = new ContentValues();
            contentValue.put(NotfallKontakt.col_Prioritaet, contact.getPrioritaet().ordinal());
            contentValue.put(NotfallKontakt.col_Icon, contact.getIcon().ordinal());
            contentValue.put(NotfallKontakt.col_Beschreibung, contact.getBeschreibung());
            contentValue.put(NotfallKontakt.col_Telefon, contact.getAlarmTelefonNummer());
            sqLiteDatenbank.update(NotfallKontakt.TabellenName, contentValue, NotfallKontakt.col_Prioritaet + "= ?", new String[]{String.valueOf(contact.getPrioritaet().ordinal())});

            StringBuilder kontaktstring = new StringBuilder();
            kontaktstring.append("Priorität " + contact.getPrioritaet() + System.getProperty("line.separator"));
            kontaktstring.append("Icon " + contact.getIcon() + System.getProperty("line.separator"));
            kontaktstring.append("Beschreibung " + contact.getBeschreibung() + System.getProperty("line.separator"));
            kontaktstring.append("Telefonnummer " + contact.getAlarmTelefonNummer() + System.getProperty("line.separator"));
            Log.d("Datenbank", "Kontakt aktualisiert: " + kontaktstring.toString());

        }
        catch (Exception e)
        {
            Log.e("Datenbank","Kontakt konnte nicht aktualisiert werden. Fehlermeldung: " + e.getMessage());
        }
    }

    public void set(ArrayList<NotfallKontakt> notfallKontakte) {
        for (NotfallKontakt notfallKontakt : notfallKontakte) {
            set(notfallKontakt);
        }
    }

    public ArrayList<NotfallKontakt> getNotfallKontakte() {
        if (sqLiteDatenbank == null)
            sqLiteDatenbank = getReadableDatabase();
        ArrayList<NotfallKontakt> Kontakte = new ArrayList<NotfallKontakt>();
        String[] tableColumns = new String[]{NotfallKontakt.col_Prioritaet, NotfallKontakt.col_Icon, NotfallKontakt.col_Beschreibung, NotfallKontakt.col_Telefon};
        try {
            Cursor c = sqLiteDatenbank.query(NotfallKontakt.TabellenName, tableColumns, null, null,
                    null, null, null);
            if (c != null) {
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    NotfallKontakt kontakt = new NotfallKontakt();
                    kontakt.setPrioritaet(c.getInt(c.getColumnIndex(NotfallKontakt.col_Prioritaet)));
                    kontakt.setIcon(c.getInt(c.getColumnIndex(NotfallKontakt.col_Icon)));
                    kontakt.setBeschreibung(c.getString(c.getColumnIndex(NotfallKontakt.col_Beschreibung)));
                    kontakt.setAlarmTelefonNummer(c.getString(c.getColumnIndex(NotfallKontakt.col_Telefon)));

                    StringBuilder kontaktstring = new StringBuilder();
                    kontaktstring.append("Priorität " + kontakt.getPrioritaet() + System.getProperty("line.separator"));
                    kontaktstring.append("Icon " + kontakt.getIcon() + System.getProperty("line.separator"));
                    kontaktstring.append("Beschreibung " + kontakt.getBeschreibung() + System.getProperty("line.separator"));
                    kontaktstring.append("Telefonnummer " + kontakt.getAlarmTelefonNummer() + System.getProperty("line.separator"));
                    Log.d("Datenbank","Kontakt geladen: " + kontaktstring.toString());

                    Kontakte.add(kontakt);
                }
            }
        } catch (Exception e) {
            Log.e("Datenbank","Fehler beim Laden der Notfallkontakte. Fehlermeldung: " +e.getMessage());
            return null;
        }
        return Kontakte;
    }

    public NotfallKontakt getNotfallKontakt(NotfallKontakt.Prioritaet prioritaet) {
        try {
            if (sqLiteDatenbank == null)
                sqLiteDatenbank = getReadableDatabase();
            String[] tableColumns = new String[]{NotfallKontakt.col_Prioritaet, NotfallKontakt.col_Icon, NotfallKontakt.col_Beschreibung, NotfallKontakt.col_Telefon};
            Cursor c = sqLiteDatenbank.query(NotfallKontakt.TabellenName, tableColumns, NotfallKontakt.col_Prioritaet + "=" + prioritaet.ordinal(), null,
                    null, null, null);
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                NotfallKontakt kontakt = new NotfallKontakt();
                kontakt.setPrioritaet(c.getInt(c.getColumnIndex(NotfallKontakt.col_Prioritaet)));
                kontakt.setIcon(c.getInt(c.getColumnIndex(NotfallKontakt.col_Icon)));
                kontakt.setBeschreibung(c.getString(c.getColumnIndex(NotfallKontakt.col_Beschreibung)));
                kontakt.setAlarmTelefonNummer(c.getString(c.getColumnIndex(NotfallKontakt.col_Telefon)));

                StringBuilder kontaktstring = new StringBuilder();
                kontaktstring.append("Priorität " + kontakt.getPrioritaet() + System.getProperty("line.separator"));
                kontaktstring.append("Icon " + kontakt.getIcon() + System.getProperty("line.separator"));
                kontaktstring.append("Beschreibung " + kontakt.getBeschreibung() + System.getProperty("line.separator"));
                kontaktstring.append("Telefonnummer " + kontakt.getAlarmTelefonNummer() + System.getProperty("line.separator"));
                Log.d("Datenbank","Einzelner Notfallkontakt geladen: " + kontaktstring.toString());

                return kontakt;
            }
        } catch (Exception e) {
            Log.e("Datenbank","Fehler beim Laden eines Notfallkontakts. Fehlermeldung: " +e.getMessage());
            return null;
        }

        return null;
    }
}

