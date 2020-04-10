package schutzengel.com.safelifemonitor.Communications.SmsClient;

public interface ISmsClient {
    Message send(String subscriberNumber, String content);
    Message receive();
}