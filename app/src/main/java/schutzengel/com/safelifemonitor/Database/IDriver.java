package schutzengel.com.safelifemonitor.Database;

import java.util.ArrayList;

public interface IDriver {
    void runCommand(String command);
    void runQuery(String command);
    void insertContact(ContactProperties contact);
    ArrayList<ContactProperties> getAllContacts();
    ContactProperties getContact(ContactProperties.Priority priority);
}
