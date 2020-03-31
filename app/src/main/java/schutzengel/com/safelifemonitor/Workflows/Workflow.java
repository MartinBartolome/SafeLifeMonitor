package schutzengel.com.safelifemonitor.Workflows;

import schutzengel.com.safelifemonitor.Core.Observer.Subject;

public abstract class Workflow extends Subject implements IWorkflow {
    protected enum State {
        Undefined,
        Preparing,
        Prepared,
        Running,
        Suspending,
        Suspended,
        Terminating,
        Terminted
    }
    protected State state = State.Undefined;

    public Workflow() {
    }

    @Override
    public void terminate() {
        this.state = State.Terminating;
        terminating();
    }

    protected void terminating() {
        this.state = State.Terminted;
    }

    @Override
    public void start() {
        switch (this.state) {
            case Undefined:
                prepare();
                run();
            case Running:
                suspend();
                prepare();
                run();
            case Suspended:
                prepare();
                run();
            default:
                break;
        }
    }

    private void prepare() {
        this.state = State.Preparing;
        preparing();
    }

    protected void preparing() {
        this.state = State.Prepared;
    }

    private void run() {
        this.state = State.Running;
        while (State.Running == this.state) {
            running();
        }
    }

    protected abstract void running();

    private void suspend() {
        this.state = State.Suspending;
        suspending();
    }

    protected void suspending() {
        this.state = State.Suspended;
    }
}