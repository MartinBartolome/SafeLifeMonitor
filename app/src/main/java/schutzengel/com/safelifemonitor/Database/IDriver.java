package schutzengel.com.safelifemonitor.Database;

public interface IDriver {
    void runCommand(String command);
    void runQuery(String command);
}
