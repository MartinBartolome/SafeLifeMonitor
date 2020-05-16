package schutzengel.com.safelifemonitor;

public class SmsBenachrichtigung {
    private String telefonNummer = "";
    private String text = "";

    /**
     * Erstellen einer SmsBenachrichtigung
     *
     * @param telefonNummer
     * @param text
     */
    public SmsBenachrichtigung(String telefonNummer, String text) {
        this.telefonNummer = telefonNummer;
        this.text = text;
    }

    /**
     * Holen der Telefonnummer
     *
     * @return Telefonnummer
     */
    public String getTelefonNummer() {
        return this.telefonNummer;
    }

    /**
     * Holen des Textes
     *
     * @return Text
     */
    public String getText() {
        return this.text;
    }
}