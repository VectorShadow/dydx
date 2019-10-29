package linker;

import error.LogReadyTraceableException;

public abstract class AbstractDataLink extends Thread {

    private boolean terminated = false;

    public abstract void send(byte[] transmission) throws LogReadyTraceableException;

    protected abstract void listen() throws LogReadyTraceableException;

    protected abstract void handle(byte instruction, byte[] body);

    public abstract void run();

    protected void terminate() {
        terminated = true;
    }
    public boolean isTerminated() {
        return terminated;
    }
}
