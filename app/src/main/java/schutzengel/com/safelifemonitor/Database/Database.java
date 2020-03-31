package schutzengel.com.safelifemonitor.Database;

import java.util.ArrayList;
import schutzengel.com.safelifemonitor.Database.ApplicationProperties;
import schutzengel.com.safelifemonitor.Database.ContactProperties;

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
        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append("SELECT * FROM ");
        sqlCommand.append(ContactProperties.getTableName());
        this.driver.runQuery(sqlCommand.toString());
//// To do
        return this.contactProperties;
    }

    public ApplicationProperties getApplicationProperties() {
        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append("SELECT * FROM ");
        sqlCommand.append(ApplicationProperties.getTableName());
        this.driver.runQuery(sqlCommand.toString());
        // To do...
        return this.applicationProperties;
    }

    public void set(ContactProperties properties) {

    }


    public void set(ArrayList<ContactProperties> properties) {
        StringBuilder sqlCommand = new StringBuilder();
        // Delete all rows
        sqlCommand.append("DELETE FROM ");
        sqlCommand.append(ContactProperties.getTableName());
        this.driver.runCommand(sqlCommand.toString());
        // Insert rows
        sqlCommand = new StringBuilder();
        sqlCommand.append("INSERT INTO ");
        sqlCommand.append(ContactProperties.getTableName());
        sqlCommand.append(" VALUES ");
        boolean firstRow = true;
        for (ContactProperties contactProperties : properties) {
            if (!firstRow) {
                sqlCommand.append(",");
            }
            firstRow = false;
            sqlCommand.append("(");
            sqlCommand.append(contactProperties.getPriority().ordinal());
            sqlCommand.append(",");
            sqlCommand.append(contactProperties.getIcon().ordinal());
            sqlCommand.append(",");
            sqlCommand.append(contactProperties.getDescription());
            sqlCommand.append(",");
            sqlCommand.append(contactProperties.getAlarmTelephoneNumber());
            sqlCommand.append(")");
        }
        this.driver.runCommand(sqlCommand.toString());
    }

    public void set(ApplicationProperties properties) {
        StringBuilder sqlCommand = new StringBuilder();
        // Delete all rows
        sqlCommand.append("DELETE FROM ");
        sqlCommand.append(ApplicationProperties.getTableName());
        this.driver.runCommand(sqlCommand.toString());
        // Insert rows
        sqlCommand = new StringBuilder();
        sqlCommand.append("INSERT INTO ");
        sqlCommand.append(ApplicationProperties.getTableName());
        sqlCommand.append(" VALUES ");
        // To do
        this.driver.runCommand(sqlCommand.toString());
    }
}

