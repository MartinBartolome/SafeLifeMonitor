package schutzengel.com.safelifemonitor.Workflows.Alarming;

import schutzengel.com.safelifemonitor.Communications.SMSClient.ISMSClient;
import schutzengel.com.safelifemonitor.Communications.SMSClient.Message;
import schutzengel.com.safelifemonitor.Database.ContactProperties;
import schutzengel.com.safelifemonitor.Database.IDatabase;

public class Workflow extends schutzengel.com.safelifemonitor.Core.Workflows.Workflow {
    protected IDatabase database =  null;
    protected ISMSClient smsClient = null;

    @Override
    protected void prepare() {
        super.prepare();
        schutzengel.com.safelifemonitor.Core.Factory.Factory factory = schutzengel.com.safelifemonitor.Core.Factory.Factory.getInstance();
        this.database = factory.getFactoryDatabase().getDatabase();
        this.smsClient = factory.getFactoryCommunications().getSMSClient();
    }

    @Override
    protected Boolean running() {
        wait(this.database.getApplicationProperties().getReadShortMessagesInterval());
        return readShortMessages();
    }

    private Boolean readShortMessages() {
        // To do...
        Message message = this.smsClient.receive();
        // Parse content...
        Boolean isSuccess = false;
        // Success
        if (isSuccess) {
            // Get contact
            ContactProperties contactProperties = this.database.getContactProperties(message.getSubscriberNumber());
            EventAlarmConfirmation event = new EventAlarmConfirmation(contactProperties.getPriority().toString(), message.getContent());
            notify(event);
            return true;
        }
        return false;
    }
}