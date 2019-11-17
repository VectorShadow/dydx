package engine;

import actor.ActionItem;
import actor.ActorExecutionQueue;
import data.EventDatum;
import data.StreamConverter;
import engine.time.Time;
import error.ErrorLogger;
import error.LogReadyTraceableException;
import level.Level;
import linker.AbstractDataLink;
import linker.ServerDataLink;
import linker.local.ClientLocalDataLink;
import linker.local.AbstractLocalDataLink;
import linker.local.ServerLocalDataLink;
import server.Server;

/**
 * This class manages the game world. It will be constructed with remote = true if called by Launcher
 * (server side, remote), or remote = false if called by Driver(client side, local).
 */
public class Engine extends Thread {

    private Server server = null;
    private LinkLevelTable llt = new LinkLevelTable();
    private final boolean realtime;

    public Engine(boolean remote, boolean realtime) {
        //todo - check for server file structure. create it if necessary
        this.realtime = realtime;
        if (remote) {
            server = new Server();
            llt.setOpenLinks(server.getOpenConnections());
            server.start();
        } else {
            llt.addOpenLink(new ServerLocalDataLink());
        }
    }

    /**
     * if world manager is constructed from driver, we provide a method for the client to get its local D
     * @return
     */
    public ClientLocalDataLink generateClientDataLink() {
        return AbstractLocalDataLink.generateClientLink((ServerLocalDataLink)llt.getOpenLinks().get(0));
    }

    public void run(){
        try {
            loop();
        } catch (Exception e) {
            ErrorLogger.logFatalException(ErrorLogger.trace(e));
        }
    }
    private void loop() throws LogReadyTraceableException {
        long systime;
        for (;;) {
            //sleep until the minimum time between actions has passed
            try {
                Thread.sleep(Time.BASE_GRANULARITY);
            } catch (InterruptedException e) {
                throw new LogReadyTraceableException(e.getMessage());
            }
            systime = System.currentTimeMillis();
            //attempt to process pending actions on all active levels
            for (Level l : llt.getActiveLevels()) {
                //get the queue for this level
                ActorExecutionQueue aeq = l.getActors();
                //update the time on this level - a realtime engine will use the system time,
                //while a turn based engine will simply jump to the time of the next pending event
                l.getTime().setTime(realtime ? systime : aeq.nextTime());
                //loop until all pending actions are executed.
                //this is required because more than one actor may have an action queued for the same instant
                while (l.getTime().getCurrentTime() >= aeq.nextTime()) {
                    //execute
                    EventDatum ed = execute(aeq.execute());
                    //send the resulting datum via all relevant data links
                    for (ServerDataLink sdl : llt.getLinks(l)) {
                        ((AbstractDataLink)sdl).send(StreamConverter.toByteArray(ed));
                    }
                }
            }
        }
    }

    /**
     * Execute an action item.
     * This will update the World State as necessary, and generate an EventDatum that will
     * cause all relevant Clients to make the exact same update.
     */
    private EventDatum execute(ActionItem ai) {
        //todo - update the World, then build an EventDatum to send to the Client
        return null;
    }
}
