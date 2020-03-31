package schutzengel.com.safelifemonitor.Communications.SMSClient;

public class SMSClient implements ISMSClient {
    protected IDriver driver = null;

    public SMSClient(IDriver driver) {
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