package schutzengel.com.safelifemonitor;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class Datenbank extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatenbank = null;
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

    /**
     * Laden einer Datenbank Instanz
     *
     * @return Datenbank Instanz
     */
    public static Datenbank getInstanz() {
        if (null == instanz) {
            instanz = new Datenbank();
        }
        return instanz;
    }

    /**
     * Konstruktor der Datenbank Klasse
     */
    public Datenbank() {
        super(HauptActivity.context, DB_NAME, null, DB_VERSION);
        this.applikationsEinstellungen = new ApplikationEinstellungen();
    }

    /**
     * Laden der Applikationseinstellungen
     *
     * @return Applikationseinstellungen
     */
    public ApplikationEinstellungen getApplikationsEinstellungen() {
        try {
            if (this.sqLiteDatenbank == null) {
                this.sqLiteDatenbank = getReadableDatabase();
            }
            String[] tabellenspalten = new String[] {
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
            Cursor cursors = this.sqLiteDatenbank.query(ApplikationEinstellungen.TabellenName, tabellenspalten, null, null, null, null, null);
            for (cursors.moveToFirst(); !cursors.isAfterLast(); cursors.moveToNext()) {
                this.applikationsEinstellungen.setMonitorAktiv(cursors.getInt(cursors.getColumnIndex(ApplikationEinstellungen.col_MonitorEnabled)));
                this.applikationsEinstellungen.setBenutzerName(cursors.getString(cursors.getColumnIndex(ApplikationEinstellungen.col_BenutzerName)));
                this.applikationsEinstellungen.setSchwellwertBewegungssensor(cursors.getInt(cursors.getColumnIndex(ApplikationEinstellungen.col_Schwellwert)));
                this.applikationsEinstellungen.setMaximaleAnzahlInaktiveBewegungen(cursors.getInt(cursors.getColumnIndex(ApplikationEinstellungen.col_MaxInactive)));
                this.applikationsEinstellungen.setMonitorServiceInterval(cursors.getInt(cursors.getColumnIndex(ApplikationEinstellungen.col_Intervall)));
                ArrayList<String> zeiten = new ArrayList<>();
                zeiten.add((cursors.getString(cursors.getColumnIndex(ApplikationEinstellungen.col_Zeit1Von))));
                zeiten.add((cursors.getString(cursors.getColumnIndex(ApplikationEinstellungen.col_Zeit1Bis))));
                zeiten.add((cursors.getString(cursors.getColumnIndex(ApplikationEinstellungen.col_Zeit2Von))));
                zeiten.add((cursors.getString(cursors.getColumnIndex(ApplikationEinstellungen.col_Zeit2Bis))));
                zeiten.add((cursors.getString(cursors.getColumnIndex(ApplikationEinstellungen.col_Zeit3Von))));
                zeiten.add((cursors.getString(cursors.getColumnIndex(ApplikationEinstellungen.col_Zeit3Bis))));
                zeiten.add((cursors.getString(cursors.getColumnIndex(ApplikationEinstellungen.col_Zeit4Von))));
                zeiten.add((cursors.getString(cursors.getColumnIndex(ApplikationEinstellungen.col_Zeit4Bis))));
                this.applikationsEinstellungen.setZeiten(zeiten);
                StringBuilder einstellungen = new StringBuilder();
                einstellungen.append("Monitoring Erlaubt? " + this.applikationsEinstellungen.getMonitorAktiv() + System.getProperty("line.separator"));
                einstellungen.append("Username: " + this.applikationsEinstellungen.getBenutzerName() + System.getProperty("line.separator"));
                einstellungen.append("Schwellwert: " + this.applikationsEinstellungen.getSchwellwertBewegungssensor() + System.getProperty("line.separator"));
                einstellungen.append("Maximale Anzahl Inaktive Bewegungen: " + this.applikationsEinstellungen.getMaximaleAnzahlInaktiveBewegungen() + System.getProperty("line.separator"));
                einstellungen.append("MonitorServiceInterval: " + this.applikationsEinstellungen.getMonitorServiceInterval() + System.getProperty("line.separator"));
                einstellungen.append("Zeit1 Von-bis: " + this.applikationsEinstellungen.getZeiten().get(0) + "-" + this.applikationsEinstellungen.getZeiten().get(1) + System.getProperty("line.separator"));
                einstellungen.append("Zeit2 Von-bis: " + this.applikationsEinstellungen.getZeiten().get(2) + "-" + this.applikationsEinstellungen.getZeiten().get(3) + System.getProperty("line.separator"));
                einstellungen.append("Zeit3 Von-bis: " + this.applikationsEinstellungen.getZeiten().get(4) + "-" + this.applikationsEinstellungen.getZeiten().get(5) + System.getProperty("line.separator"));
                einstellungen.append("Zeit4 Von-bis: " + this.applikationsEinstellungen.getZeiten().get(6) + "-" + this.applikationsEinstellungen.getZeiten().get(7) + System.getProperty("line.separator"));
                Log.d("Datenbank", "Lesen von der Datenbank: " + einstellungen.toString());
            }
        } catch (Exception e) {
            Log.e("Datenbank", "Fehler beim Lesen der Applikationseinstellungen. Fehlermeldung: " + e.getMessage());
            return null;
        }
        return this.applikationsEinstellungen;
    }

    /**
     * Setzen der Applikationseinstellungen
     *
     * @param applikationsEinstellungen
     */
    public void set(ApplikationEinstellungen applikationsEinstellungen) {
        try {
            if (this.sqLiteDatenbank == null || sqLiteDatenbank.isReadOnly()) {
                this.sqLiteDatenbank = getWritableDatabase();
            }
            ContentValues contentValue = new ContentValues();
            contentValue.put(ApplikationEinstellungen.col_MonitorEnabled, applikationsEinstellungen.getMonitorAktiv());
            contentValue.put(ApplikationEinstellungen.col_BenutzerName, applikationsEinstellungen.getBenutzerName());
            contentValue.put(ApplikationEinstellungen.col_Schwellwert, applikationsEinstellungen.getSchwellwertBewegungssensor());
            contentValue.put(ApplikationEinstellungen.col_MaxInactive, applikationsEinstellungen.getMaximaleAnzahlInaktiveBewegungen());
            contentValue.put(ApplikationEinstellungen.col_Intervall, applikationsEinstellungen.getMonitorServiceInterval());
            int zaehler = 0;
            for (String zeit : applikationsEinstellungen.getZeiten()) {
                switch (zaehler) {
                    case 0:
                        contentValue.put(ApplikationEinstellungen.col_Zeit1Von, zeit);
                        break;
                    case 1:
                        contentValue.put(ApplikationEinstellungen.col_Zeit1Bis, zeit);
                        break;
                    case 2:
                        contentValue.put(ApplikationEinstellungen.col_Zeit2Von, zeit);
                        break;
                    case 3:
                        contentValue.put(ApplikationEinstellungen.col_Zeit2Bis, zeit);
                        break;
                    case 4:
                        contentValue.put(ApplikationEinstellungen.col_Zeit3Von, zeit);
                        break;
                    case 5:
                        contentValue.put(ApplikationEinstellungen.col_Zeit3Bis, zeit);
                        break;
                    case 6:
                        contentValue.put(ApplikationEinstellungen.col_Zeit4Von, zeit);
                        break;
                    case 7:
                        contentValue.put(ApplikationEinstellungen.col_Zeit4Bis, zeit);
                        break;
                }
                zaehler++;
            }
            this.sqLiteDatenbank.update(ApplikationEinstellungen.TabellenName, contentValue, null, null);
            this.applikationsEinstellungen = applikationsEinstellungen;
            StringBuilder einstellungen = new StringBuilder();
            einstellungen.append("Monitoring Erlaubt? " + this.applikationsEinstellungen.getMonitorAktiv() + System.getProperty("line.separator"));
            einstellungen.append("Username: " + this.applikationsEinstellungen.getBenutzerName() + System.getProperty("line.separator"));
            einstellungen.append("Schwellwert: " + this.applikationsEinstellungen.getSchwellwertBewegungssensor() + System.getProperty("line.separator"));
            einstellungen.append("Maximale Anzahl Inaktive Bewegungen: " + this.applikationsEinstellungen.getMaximaleAnzahlInaktiveBewegungen() + System.getProperty("line.separator"));
            einstellungen.append("MonitorServiceInterval: " + this.applikationsEinstellungen.getMonitorServiceInterval() + System.getProperty("line.separator"));
            einstellungen.append("Zeit1 Von-bis: " + this.applikationsEinstellungen.getZeiten().get(0) + "-" + this.applikationsEinstellungen.getZeiten().get(1) + System.getProperty("line.separator"));
            einstellungen.append("Zeit2 Von-bis: " + this.applikationsEinstellungen.getZeiten().get(2) + "-" + this.applikationsEinstellungen.getZeiten().get(3) + System.getProperty("line.separator"));
            einstellungen.append("Zeit3 Von-bis: " + this.applikationsEinstellungen.getZeiten().get(4) + "-" + this.applikationsEinstellungen.getZeiten().get(5) + System.getProperty("line.separator"));
            einstellungen.append("Zeit4 Von-bis: " + this.applikationsEinstellungen.getZeiten().get(6) + "-" + this.applikationsEinstellungen.getZeiten().get(7) + System.getProperty("line.separator"));
            Log.d("Datenbank", "Schreiben in die Datenbank: " + einstellungen.toString());
        } catch (Exception e) {
            Log.e("Datenbank", "Fehler beim schreiben in die Datenbank. Fehlermeldung: " + e.getMessage());
        }
    }

    /**
     * Erstellen der Tabelle für die Kontakte und der Applikationseinstellungen, sowie füllen der Tabellen mit Daten
     *
     * @param database
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        try {
            this.sqLiteDatenbank = database;
            this.sqLiteDatenbank.execSQL(ERSTELLE_TABELLE_KONTAKT);
            Log.i("Datenbank", "Tabelle Kontakt erstellt");
            for (int index = 0; index < 5; index++) {
                this.sqLiteDatenbank.execSQL("INSERT INTO " + NotfallKontakt.TabellenName + " VALUES(" + index + ",0,NULL,0123456789);");
                Log.i("Datenbank", "Dummy Kontakt erstellt");
            }
            this.sqLiteDatenbank.execSQL(ERSTELLE_TABELLE_APPLIKATION);
            Log.i("Datenbank", "Applikationsdatenbank erstellt");
            this..execSQL("INSERT INTO " + ApplikationEinstellungen.TabellenName + " VALUES (0,'Rüstiger Rentner',1," + applikationsEinstellungen.getMaximaleAnzahlInaktiveBewegungen() + "," + applikationsEinstellungen.getMonitorServiceInterval() + ",'00:00','00:00','00:00','00:00','00:00','00:00','00:00','00:00' )");
            Log.i("Datenbank", "Eintrag eingefügt");
        } catch (Exception e) {
            Log.e("Datenbank", "Fehler beim Erstellen der Datenbanken. Fehlermeldung:" + e.getMessage());
        }
    }

    /**
     * Bei Upgrade der Datenbank werden die Tabellen gelöscht und neu erstellt
     *
     * @param database
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL(LOESCHE_TABELLE_KONTAKT);
        database.execSQL(LOESCHE_TABELLE_APPLIKATION);
        onCreate(database);
        Log.i("Datenbank", "Upgrade der Datenbank");
    }

    /**
     * Updaten eines Kontaktes in der Datenbank anhand der Prioritaet
     *
     * @param kontakt
     */
    public void set(NotfallKontakt kontakt) {
        try {
            if (this.sqLiteDatenbank == null || this.sqLiteDatenbank.isReadOnly() {
                this.sqLiteDatenbank = getWritableDatabase();
            }
            ContentValues contentValue = new ContentValues();
            contentValue.put(NotfallKontakt.col_Prioritaet, kontakt.getPrioritaet().ordinal());
            contentValue.put(NotfallKontakt.col_Icon, kontakt.getIcon().ordinal());
            contentValue.put(NotfallKontakt.col_Beschreibung, kontakt.getBeschreibung());
            contentValue.put(NotfallKontakt.col_Telefon, kontakt.getAlarmTelefonNummer());
            this.sqLiteDatenbank.update(NotfallKontakt.TabellenName, contentValue, NotfallKontakt.col_Prioritaet + "= ?", new String[]{String.valueOf(kontakt.getPrioritaet().ordinal())});
            StringBuilder kontaktstring = new StringBuilder();
            kontaktstring.append("Priorität " + kontakt.getPrioritaet() + System.getProperty("line.separator"));
            kontaktstring.append("Icon " + kontakt.getIcon() + System.getProperty("line.separator"));
            kontaktstring.append("Beschreibung " + kontakt.getBeschreibung() + System.getProperty("line.separator"));
            kontaktstring.append("Telefonnummer " + kontakt.getAlarmTelefonNummer() + System.getProperty("line.separator"));
            Log.d("Datenbank", "Kontakt aktualisiert: " + kontaktstring.toString());
        } catch (Exception e) {
            Log.e("Datenbank", "Kontakt konnte nicht aktualisiert werden. Fehlermeldung: " + e.getMessage());
        }
    }

    /**
     * Setzen aller Kontakte
     *
     * @param notfallKontakte
     */
    public void set(ArrayList<NotfallKontakt> notfallKontakte) {
        for (NotfallKontakt notfallKontakt : notfallKontakte) {
            set(notfallKontakt);
        }
    }

    /**
     * Holen aller Notfallkontakte
     *
     * @return Notfallkontakte
     */
    public ArrayList<NotfallKontakt> getNotfallKontakte() {
        if (this.sqLiteDatenbank == null {
            sqLiteDatenbank = getReadableDatabase();
        }
        ArrayList<NotfallKontakt> kontakte = new ArrayList<NotfallKontakt>();
        String[] tableColumns = new String[] { NotfallKontakt.col_Prioritaet, NotfallKontakt.col_Icon, NotfallKontakt.col_Beschreibung, NotfallKontakt.col_Telefon};
        try {
            Cursor cursor = this.sqLiteDatenbank.query(NotfallKontakt.TabellenName, tableColumns, null, null, null, null, null);
            if (cursor != null) {
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    NotfallKontakt kontakt = new NotfallKontakt();
                    kontakt.setPrioritaet(cursor.getInt(cursor.getColumnIndex(NotfallKontakt.col_Prioritaet)));
                    kontakt.setIcon(cursor.getInt(cursor.getColumnIndex(NotfallKontakt.col_Icon)));
                    kontakt.setBeschreibung(cursor.getString(cursor.getColumnIndex(NotfallKontakt.col_Beschreibung)));
                    kontakt.setAlarmTelefonNummer(cursor.getString(cursor.getColumnIndex(NotfallKontakt.col_Telefon)));
                    StringBuilder kontaktstring = new StringBuilder();
                    kontaktstring.append("Priorität " + kontakt.getPrioritaet() + System.getProperty("line.separator"));
                    kontaktstring.append("Icon " + kontakt.getIcon() + System.getProperty("line.separator"));
                    kontaktstring.append("Beschreibung " + kontakt.getBeschreibung() + System.getProperty("line.separator"));
                    kontaktstring.append("Telefonnummer " + kontakt.getAlarmTelefonNummer() + System.getProperty("line.separator"));
                    Log.d("Datenbank", "Kontakt geladen: " + kontaktstring.toString());
                    kontakte.add(kontakt);
                }
            }
        } catch (Exception e) {
            Log.e("Datenbank", "Fehler beim Laden der Notfallkontakte. Fehlermeldung: " + e.getMessage());
            return null;
        }
        return kontakte;
    }

    /**
     * Holen eines Notfallkontakts anhand der Prioritaet
     *
     * @param prioritaet
     * @return Notfallkontakt
     */
    public NotfallKontakt getNotfallKontakt(NotfallKontakt.Prioritaet prioritaet) {
        try {
            if (sqLiteDatenbank == null) {
                sqLiteDatenbank = getReadableDatabase();
            }
            String[] tableColumns = new String[] { NotfallKontakt.col_Prioritaet, NotfallKontakt.col_Icon, NotfallKontakt.col_Beschreibung, NotfallKontakt.col_Telefon};
            Cursor cursor = sqLiteDatenbank.query(NotfallKontakt.TabellenName, tableColumns, NotfallKontakt.col_Prioritaet + "=" + prioritaet.ordinal(), null, null, null, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                NotfallKontakt kontakt = new NotfallKontakt();
                kontakt.setPrioritaet(cursor.getInt(cursor.getColumnIndex(NotfallKontakt.col_Prioritaet)));
                kontakt.setIcon(cursor.getInt(cursor.getColumnIndex(NotfallKontakt.col_Icon)));
                kontakt.setBeschreibung(cursor.getString(cursor.getColumnIndex(NotfallKontakt.col_Beschreibung)));
                kontakt.setAlarmTelefonNummer(cursor.getString(cursor.getColumnIndex(NotfallKontakt.col_Telefon)));
                StringBuilder kontaktstring = new StringBuilder();
                kontaktstring.append("Priorität " + kontakt.getPrioritaet() + System.getProperty("line.separator"));
                kontaktstring.append("Icon " + kontakt.getIcon() + System.getProperty("line.separator"));
                kontaktstring.append("Beschreibung " + kontakt.getBeschreibung() + System.getProperty("line.separator"));
                kontaktstring.append("Telefonnummer " + kontakt.getAlarmTelefonNummer() + System.getProperty("line.separator"));
                Log.d("Datenbank", "Einzelner Notfallkontakt geladen: " + kontaktstring.toString());
                return kontakt;
            }
        } catch (Exception e) {
            Log.e("Datenbank", "Fehler beim Laden eines Notfallkontakts. Fehlermeldung: " + e.getMessage());
            return null;
        }
        return null;
    }
}

