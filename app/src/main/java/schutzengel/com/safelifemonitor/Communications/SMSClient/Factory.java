package schutzengel.com.safelifemonitor.Communications.SmsClient;

import schutzengel.com.safelifemonitor.Communications.SmsClient.Drivers.Generic.Driver;
import schutzengel.com.safelifemonitor.Core.Factory.IFactory;

public class Factory implements IFactory {
    protected ISmsClient smsClient = null;
    @Override
    public void create() {
        IDriver driver = new Driver();
        this.smsClient = new SmsClient(driver);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void startup() {

    }

    @Override
    public void shutdown() {

    }

    public ISmsClient getSmsClient(){
        return this.smsClient;
    }
}
