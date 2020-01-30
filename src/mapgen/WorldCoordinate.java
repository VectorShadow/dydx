package mapgen;

import java.io.Serializable;

/**
 * Describe the absolute location of a player in the world.
 * Used for map generation parameters.
 * Instance allows unique private game instances in otherwise equivalent locations.
 * World Location refers to overworld areas, such as hubs and dungeon locations.
 * Level Depth describes how far into a dungeon(or town in some cases) the player is.
 * This often influences difficulty and rewards.
 */
public class WorldCoordinate implements Serializable {
    public static final int PUBLIC_INSTANCE = 0;
    public static final int ORIGIN_LOCATION = 0;
    public static final int SURFACE = 0;

    public static final WorldCoordinate ORIGIN = new WorldCoordinate(PUBLIC_INSTANCE, ORIGIN_LOCATION, SURFACE);

    int instance;
    int worldLocation;
    int levelDepth;

    public WorldCoordinate(int inst, int loc, int depth) {
        instance = inst;
        worldLocation = loc;
        levelDepth = depth;
    }

    public int getInstance() {
        return instance;
    }

    public int getWorldLocation() {
        return worldLocation;
    }

    public int getLevelDepth() {
        return levelDepth;
    }

    @Override
    public String toString() {
        return "(" + instance + "," + worldLocation + "," + levelDepth + ")";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof WorldCoordinate &&
                ((WorldCoordinate) obj).instance == instance &&
                ((WorldCoordinate) obj).worldLocation == worldLocation &&
                ((WorldCoordinate) obj).levelDepth == levelDepth;
    }
}
