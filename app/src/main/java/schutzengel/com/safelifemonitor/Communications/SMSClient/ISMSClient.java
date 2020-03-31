package schutzengel.com.safelifemonitor.Communications.SMSClient;

public interface ISMSClient {
    Message send(String subscriberNumber, String content);
    Message receive();
}