package schutzengel.com.safelifemonitor.Database.Drivers.SQLite;

import schutzengel.com.safelifemonitor.Database.IDriver;

public class Driver implements IDriver {
    private String fileNmae = "";

    public Driver(String fileName) {
        this.fileNmae = fileName;
    }

    public void runCommand(String command) {
    }

    public void runQuery(String command) {
    }
}
