package engine;

import client.Client;
import data.AbstractDatum;
import data.DataPacker;
import data.LevelDatum;
import level.Level;
import linker.ClientDataLink;
import mapgen.FloorDesigner;
import mapgen.WorldCoordinate;
import player.Account;

public class CoreProcesses {

    private static Client client; //null if local
    private static ClientDataLink clientDataLink; //set whether remote or local
    private static Engine localEngine; //null if remote

    public static void setClient(Client c) {
        client = c;
    }

    public static void setClientDataLink(ClientDataLink cdl) {
        clientDataLink = cdl;
    }

    public static ClientDataLink getClientDataLink() {
        return clientDataLink;
    }

    public static void setLocalEngine(Engine engine) {
        localEngine = engine;
        setClientDataLink(engine.generateClientDataLink());
    }
    public static void startLocalEngine() {
        localEngine.start();
    }

    public static void send(AbstractDatum ad, byte ic) {
        clientDataLink.send(DataPacker.pack(ad, ic));
    }
    public static boolean isRemotelyConnected() {
        return client != null;
    }
    public static Account getActiveAccount() {
        return clientDataLink.getAccount();
    }
    public static Level getActiveLevel() {
        return clientDataLink.getLevel();
    }
    public static boolean isRealTime() {
        return localEngine == null || localEngine.isRealtime();
    }
    public static Level getLevelAtWorldCoordinate(WorldCoordinate wc) {
        //todo - MEGAHACK - find out if this level already exists somewhere. If not, find out of it's one of the
        // Server's permanent levels, and load it, or generate it if it doesn't yet exist.
        // Finally, generate it fresh if needed.
        return FloorDesigner.design(WorldCoordinate.ORIGIN);
    }
}
