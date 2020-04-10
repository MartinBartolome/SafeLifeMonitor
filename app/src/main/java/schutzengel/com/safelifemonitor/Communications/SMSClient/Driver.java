package schutzengel.com.safelifemonitor.Communications.SmsClient;

public abstract class Driver implements IDriver {
    public abstract  void write(Message message);
    public abstract Message read();
}
