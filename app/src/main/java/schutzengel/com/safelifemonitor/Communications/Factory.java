package schutzengel.com.safelifemonitor.Communications;

import schutzengel.com.safelifemonitor.Communications.SMSClient.Drivers.Generic.Driver;
import schutzengel.com.safelifemonitor.Communications.SMSClient.IDriver;
import schutzengel.com.safelifemonitor.Communications.SMSClient.ISMSClient;
import schutzengel.com.safelifemonitor.Communications.SMSClient.SMSClient;
import schutzengel.com.safelifemonitor.Core.Factory.IFactory;

public class Factory implements IFactory {
    protected ISMSClient smsClient = null;

    public Factory() {
    }

    public void create() {
        IDriver driver = new Driver();
        this.smsClient = new SMSClient(driver);
    }

    public void initialize() {
    }

    public void startup() {
    }

    public void shutdown() {
    }

    public ISMSClient getSMSClient() {
        return this.smsClient;
    }
}