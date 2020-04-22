package schutzengel.com.safelifemonitor;

public class NotfallKontakt {
    public static final String tableName = "Contacts";
    public static final String col_Priority = "Priority";
    public static final String col_Icon = "Icon";
    public static final String col_Description = "Description";
    public static final String col_Telephone = "Telephone";
    private String beschreibung = "";
    private String alarmTelefonNummer = "";

    public enum Prioritaet {
        Prioritaet_1,
        Prioritaet_2,
        Prioritaet_3,
        Prioritaet_4,
        Prioritaet_5,
        // This must be last definition
        Maximal
    }
    private Prioritaet prioritaet = Prioritaet.Prioritaet_1;

    public enum Icon {
        Schutzengel,
        Person,
        Institution,
        Hospital,
        Police,
        FireFighter
    }
    private Icon icon = Icon.Schutzengel;

    public enum Field {
        Priority,
        Icon,
        Description,
        AlarmTelephoneNumber
    }

    public Prioritaet getPrioritaet() {
        return this.prioritaet;
    }

    public void setPriorität(Prioritaet prioritaet) {
        this.prioritaet = prioritaet;
    }

    public static Prioritaet toPriority(Integer priority) {
        switch (priority) {
            case 0:
                return Prioritaet.Prioritaet_1;
            case 1:
                return Prioritaet.Prioritaet_2;
            case 2:
                return Prioritaet.Prioritaet_3;
            case 3:
                return Prioritaet.Prioritaet_4;
            case 4:
                return Prioritaet.Prioritaet_5;
            default:
                break;
        }
        return Prioritaet.Maximal;
    }

    public void setPriority(Integer priority) {
        switch(priority)
        {
            case 0:
                this.prioritaet = prioritaet.Prioritaet_1;
                break;
            case 1:
                this.prioritaet = prioritaet.Prioritaet_2;
                break;
            case 2:
                this.prioritaet = prioritaet.Prioritaet_3;
                break;
            case 3:
                this.prioritaet = prioritaet.Prioritaet_4;
                break;
            case 4:
                this.prioritaet = prioritaet.Prioritaet_5;
                break;
            default:
                this.prioritaet = prioritaet.Maximal;
                break;

        }
    }

    public int getDrawable() {
        switch (getIcon())
        {
            case Schutzengel:
                return R.drawable.schutzengel;
            case Person:
                return R.drawable.person;
            case Institution:
                return R.drawable.institution;
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
    public int getSmallDrawable() {
        switch (getIcon())
        {
            case Schutzengel:
                return R.drawable.schutzengel_32;
            case Person:
                return R.drawable.person_32;
            case Institution:
                return R.drawable.institution_32;
            case Hospital:
                return R.drawable.hospital_32;
            case Police:
                return R.drawable.police_32;
            case FireFighter:
                return R.drawable.firefighter_32;
            default:
                return 0;
        }
    }

    public Icon getIcon() {
        return this.icon;
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

    public String getBeschreibung() {
        return this.beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getAlarmTelefonNummer() {
        return this.alarmTelefonNummer;
    }

    public void setAlarmTelefonNummer(String alarmTelefonNummer) {
        this.alarmTelefonNummer = alarmTelefonNummer;
    }


}