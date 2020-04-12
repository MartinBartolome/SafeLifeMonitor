package schutzengel.com.safelifemonitor.Database.Drivers.SQLite;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import schutzengel.com.safelifemonitor.Database.ContactProperties;
import schutzengel.com.safelifemonitor.Database.IDriver;
import schutzengel.com.safelifemonitor.HMI.Main;

public class Driver extends SQLiteOpenHelper implements IDriver {
    SQLiteDatabase sqLiteDatabase;
    // Database Information
    static final String DB_NAME = "SafeLifeMonitor.db";
    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + ContactProperties.tableName + "(id INTEGER PRIMARY KEY AUTOINCREMENT, " + ContactProperties.col_Priority + " INTEGER NOT NULL," + ContactProperties.col_Icon + " INTEGER NOT NULL, " + ContactProperties.col_Description + " TEXT," + ContactProperties.col_Telephone + " TEXT NOT NULL);";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+ ContactProperties.tableName;

    public Driver() {
        super(Main.context, DB_NAME, null, DB_VERSION);
    }

    public void runCommand(String command) {
    }

    public void runQuery(String command) {
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sqLiteDatabase = db;
        sqLiteDatabase.execSQL(CREATE_TABLE);
        ContactProperties emptyContact = new ContactProperties();
        for(int i = 0;i<5;i++)
        {
            emptyContact.setPriority(i);
            insertContact(emptyContact);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    @Override
    public void insertContact(ContactProperties contact) {
        sqLiteDatabase = getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(ContactProperties.col_Priority,contact.getPriority().ordinal());
        contentValue.put(ContactProperties.col_Icon,contact.getIcon().ordinal());
        contentValue.put(ContactProperties.col_Description,contact.getDescription());
        contentValue.put(ContactProperties.col_Telephone,contact.getAlarmTelephoneNumber());
        sqLiteDatabase.insert(ContactProperties.tableName,null,contentValue);

    }
    @Override
    public ArrayList<ContactProperties> getAllContacts()
    {
        sqLiteDatabase = this.getReadableDatabase();
        ArrayList<ContactProperties> Contacts = new ArrayList<ContactProperties>();
        String[] tableColumns  = new String[]{ContactProperties.col_Priority, ContactProperties.col_Icon, ContactProperties.col_Description, ContactProperties.col_Telephone};
        try {
            Cursor c = sqLiteDatabase.query(ContactProperties.tableName, tableColumns, null, null,
                    null, null, null);
            if (c != null) {
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    ContactProperties contact = new ContactProperties();
                    contact.setPriority(c.getInt(c.getColumnIndex(ContactProperties.col_Priority)));
                    contact.setIcon(c.getInt(c.getColumnIndex(ContactProperties.col_Icon)));
                    contact.setDescription(c.getString(c.getColumnIndex(ContactProperties.col_Description)));
                    contact.setAlarmTelephoneNumber(c.getString(c.getColumnIndex(ContactProperties.col_Telephone)));
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

    @Override
    public ContactProperties getContact(ContactProperties.Priority priority)
    {
        sqLiteDatabase = this.getReadableDatabase();
        String[] tableColumns  = new String[]{ContactProperties.col_Priority, ContactProperties.col_Icon, ContactProperties.col_Description, ContactProperties.col_Telephone};
        Cursor c = sqLiteDatabase.query(ContactProperties.tableName, tableColumns, ContactProperties.col_Priority +"=" + priority.ordinal(), null,
                null, null, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            ContactProperties contact = new ContactProperties();
            contact.setPriority(c.getInt(c.getColumnIndex(ContactProperties.col_Priority)));
            contact.setIcon(c.getInt(c.getColumnIndex(ContactProperties.col_Icon)));
            contact.setDescription(c.getString(c.getColumnIndex(ContactProperties.col_Description)));
            contact.setAlarmTelephoneNumber(c.getString(c.getColumnIndex(ContactProperties.col_Telephone)));
            return contact;
        }
        return null;
    }
}
