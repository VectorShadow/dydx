package engine;

import level.Level;
import linker.ServerDataLink;

import java.util.ArrayList;

/**
 * Connects links to levels.
 * When given a level, returns a list of links associated with that level.
 * When given a link, returns the level that link is associated with.
 */
public class LinkLevelTable {
    private ArrayList<ServerDataLink> openLinks = new ArrayList<>();
    private ArrayList<Level> activeLevels = new ArrayList<>();
    //todo - some data structure here

    public ArrayList<ServerDataLink> getLinks(Level l) {
        //todo - stub
        return null;
    }
    public Level getLevel(ServerDataLink l) {
        //todo - stub
        return null;
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
