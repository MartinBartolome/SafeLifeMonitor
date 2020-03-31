package schutzengel.com.safelifemonitor.Database;

public class ContactProperties {
    private String description = "";
    private String alarmTelephoneNumber = "";

    public enum Priority {
        Prority_1,
        Prority_2,
        Prority_3,
        Prority_4,
        Prority_5,
        // This must be last definition
        Maximal
    }
    private Priority priority = Priority.Prority_1;

    public enum Icon {
        Schutzengel,
        Person,
        Institution,
        Hospital,
        Police,
        FireFighter
    }
    private Icon icon = Icon.Schutzengel;

    public static String getTableName() {
        return "Contacts";
    }

    public enum Field {
        Priority,
        Icon,
        Description,
        AlarmTelephoneNumber
    }

    public Priority getPriority() {
        return this.priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Icon getIcon() {
        return this.icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlarmTelephoneNumber() {
        return this.alarmTelephoneNumber;
    }

    public void setAlarmTelephoneNumber(String telephoneNumber) {
        this.alarmTelephoneNumber = telephoneNumber;
    }
}