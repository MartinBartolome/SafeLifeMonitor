package schutzengel.com.safelifemonitor;

public class SmsClient {
    public SmsBenachrichtigung senden(String telefonNummer, String text) {
        SmsBenachrichtigung message = new SmsBenachrichtigung(telefonNummer, text);
        return message;
    }

    public Boolean smsEmpfangen() {
        return false;
    }
}