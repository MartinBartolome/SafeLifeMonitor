package schutzengel.com.safelifemonitor.Database;

import android.graphics.drawable.Drawable;

import schutzengel.com.safelifemonitor.R;

public class ContactProperties {
    public static final String tableName = "Contacts";
    public static final String col_Priority = "Priority";
    public static final String col_Icon = "Icon";
    public static final String col_Description = "Description";
    public static final String col_Telephone = "Telephone";
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

    public void setPriority(Integer priority) {
        switch(priority)
        {
            case 0:
                this.priority = Priority.Prority_1;
                break;
            case 1:
                this.priority = Priority.Prority_2;
                break;
            case 2:
                this.priority = Priority.Prority_3;
                break;
            case 3:
                this.priority = Priority.Prority_4;
                break;
            case 4:
                this.priority = Priority.Prority_5;
                break;
            default:
                this.priority = Priority.Maximal;
                break;

        }
    }

    public Icon getIcon() {
        return this.icon;
    }

    public int getDrawable() {
        switch (getIcon())
        {
            case Schutzengel:
                return R.drawable.schutzengel;
            case Person:
                return R.drawable.person;
            case Institution:
                return R.drawable.person;
            case Hospital:
                return R.drawable.hospital;
            case Police:
                return R.drawable.police;
            case FireFighter:
                return R.drawable.firefighter;
            default:
                return 0;
        }
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public void setIcon(Integer icon) {
        switch(icon)
        {
            case 0:
                this.icon = Icon.Schutzengel;
                break;
            case 1:
                this.icon = Icon.Person;
                break;
            case 2:
                this.icon = Icon.Institution;
                break;
            case 3:
                this.icon = Icon.Hospital;
                break;
            case 4:
                this.icon = Icon.Police;
                break;
            default:
                this.icon = Icon.FireFighter;
                break;

        }
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