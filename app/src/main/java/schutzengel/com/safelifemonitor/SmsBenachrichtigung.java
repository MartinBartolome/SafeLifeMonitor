package schutzengel.com.safelifemonitor;

public class SmsBenachrichtigung {
    private String telefonNummer = "";
    private String text = "";

    public SmsBenachrichtigung(String telefonNummer, String text) {
        this.telefonNummer = telefonNummer;
        this.text = text;
    }

    public String getTelefonNummer() {
        return this.telefonNummer;
    }

    public String getText() {
        return this.text;
    }
}