package engine;

import actor.Action;
import actor.ActionItem;
import actor.ActionResolutionManager;
import crypto.RSA;
import data.*;
import engine.time.Time;
import error.ErrorLogger;
import error.LogReadyTraceableException;
import level.Level;
import linker.ServerDataLink;
import linker.local.ClientLocalDataLink;
import linker.local.AbstractLocalDataLink;
import linker.local.ServerLocalDataLink;
import mapgen.WorldCoordinate;
import server.FileManager;
import server.Server;

import java.util.ArrayList;

/**
 * This class manages the game world. It will be constructed with remote = true if called by Launcher
 * (server side, remote), or remote = false if called by Driver(client side, local).
 */
public class Engine extends Thread {

    private Server server = null;
    private ArrayList<ServerDataLink> dataLinks = null;
    private ArrayList<Level> activeLevels = new ArrayList<>();
    private final boolean remote;
    private final boolean realtime;

    public Engine(boolean remote, boolean realtime) {
        FileManager.ensurePaths();
        this.remote = remote;
        this.realtime = realtime;
        if (remote) {
            server = new Server();
            server.start();
            dataLinks = server.getOpenConnections();
        } else {
            dataLinks = new ArrayList<>();
            dataLinks.add(new ServerLocalDataLink());
        }
    }
    public void trackLevel(Level l) {
        activeLevels.add(l);
        l.getTime().initialize();
    }
    public ArrayList<ServerDataLink> getDataLinks() {
        return dataLinks;
    }
    public ArrayList<Level> getActiveLevels() {
        return activeLevels;
    }
    public Level getLevelByWorldCoordinate(WorldCoordinate wc) {
        for (Level l : activeLevels) {
            if (l.getWorldCoordinate().equals(wc)) return l;
        }
        return null;
    }

    /**
     * Return a list of ServerDataLinks which this engine is connected on.
     * If we are local, simply return the dataLinks field.
     * Otherwise, get the open connections from the active Server.
     */
    private ArrayList<ServerDataLink> getAllDataLinks() {
        if (server == null) return dataLinks;
        return server.getOpenConnections();
    }

    /**
     * if world manager is constructed from driver, we provide a method for the client to get its local data link
     * we also take care of RSA crypto since we don't use a Server (which normally handles this) in local mode
     * @return
     */
    public ClientLocalDataLink generateClientDataLink() {
        RSA.generateSessionKeys();
        ServerLocalDataLink sldl = (ServerLocalDataLink) dataLinks.get(0);
        ClientLocalDataLink cldl = AbstractLocalDataLink.generateClientLink(sldl);
        sldl.send(
                DataPacker.pack(
                        new BigIntegerDatum(RSA.getSessionPublicKey()),
                        InstructionCode.PROTOCOL_BIG_INTEGER)
        );
        return cldl;
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
            for (Level l : activeLevels) {
                //get the queue for this level
                ActorExecutionQueue aeq = l.getActors();
                //update the time on this level - a realtime engine will use the system time,
                //while a turn based engine will simply jump to the time of the next pending event
                l.getTime().setTime(realtime ? systime : aeq.nextTime());
                //loop until all pending actions are executed.
                //this is required because more than one actor may have an action queued for the same instant
                while (l.getTime().getCurrentTime() >= aeq.nextTime()) {
                    //execute
                    ActionEventDatum aed = execute(l);
                    //send the resulting datum via all relevant data links
                    for (ServerDataLink sdl : getAllDataLinks()) {
                        if (sdl.getLevel() == l && aed.getActionItem().getAction() != Action.PAUSE)
                            sdl.send(DataPacker.pack(aed, InstructionCode.PROTOCOL_TRANSMIT_ACTION_EVENT));
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
    private ActionEventDatum execute(Level l) {
        ActionItem ai = l.execute();
        if (CoreProcesses.isRemotelyConnected()) //update remote gamestate
            ActionResolutionManager.resolve(ai, l);
        return new ActionEventDatum(ai); //update local gamestate
    }
    /**
     * Search all open links for the specified level and return a list of links connected to that level.
     */
    public ArrayList<ServerDataLink> getLinksByLevel(Level l) {
        if (!activeLevels.contains(l)) throw new IllegalArgumentException("Specified level does not exist.");
        ArrayList<ServerDataLink> serverDataLinks = new ArrayList<>();
        for (ServerDataLink sdl : getAllDataLinks()) if (sdl.getLevel() == l) serverDataLinks.add(sdl);
        return serverDataLinks;
    }

    /**
     * Return the level associated with the specified data link.
     */
    public Level getLevelByLink(ServerDataLink l) {
        if (!getAllDataLinks().contains(l)) throw new IllegalArgumentException("Specified data link does not exist.");
        return l.getLevel();
    }

    public boolean isRemote() {
        return remote;
    }
    public boolean isRealtime() {
        return realtime;
    }
}
