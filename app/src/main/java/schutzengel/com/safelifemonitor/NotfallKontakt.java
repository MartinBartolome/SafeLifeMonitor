package schutzengel.com.safelifemonitor;

public class NotfallKontakt {
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

    public static String getTableName() {
        return "Contacts";
    }

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

    public Icon getIcon() {
        return this.icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
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