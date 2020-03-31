package schutzengel.com.safelifemonitor.Database;

import android.provider.ContactsContract;

import schutzengel.com.safelifemonitor.Core.Factory.IFactory;
import schutzengel.com.safelifemonitor.Database.Drivers.SQLite.Driver;

public class Factory implements IFactory {
    protected IDatabase database = null;

    public Factory() {
    }

    public void create() {
        IDriver driver = new Driver("");
        this.database = new Database(driver);
    }

    public void initialize() {
    }

    public void startup() {
    }

    public void shutdown() {
    }

    public IDatabase getDatabase() {
        return this.database;
    }
}