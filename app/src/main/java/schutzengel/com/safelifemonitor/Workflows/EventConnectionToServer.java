package schutzengel.com.safelifemonitor.Workflows;

import schutzengel.com.safelifemonitor.Core.Observer.IEvent;

public class EventConnectionToServer implements IEvent {
    public enum State {
        Undefined,
        Connected,
        Disconnected
    }
    protected State state = State.Undefined;

    public EventConnectionToServer(State state) {
        this.state = state;
    }

    public State getState() {
        return this.state;
    }
}