package schutzengel.com.safelifemonitor.Communications.SmsClient;

public interface IDriver {
    void write(Message message);
    Message read();
}
