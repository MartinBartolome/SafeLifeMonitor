package schutzengel.com.safelifemonitor.Core.Factory;

public interface IFactory {
    void create();
    void initialize();
    void startup();
    void shutdown();
}