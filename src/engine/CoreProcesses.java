package engine;

import client.Client;
import data.*;
import level.Level;
import linker.ClientDataLink;
import linker.ServerDataLink;
import mapgen.FloorDesigner;
import mapgen.WorldCoordinate;
import player.Account;
import player.PlayerCharacter;

public class CoreProcesses {

    private static Client client; //null if local
    private static ClientDataLink clientDataLink; //set whether remote or local
    private static Engine engine; //null client side if remote

    public static void setClient(Client c) {
        client = c;
    }

    public static void setClientDataLink(ClientDataLink cdl) {
        clientDataLink = cdl;
    }

    public static ClientDataLink getClientDataLink() {
        return clientDataLink;
    }

    public static void setEngine(Engine e) {
        engine = e;
        if (!engine.isRemote()) setClientDataLink(engine.generateClientDataLink());
        engine.start();
    }
    public static void trackLevel(Level l) {
        engine.trackLevel(l);
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
    public static PlayerCharacter getPlayerCharacter() {
        return clientDataLink.getCharacter();
    }
    public static void updateActor(Level l) {
        getPlayerCharacter().setActor(l.getActor(getPlayerCharacter().getActor().getUID()));
    }
    public static Level getActiveLevel() {
        return clientDataLink.getLevel();
    }
    public static boolean isRealTime() {
        return engine == null || engine.isRealtime();
    }
    public static boolean isAccountLoggedIn(String accountName) {
        for (ServerDataLink sdl : engine.getDataLinks()) {
            Account a = sdl.getAccount();
            if (a == null) continue;
            if (a.getAccountName().equals(accountName)) return true;
        }
        return false;
    }
    public static Level getLevelAtWorldCoordinate(WorldCoordinate wc) {
        Level level = engine.getLevelByWorldCoordinate(wc);
        if (level != null) return level;
        //todo - MEGAHACK If null, find out of it's one of the
        // Server's permanent levels, and load it, or generate it if it doesn't yet exist.
        // Finally, generate it fresh if needed.
        return FloorDesigner.design(WorldCoordinate.ORIGIN);
    }
}
