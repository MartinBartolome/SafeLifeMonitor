package schutzengel.com.safelifemonitor.Database;

import java.util.ArrayList;

public interface IDatabase {
    ArrayList<ContactProperties> getContacts();
    ApplicationProperties getApplicationProperties();
    ContactProperties getContactProperties(String subscriberNumber);
    void set(ContactProperties properties);
    void set(ApplicationProperties properties);
}
