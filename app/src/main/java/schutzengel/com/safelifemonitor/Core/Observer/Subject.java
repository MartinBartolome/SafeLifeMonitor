package schutzengel.com.safelifemonitor.Core.Observer;

import java.util.ArrayList;

public class Subject implements ISubject{
    private ArrayList<IObserver> observers = null;

    public Subject() {
        this.observers = new ArrayList<>();
    }

    public void register(IObserver observer) {
        this.observers.add(observer);
    }

    public void notify(IEvent event) {
        for (IObserver observer : this.observers) {
            observer.update(event);
        }
    }
}