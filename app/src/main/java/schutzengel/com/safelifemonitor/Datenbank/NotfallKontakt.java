package schutzengel.com.safelifemonitor.Datenbank;

import schutzengel.com.safelifemonitor.R;

public class NotfallKontakt {
    public static final String TabellenName = "Kontakte";
    public static final String col_Prioritaet = "Prioritaet";
    public static final String col_Icon = "Icon";
    public static final String col_Beschreibung = "Beschreibung";
    public static final String col_Telefon = "Telefon";
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
        Krankenhaus,
        Polizei,
        Feuerwehr
    }

    private Icon icon = Icon.Schutzengel;

    /**
     * Holen der Priorität
     *
     * @return Priorität
     */
    public Prioritaet getPrioritaet() {
        return this.prioritaet;
    }

    /**
     * Setzen der Priorität
     *
     * @param prioritaet
     */
    public void setPrioritaet(Prioritaet prioritaet) {
        this.prioritaet = prioritaet;
    }

    /**
     * Integer Wert zu Enum Wert
     *
     * @param prioritaet
     * @return Priorität
     */
    public static Prioritaet toPriority(Integer prioritaet) {
        switch (prioritaet) {
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

    /**
     * Setze die Priorität anhand eines Integer Wertes
     *
     * @param prioritaet
     */
    public void setPrioritaet(Integer prioritaet) {
        switch (prioritaet) {
            case 0:
                this.prioritaet = this.prioritaet.Prioritaet_1;
                break;
            case 1:
                this.prioritaet = this.prioritaet.Prioritaet_2;
                break;
            case 2:
                this.prioritaet = this.prioritaet.Prioritaet_3;
                break;
            case 3:
                this.prioritaet = this.prioritaet.Prioritaet_4;
                break;
            case 4:
                this.prioritaet = this.prioritaet.Prioritaet_5;
                break;
            default:
                this.prioritaet = this.prioritaet.Maximal;
                break;
        }
    }

    /**
     * Laden des Grossen Icons für die Kategorisierung des Kontakts
     *
     * @return
     */
    public int getBild() {
        switch (getIcon()) {
            case Schutzengel:
                return R.drawable.schutzengel;
            case Person:
                return R.drawable.person;
            case Institution:
                return R.drawable.institution;
            case Krankenhaus:
                return R.drawable.krankenhaus;
            case Polizei:
                return R.drawable.polizei;
            case Feuerwehr:
                return R.drawable.feuerwehr;
            default:
                return 0;
        }
    }

    /**
     * Laden des kleinen Icons für die Kategorisierung des Kontakts
     *
     * @return Icon
     */
    public int getkleinesBild() {
        switch (getIcon()) {
            case Schutzengel:
                return R.drawable.schutzengel_32;
            case Person:
                return R.drawable.person_32;
            case Institution:
                return R.drawable.institution_32;
            case Krankenhaus:
                return R.drawable.krankenhaus_32;
            case Polizei:
                return R.drawable.polizei_32;
            case Feuerwehr:
                return R.drawable.feuerwehr_32;
            default:
                return 0;
        }
    }

    /**
     * Holen des Icons
     *
     * @return icon
     */
    public Icon getIcon() {
        return this.icon;
    }

    /**
     * Setzen des Icons
     *
     * @param icon
     */
    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    /**
     * Setzen des Icons anhand eines Integer Wertes
     *
     * @param icon
     */
    public void setIcon(Integer icon) {
        switch (icon) {
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
                this.icon = Icon.Krankenhaus;
                break;
            case 4:
                this.icon = Icon.Polizei;
                break;
            default:
                this.icon = Icon.Feuerwehr;
                break;
        }
    }

    /**
     * Holen der Beschreibung
     *
     * @return Beschreibung
     */
    public String getBeschreibung() {
        return this.beschreibung;
    }

    /**
     * Setzen der Beschreibung
     *
     * @param beschreibung
     */
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    /**
     * Holen der Telefonnummer
     *
     * @return Telefonnummer
     */
    public String getAlarmTelefonNummer() {
        return this.alarmTelefonNummer;
    }

    /**
     * Setzen der Telefonnummer
     *
     * @param alarmTelefonNummer
     */
    public void setAlarmTelefonNummer(String alarmTelefonNummer) {
        this.alarmTelefonNummer = alarmTelefonNummer;
    }
}