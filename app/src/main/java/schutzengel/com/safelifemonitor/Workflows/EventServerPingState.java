package schutzengel.com.safelifemonitor.Workflows;

import android.os.Bundle;
import android.os.Message;
import schutzengel.com.safelifemonitor.Core.Definitions;
import schutzengel.com.safelifemonitor.Core.Observer.Event;

public class EventServerPingState extends Event {
    public enum State {
        Undefined,
        Success,
        Failed
    }
    protected State state = State.Undefined;

    protected enum Key {
        State,
    }

    public EventServerPingState(Message message) {
        Bundle bundle = message.getData();
        this.state = EventServerPingState.State.valueOf(bundle.getString(EventServerPingState.Key.State.toString()));
    }

    public EventServerPingState(State state) {
        this.state = state;
    }

    @Override
    public int getIdentifier() {
        return Definitions.EventIdentifier.ServerPingState.ordinal();
    }

    public State getState() {
        return this.state;
    }

    @Override
    public Message toMessage() {
        Message message = super.toMessage();
        Bundle bundle = new Bundle();
        bundle.putString(EventServerPingState.Key.State.toString(), this.state.toString());
        message.setData(bundle);
        return message;
    }
}