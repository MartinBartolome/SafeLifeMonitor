package schutzengel.com.safelifemonitor.Communications.SmsClient;

public class SmsClient implements ISmsClient {
    protected IDriver driver = null;

    public SmsClient(IDriver driver) {
        this.driver = driver;
    }

    public Message send(String subscriberNumber, String content) {
        Message message = new Message(subscriberNumber, content);
        this.driver.write(message);
        return message;
    }

    public Message receive() {
        return this.driver.read();
    }
}