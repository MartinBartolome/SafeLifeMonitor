package schutzengel.com.safelifemonitor.Database;

import java.util.ArrayList;
import schutzengel.com.safelifemonitor.Database.ApplicationProperties;
import schutzengel.com.safelifemonitor.Database.ContactProperties;

public interface IDatabase {
    ArrayList<ContactProperties> getContacts();
    ApplicationProperties getApplicationProperties();
    void set(ContactProperties properties);
    void set(ApplicationProperties properties);
}
