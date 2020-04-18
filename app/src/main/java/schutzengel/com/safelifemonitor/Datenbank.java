package schutzengel.com.safelifemonitor;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Datenbank extends SQLiteOpenHelper {
    SQLiteDatabase sqLiteDatabase;
    static final String DB_NAME = "SafeLifeMonitor.db";
    static final int DB_VERSION = 1;
    private static final String CREATE_TABLE = "create table " + NotfallKontakt.tableName + "(" + NotfallKontakt.col_Priority + " INTEGER NOT NULL," + NotfallKontakt.col_Icon + " INTEGER NOT NULL, " + NotfallKontakt.col_Description + " TEXT," + NotfallKontakt.col_Telephone + " TEXT NOT NULL);";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+ NotfallKontakt.tableName;

    private static Datenbank instance = null;
    private ApplikationEinstellungen applikationEinstellungen = null;

    public static Datenbank getInstance() {
        if (null == instance) {
            instance = new Datenbank();
        }
        return instance;
    }

    private  Datenbank() {
        super(HauptActivity.context, DB_NAME, null, DB_VERSION);
        this.applikationEinstellungen = new ApplikationEinstellungen();
    }

    public ApplikationEinstellungen getApplikationEinstellungen() {
        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append("SELECT * FROM ");
        sqlCommand.append(ApplikationEinstellungen.getTableName());
      //  this.driver.runQuery(sqlCommand.toString());
        // To do...
        return this.applikationEinstellungen;
    }

    public NotfallKontakt getNotfallKontakt(String telefonNummer)
    {
        // To do...
        return null;
    }

    public void set(ApplikationEinstellungen applikationEinstellungen) {
        StringBuilder sqlCommand = new StringBuilder();
        // Delete all rows
        sqlCommand.append("DELETE FROM ");
        sqlCommand.append(ApplikationEinstellungen.getTableName());
//        this.driver.runCommand(sqlCommand.toString());
        // Insert rows
        sqlCommand = new StringBuilder();
        sqlCommand.append("INSERT INTO ");
        sqlCommand.append(ApplikationEinstellungen.getTableName());
        sqlCommand.append(" VALUES ");
        // To do
  //      this.driver.runCommand(sqlCommand.toString());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sqLiteDatabase = db;
        sqLiteDatabase.execSQL(CREATE_TABLE);
        for (int i = 0; i < 5; i++) {
            sqLiteDatabase.execSQL("INSERT INTO " + NotfallKontakt.getTableName() + " (" + NotfallKontakt.col_Priority + "," + NotfallKontakt.col_Icon + "," + NotfallKontakt.col_Telephone + ") VALUES(" + i + ",0,0123456789);");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }


    public void set(NotfallKontakt contact) {
        if(sqLiteDatabase == null || sqLiteDatabase.isReadOnly())
            sqLiteDatabase = getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(NotfallKontakt.col_Priority,contact.getPrioritaet().ordinal());
        contentValue.put(NotfallKontakt.col_Icon,contact.getIcon().ordinal());
        contentValue.put(NotfallKontakt.col_Description,contact.getBeschreibung());
        contentValue.put(NotfallKontakt.col_Telephone,contact.getAlarmTelefonNummer());
        sqLiteDatabase.update(NotfallKontakt.tableName,contentValue,NotfallKontakt.col_Priority +  "= ?",new String[]{ String.valueOf(contact.getPrioritaet().ordinal()) });
    }

    public void set(ArrayList<NotfallKontakt> notfallKontakte) {
        for (NotfallKontakt notfallKontakt : notfallKontakte) {
            set(notfallKontakt);
        }
    }

    public ArrayList<NotfallKontakt> getNotfallKontakte()
    {
        sqLiteDatabase = this.getReadableDatabase();
        ArrayList<NotfallKontakt> Contacts = new ArrayList<NotfallKontakt>();
        String[] tableColumns  = new String[]{NotfallKontakt.col_Priority, NotfallKontakt.col_Icon, NotfallKontakt.col_Description, NotfallKontakt.col_Telephone};
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
                    Contacts.add(contact);
                }
            }
        }
        catch(Exception e)
        {
            return null;
        }
        return Contacts;
    }

    public NotfallKontakt getNotfallKontakt(int priority)
    {
        try
        {
            sqLiteDatabase = this.getReadableDatabase();
            String[] tableColumns  = new String[]{NotfallKontakt.col_Priority, NotfallKontakt.col_Icon, NotfallKontakt.col_Description, NotfallKontakt.col_Telephone};
            Cursor c = sqLiteDatabase.query(NotfallKontakt.tableName, tableColumns, NotfallKontakt.col_Priority +"=" + priority, null,
                    null, null, null);
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                NotfallKontakt contact = new NotfallKontakt();
                contact.setPriority(c.getInt(c.getColumnIndex(NotfallKontakt.col_Priority)));
                contact.setIcon(c.getInt(c.getColumnIndex(NotfallKontakt.col_Icon)));
                contact.setBeschreibung(c.getString(c.getColumnIndex(NotfallKontakt.col_Description)));
                contact.setAlarmTelefonNummer(c.getString(c.getColumnIndex(NotfallKontakt.col_Telephone)));
                return contact;
            }
        }
        catch (Exception e)
        {
            return null;
        }

        return null;
    }
}

