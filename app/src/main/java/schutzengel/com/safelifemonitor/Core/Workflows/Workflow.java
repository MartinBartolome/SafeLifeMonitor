package schutzengel.com.safelifemonitor.Core.Workflows;

import android.content.Intent;
import android.os.Bundle;
import android.os.Messenger;
import android.os.RemoteException;
import schutzengel.com.safelifemonitor.Core.Observer.IEvent;

public abstract class Workflow extends schutzengel.com.safelifemonitor.Core.Threading.Thread implements IWorkflow {
    protected Messenger messenger = null;

    public class Binder extends android.os.Binder {
        public IWorkflow getWorkflow() {
            return Workflow.this;
        }
    }

    protected enum State {
        Undefined,
        Preparing,
        Prepared,
        Running,
        Terminating,
        Terminated
    }
    protected State state = State.Undefined;

    public Workflow() {
        this.binder = new Binder();
    }

    public void terminate() {
        this.state = State.Terminating;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Bundle extras = intent.getExtras();
        this.messenger = (Messenger)(extras.get("Messenger"));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void prepare() {
        this.state = State.Preparing;
        preparing();
    }

    protected void preparing() {
        this.state = State.Prepared;
    }

    @Override
    protected void runningThreadLoop() {
        try
        {
            this.state = State.Running;
            Boolean shallExit = false;
            while ((!shallExit)|| (State.Running == this.state)) {
                shallExit = running();
            }
        } catch (Exception exception) {
        }
    }

    protected abstract Boolean running();

    @Override
    protected void finalize() {
        finalizing();
        this.state = State.Terminated;
    }

    protected void finalizing() {
    }

    public void notify(IEvent event) {
        try {
            this.messenger.send(event.toMessage());
        } catch (RemoteException exception) {
        }
    }

    public void abort(IEvent event) {
        try {
            this.messenger.send(event.toMessage());
        } catch (RemoteException exception) {
        }
        throw new IllegalArgumentException("Exit");
    }
}