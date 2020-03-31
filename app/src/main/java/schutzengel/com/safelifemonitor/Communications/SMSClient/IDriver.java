package schutzengel.com.safelifemonitor.Communications.SMSClient;

public interface IDriver {
    void write(Message message);
    Message read();
}
