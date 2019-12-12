package engine;

import level.Level;
import linker.ServerDataLink;
import server.Server;

import java.util.ArrayList;

/**
 * Connects links to levels.
 * When given a level, returns a list of links associated with that level.
 * When given a link, returns the level that link is associated with.
 */
public class LinkLevelTable {
    private ArrayList<ServerDataLink> openLinks = new ArrayList<>();
    private ArrayList<Level> activeLevels = new ArrayList<>();

    /**
     * Search all open links for the specified level and return a list of links connected to that level.
     */
    public ArrayList<ServerDataLink> getLinks(Level l) {
        ArrayList<ServerDataLink> serverDataLinks = new ArrayList<>();
        for (ServerDataLink sdl : openLinks) if (sdl.getLevel() == l) serverDataLinks.add(sdl);
        return serverDataLinks;
    }
    public Level getLevel(ServerDataLink l) {
        if (openLinks.contains(l)) return l.getLevel();
        throw new IllegalArgumentException("Specified data link is not in this table.");
    }

    public ArrayList<Level> getActiveLevels() {
        return activeLevels;
    }

    public ArrayList<ServerDataLink> getOpenLinks() {
        return openLinks;
    }

    public void setOpenLinks(ArrayList<ServerDataLink> openLinks) {
        this.openLinks = openLinks;
    }
    public void addOpenLink(ServerDataLink sdl) {
        openLinks.add(sdl);
    }
}
