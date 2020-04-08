package schutzengel.com.safelifemonitor.Database;

import java.util.ArrayList;

public class Database implements IDatabase {
    private ArrayList<ContactProperties> contactProperties = null;
    private ApplicationProperties applicationProperties = null;
    private IDriver driver = null;

    public Database(IDriver driver) {
        this.driver = driver;
        this.contactProperties = new ArrayList<>();
        this.applicationProperties = new ApplicationProperties();
    }

    public ArrayList<ContactProperties> getContacts() {
        this.contactProperties.clear();
        this.contactProperties = this.driver.getAllContacts();
        return this.contactProperties;
    }

    public ApplicationProperties getApplicationProperties() {
        return this.applicationProperties;
    }

    public ContactProperties getContactProperties(ContactProperties.Priority priority)
    {
        return this.driver.getContact(priority);
    }

    public void set(ContactProperties properties) {
        this.driver.insertContact(properties);
    }


    public void set(ArrayList<ContactProperties> properties) {
        for (ContactProperties contactProperties : properties) {
            this.driver.insertContact(contactProperties);
        }
    }

    public void set(ApplicationProperties properties) {

    }
}

