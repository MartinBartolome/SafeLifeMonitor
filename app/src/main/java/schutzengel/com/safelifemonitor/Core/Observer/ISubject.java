package schutzengel.com.safelifemonitor.Core.Observer;

public interface ISubject {
    void register(IObserver observer);
    void notify(IEvent event);
}
