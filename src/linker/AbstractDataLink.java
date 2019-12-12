package linker;

import data.AbstractDatum;
import error.LogReadyTraceableException;
import level.Level;

public abstract class AbstractDataLink extends Thread {

    private boolean terminated = false;

    /**
     * Each link is connected on a particular game level.
     * Both Server and Client interfaces require a method called getLevel() to reference this connection.
     * We implement that below by returning this field.
     */
    //todo - MEGA HACK - generate a level here. This should instead be done by accessing the account data and loading or generating an appropriate level!
    protected Level level = new Level(true, 32, 32, 0);

    public abstract void send(byte[] transmission) throws LogReadyTraceableException;

    protected abstract void listen() throws LogReadyTraceableException;

    protected abstract void handle(byte instruction, AbstractDatum datum);

    public abstract void run();

    protected void terminate() {
        terminated = true;
    }
    public boolean isTerminated() {
        return terminated;
    }

    public Level getLevel() {
        return level;
    }
}
