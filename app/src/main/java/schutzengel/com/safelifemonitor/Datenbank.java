package schutzengel.com.safelifemonitor;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class Datenbank extends SQLiteOpenHelper {
    SQLiteDatabase sqLiteDatabase;
    static final String DB_NAME = "SafeLifeMonitor.db";
    static final int DB_VERSION = 1;
    private static final String CREATE_TABLE_CONTACT = "create table " + NotfallKontakt.tableName + "(" +
            NotfallKontakt.col_Priority + " INTEGER NOT NULL," +
            NotfallKontakt.col_Icon + " INTEGER NOT NULL, " +
            NotfallKontakt.col_Description + " TEXT," +
            NotfallKontakt.col_Telephone + " TEXT NOT NULL);";
    private static final String DROP_TABLE_CONTACT = "DROP TABLE IF EXISTS " + NotfallKontakt.tableName;
    private static final String CREATE_TABLE_APPLICATION = "create table " + ApplikationEinstellungen.tableName + "(" +
            ApplikationEinstellungen.col_MonitorEnabled + " INTEGER NOT NULL," +
            ApplikationEinstellungen.col_UserName + " Text, " +
            ApplikationEinstellungen.col_Schwellwert + " INTEGER NOT NULL," +
            ApplikationEinstellungen.col_MaxInactive + " INTEGER NOT NULL, " +
            ApplikationEinstellungen.col_Intervall + " INTEGER NOT NULL, " +
            ApplikationEinstellungen.col_Time1From + " TEXT, " +
            ApplikationEinstellungen.col_Time1To + " TEXT, " +
            ApplikationEinstellungen.col_Time2From + " TEXT, " +
            ApplikationEinstellungen.col_Time2To + " TEXT, " +
            ApplikationEinstellungen.col_Time3From + " TEXT, " +
            ApplikationEinstellungen.col_Time3To + " TEXT, " +
            ApplikationEinstellungen.col_Time4From + " TEXT, " +
            ApplikationEinstellungen.col_Time4To + " TEXT );";
    private static final String DROP_TABLE_APPLICATION = "DROP TABLE IF EXISTS " + ApplikationEinstellungen.tableName;
    private static Datenbank instance = null;
    private ApplikationEinstellungen applikationEinstellungen = null;

    public static Datenbank getInstance() {
        if (null == instance) {
            instance = new Datenbank();
        }
        return instance;
    }

    public Datenbank() {
        super(HauptActivity.context, DB_NAME, null, DB_VERSION);
        this.applikationEinstellungen = new ApplikationEinstellungen();
    }

    public ApplikationEinstellungen getApplikationEinstellungen() {
        try {
            if (sqLiteDatabase == null) {
                sqLiteDatabase = getReadableDatabase();
            }
            String[] tableColumns = new String[]{
                    ApplikationEinstellungen.col_MonitorEnabled,
                    ApplikationEinstellungen.col_UserName,
                    ApplikationEinstellungen.col_Schwellwert,
                    ApplikationEinstellungen.col_MaxInactive,
                    ApplikationEinstellungen.col_Intervall,
                    ApplikationEinstellungen.col_Time1From,
                    ApplikationEinstellungen.col_Time1To,
                    ApplikationEinstellungen.col_Time2From,
                    ApplikationEinstellungen.col_Time2To,
                    ApplikationEinstellungen.col_Time3From,
                    ApplikationEinstellungen.col_Time3To,
                    ApplikationEinstellungen.col_Time4From,
                    ApplikationEinstellungen.col_Time4To
            };

            Cursor c = sqLiteDatabase.query(ApplikationEinstellungen.tableName, tableColumns, null, null,
                    null, null, null);
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                this.applikationEinstellungen.setMonitorEnabled(c.getInt(c.getColumnIndex(ApplikationEinstellungen.col_MonitorEnabled)));
                this.applikationEinstellungen.setUserName(c.getString(c.getColumnIndex(ApplikationEinstellungen.col_UserName)));
                this.applikationEinstellungen.setSchwellwertBewegungssensor(c.getInt(c.getColumnIndex(ApplikationEinstellungen.col_Schwellwert)));
                this.applikationEinstellungen.setMaximaleAnzahlInaktiveBewegungen(c.getInt(c.getColumnIndex(ApplikationEinstellungen.col_MaxInactive)));
                this.applikationEinstellungen.setMonitorServiceInterval(c.getInt(c.getColumnIndex(ApplikationEinstellungen.col_Intervall)));
                ArrayList<String> Times = new ArrayList<>();
                Times.add((c.getString(c.getColumnIndex(ApplikationEinstellungen.col_Time1From))));
                Times.add((c.getString(c.getColumnIndex(ApplikationEinstellungen.col_Time1To))));
                Times.add((c.getString(c.getColumnIndex(ApplikationEinstellungen.col_Time2From))));
                Times.add((c.getString(c.getColumnIndex(ApplikationEinstellungen.col_Time2To))));
                Times.add((c.getString(c.getColumnIndex(ApplikationEinstellungen.col_Time3From))));
                Times.add((c.getString(c.getColumnIndex(ApplikationEinstellungen.col_Time3To))));
                Times.add((c.getString(c.getColumnIndex(ApplikationEinstellungen.col_Time4From))));
                Times.add((c.getString(c.getColumnIndex(ApplikationEinstellungen.col_Time4To))));
                this.applikationEinstellungen.setZeiten(Times);

                StringBuilder Einstellungen = new StringBuilder();
                Einstellungen.append("Monitoring Erlaubt? " + this.applikationEinstellungen.istMonitorAktiv() + System.getProperty("line.separator"));
                Einstellungen.append("Username: " + this.applikationEinstellungen.getUserName() + System.getProperty("line.separator"));
                Einstellungen.append("Schwellwert: " + this.applikationEinstellungen.getSchwellwertBewegungssensor() + System.getProperty("line.separator"));
                Einstellungen.append("Maximale Anzahl Inaktive Bewegungen: " + this.applikationEinstellungen.getMaximaleAnzahlInaktiveBewegungen() + System.getProperty("line.separator"));
                Einstellungen.append("MonitorServiceInterval: " + this.applikationEinstellungen.getMonitorServiceInterval() + System.getProperty("line.separator"));
                Einstellungen.append("Zeit1 Von-bis: " + this.applikationEinstellungen.getZeiten().get(0) + "-" + this.applikationEinstellungen.getZeiten().get(1) + System.getProperty("line.separator"));
                Einstellungen.append("Zeit2 Von-bis: " + this.applikationEinstellungen.getZeiten().get(2) + "-" + this.applikationEinstellungen.getZeiten().get(3) + System.getProperty("line.separator"));
                Einstellungen.append("Zeit3 Von-bis: " + this.applikationEinstellungen.getZeiten().get(4) + "-" + this.applikationEinstellungen.getZeiten().get(5) + System.getProperty("line.separator"));
                Einstellungen.append("Zeit4 Von-bis: " + this.applikationEinstellungen.getZeiten().get(6) + "-" + this.applikationEinstellungen.getZeiten().get(7) + System.getProperty("line.separator"));

                Log.d("Datenbank","Lesen von der Datenbank: " + Einstellungen.toString());
            }
        } catch (Exception e) {
            Log.e("Datenbank","Fehler beim Lesen der Applikationseinstellungen. Fehlermeldung: " + e.getMessage());
            return null;
        }
        return this.applikationEinstellungen;
    }

    public void set(ApplikationEinstellungen applikationEinstellungen) {
        try {

            if (sqLiteDatabase == null || sqLiteDatabase.isReadOnly())
                sqLiteDatabase = getWritableDatabase();
            ContentValues contentValue = new ContentValues();
            contentValue.put(ApplikationEinstellungen.col_MonitorEnabled, applikationEinstellungen.istMonitorAktiv());
            contentValue.put(ApplikationEinstellungen.col_UserName, applikationEinstellungen.getUserName());
            contentValue.put(ApplikationEinstellungen.col_Schwellwert, applikationEinstellungen.getSchwellwertBewegungssensor());
            contentValue.put(ApplikationEinstellungen.col_MaxInactive, applikationEinstellungen.getMaximaleAnzahlInaktiveBewegungen());
            contentValue.put(ApplikationEinstellungen.col_Intervall, applikationEinstellungen.getMonitorServiceInterval());

            int counter = 0;
            for (String t : applikationEinstellungen.getZeiten()) {
                if (counter == 0)
                    contentValue.put(ApplikationEinstellungen.col_Time1From, t);
                if (counter == 1)
                    contentValue.put(ApplikationEinstellungen.col_Time1To, t);
                if (counter == 2)
                    contentValue.put(ApplikationEinstellungen.col_Time2From, t);
                if (counter == 3)
                    contentValue.put(ApplikationEinstellungen.col_Time2To, t);
                if (counter == 4)
                    contentValue.put(ApplikationEinstellungen.col_Time3From, t);
                if (counter == 5)
                    contentValue.put(ApplikationEinstellungen.col_Time3To, t);
                if (counter == 6)
                    contentValue.put(ApplikationEinstellungen.col_Time4From, t);
                if (counter == 7)
                    contentValue.put(ApplikationEinstellungen.col_Time4To, t);
                counter++;
            }
            sqLiteDatabase.update(ApplikationEinstellungen.tableName, contentValue, null, null);
            this.applikationEinstellungen = applikationEinstellungen;

            StringBuilder Einstellungen = new StringBuilder();
            Einstellungen.append("Monitoring Erlaubt? " + this.applikationEinstellungen.istMonitorAktiv() + System.getProperty("line.separator"));
            Einstellungen.append("Username: " + this.applikationEinstellungen.getUserName() + System.getProperty("line.separator"));
            Einstellungen.append("Schwellwert: " + this.applikationEinstellungen.getSchwellwertBewegungssensor() + System.getProperty("line.separator"));
            Einstellungen.append("Maximale Anzahl Inaktive Bewegungen: " + this.applikationEinstellungen.getMaximaleAnzahlInaktiveBewegungen() + System.getProperty("line.separator"));
            Einstellungen.append("MonitorServiceInterval: " + this.applikationEinstellungen.getMonitorServiceInterval() + System.getProperty("line.separator"));
            Einstellungen.append("Zeit1 Von-bis: " + this.applikationEinstellungen.getZeiten().get(0) + "-" + this.applikationEinstellungen.getZeiten().get(1) + System.getProperty("line.separator"));
            Einstellungen.append("Zeit2 Von-bis: " + this.applikationEinstellungen.getZeiten().get(2) + "-" + this.applikationEinstellungen.getZeiten().get(3) + System.getProperty("line.separator"));
            Einstellungen.append("Zeit3 Von-bis: " + this.applikationEinstellungen.getZeiten().get(4) + "-" + this.applikationEinstellungen.getZeiten().get(5) + System.getProperty("line.separator"));
            Einstellungen.append("Zeit4 Von-bis: " + this.applikationEinstellungen.getZeiten().get(6) + "-" + this.applikationEinstellungen.getZeiten().get(7) + System.getProperty("line.separator"));

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
            sqLiteDatabase = db;
            sqLiteDatabase.execSQL(CREATE_TABLE_CONTACT);
            Log.i("Datenbank","Tabelle Kontakt erstellt");
            for (int i = 0; i < 5; i++) {
                sqLiteDatabase.execSQL("INSERT INTO " + NotfallKontakt.tableName + " VALUES(" + i + ",0,NULL,0123456789);");
                Log.i("Datenbank","Dummy Kontakt erstellt");
            }
            sqLiteDatabase.execSQL(CREATE_TABLE_APPLICATION);
            Log.i("Datenbank","Applikationsdatenbank erstellt");
            sqLiteDatabase.execSQL("INSERT INTO " + ApplikationEinstellungen.tableName + " VALUES (0,'Rüstiger Rentner',1," + applikationEinstellungen.getMaximaleAnzahlInaktiveBewegungen() + "," + applikationEinstellungen.getMonitorServiceInterval() + ",'00:00','00:00','00:00','00:00','00:00','00:00','00:00','00:00' )");
            Log.i("Datenbank","Eintrag eingefügt");
        }
        catch (Exception e)
        {
            Log.e("Datenbank","Fehler beim Erstellen der Datenbanken. Fehlermeldung:" + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_CONTACT);
        db.execSQL(DROP_TABLE_APPLICATION);
        onCreate(db);
        Log.i("Datenbank","Upgrade der Datenbank");
    }


    public void set(NotfallKontakt contact) {
        try {
            if (sqLiteDatabase == null || sqLiteDatabase.isReadOnly())
                sqLiteDatabase = getWritableDatabase();
            ContentValues contentValue = new ContentValues();
            contentValue.put(NotfallKontakt.col_Priority, contact.getPrioritaet().ordinal());
            contentValue.put(NotfallKontakt.col_Icon, contact.getIcon().ordinal());
            contentValue.put(NotfallKontakt.col_Description, contact.getBeschreibung());
            contentValue.put(NotfallKontakt.col_Telephone, contact.getAlarmTelefonNummer());
            sqLiteDatabase.update(NotfallKontakt.tableName, contentValue, NotfallKontakt.col_Priority + "= ?", new String[]{String.valueOf(contact.getPrioritaet().ordinal())});
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
        if (sqLiteDatabase == null)
            sqLiteDatabase = getReadableDatabase();
        ArrayList<NotfallKontakt> Contacts = new ArrayList<NotfallKontakt>();
        String[] tableColumns = new String[]{NotfallKontakt.col_Priority, NotfallKontakt.col_Icon, NotfallKontakt.col_Description, NotfallKontakt.col_Telephone};
        try {
            Cursor c = sqLiteDatabase.query(NotfallKontakt.tableName, tableColumns, null, null,
                    null, null, null);
            if (c != null) {
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    NotfallKontakt contact = new NotfallKontakt();
                    contact.setPriority(c.getInt(c.getColumnIndex(NotfallKontakt.col_Priority)));
                    contact.setIcon(c.getInt(c.getColumnIndex(NotfallKontakt.col_Icon)));
                    contact.setBeschreibung(c.getString(c.getColumnIndex(NotfallKontakt.col_Description)));
                    contact.setAlarmTelefonNummer(c.getString(c.getColumnIndex(NotfallKontakt.col_Telephone)));

                    StringBuilder kontaktstring = new StringBuilder();
                    kontaktstring.append("Priorität " + contact.getPrioritaet() + System.getProperty("line.separator"));
                    kontaktstring.append("Icon " + contact.getIcon() + System.getProperty("line.separator"));
                    kontaktstring.append("Beschreibung " + contact.getBeschreibung() + System.getProperty("line.separator"));
                    kontaktstring.append("Telefonnummer " + contact.getAlarmTelefonNummer() + System.getProperty("line.separator"));
                    Log.d("Datenbank","Kontakt geladen: " + kontaktstring.toString());

                    Contacts.add(contact);
                }
            }
        } catch (Exception e) {
            Log.e("Datenbank","Fehler beim Laden der Notfallkontakte. Fehlermeldung: " +e.getMessage());
            return null;
        }
        return Contacts;
    }

    public NotfallKontakt getNotfallKontakt(NotfallKontakt.Prioritaet prioritaet) {
        try {
            if (sqLiteDatabase == null)
                sqLiteDatabase = getReadableDatabase();
            String[] tableColumns = new String[]{NotfallKontakt.col_Priority, NotfallKontakt.col_Icon, NotfallKontakt.col_Description, NotfallKontakt.col_Telephone};
            Cursor c = sqLiteDatabase.query(NotfallKontakt.tableName, tableColumns, NotfallKontakt.col_Priority + "=" + prioritaet.ordinal(), null,
                    null, null, null);
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                NotfallKontakt contact = new NotfallKontakt();
                contact.setPriority(c.getInt(c.getColumnIndex(NotfallKontakt.col_Priority)));
                contact.setIcon(c.getInt(c.getColumnIndex(NotfallKontakt.col_Icon)));
                contact.setBeschreibung(c.getString(c.getColumnIndex(NotfallKontakt.col_Description)));
                contact.setAlarmTelefonNummer(c.getString(c.getColumnIndex(NotfallKontakt.col_Telephone)));

                StringBuilder kontaktstring = new StringBuilder();
                kontaktstring.append("Priorität " + contact.getPrioritaet() + System.getProperty("line.separator"));
                kontaktstring.append("Icon " + contact.getIcon() + System.getProperty("line.separator"));
                kontaktstring.append("Beschreibung " + contact.getBeschreibung() + System.getProperty("line.separator"));
                kontaktstring.append("Telefonnummer " + contact.getAlarmTelefonNummer() + System.getProperty("line.separator"));
                Log.d("Datenbank","Einzelner Notfallkontakt geladen: " + kontaktstring.toString());

                return contact;
            }
        } catch (Exception e) {
            Log.e("Datenbank","Fehler beim Laden eines Notfallkontakts. Fehlermeldung: " +e.getMessage());
            return null;
        }

        return null;
    }
}

