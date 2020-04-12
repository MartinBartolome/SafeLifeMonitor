package schutzengel.com.safelifemonitor.Communications.SmsClient;

import schutzengel.com.safelifemonitor.Communications.SmsClient.Drivers.Generic.Driver;
import schutzengel.com.safelifemonitor.Core.Factory.IFactory;

public class Factory implements IFactory {
    private ISmsClient smsClient = null;

    public Factory() {
    }

    public void create() {
        IDriver driver = new Driver();
        this.smsClient = new SmsClient(driver);
    }

    public void initialize() {
    }

    public void startup() {
    }

    public void shutdown() {
    }

    public ISmsClient getSmsClient() {
        return this.smsClient;
    }
}