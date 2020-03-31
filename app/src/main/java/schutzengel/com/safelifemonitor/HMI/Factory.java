package schutzengel.com.safelifemonitor.HMI;

import androidx.appcompat.app.AppCompatActivity;

import schutzengel.com.safelifemonitor.Core.Factory.IFactory;

public class Factory implements IFactory {
    private enum Type {
        Main,
        ApplicationProperties,
        ContactProperties,
        // Must be last definition
        Maximal
    }
    private IActivity[] activities = null;

    public Factory() {
    }

    public void create() {
        this.activities = new IActivity[Type.Maximal.ordinal()];
        this.activities[Type.Main.ordinal()] = new Main();
        this.activities[Type.ApplicationProperties.ordinal()] = new ApplicationProperties();
        this.activities[Type.ContactProperties.ordinal()] = new ContactProperties();
    }

    public void initialize() {
        for (IActivity activity : this.activities) {
            activity.initialize();
        }
    }

    public void startup() {
    }

    public void shutdown() {
    }

    public Main getMain() {
        return (Main)(this.activities[Type.Main.ordinal()]);
    }

    public ApplicationProperties getApplicationProperties() {
        return (ApplicationProperties)(this.activities[Type.ApplicationProperties.ordinal()]);
    }

    public ContactProperties getContactProperties() {
        return (ContactProperties)(this.activities[Type.ContactProperties.ordinal()]);
    }
}