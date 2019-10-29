package server.engine;

import linker.AbstractDataLink;
import linker.local.ClientLocalDataLink;
import linker.local.AbstractLocalDataLink;
import linker.local.ServerLocalDataLink;
import server.Server;

import java.util.ArrayList;

/**
 * This class manages the game world. It will be constructed with remote = true if called by Launcher
 * (server side, remote), or remote = false if called by Driver(client side, local).
 */
public class WorldManager {

    private Server server = null;
    private ArrayList<AbstractDataLink> openLinks = new ArrayList<>();

    public WorldManager(boolean remote) {
        //todo - check for server file structure. create it if necessary
        if (remote) {
            server = new Server();
            openLinks = server.getOpenConnections();
            server.start();
        } else {
            openLinks.add(new ServerLocalDataLink());
        }
    }

    /**
     * if world manager is constructed from driver, we provide a method for the client to get its local D
     * @return
     */
    public ClientLocalDataLink generateClientDataLink() {
        return AbstractLocalDataLink.generateClientLink((ServerLocalDataLink)openLinks.get(0));
    }
}
