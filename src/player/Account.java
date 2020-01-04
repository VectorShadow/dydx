package player;

import mapgen.WorldCoordinate;

/**
 * The base Account class. Implementations should extend this.
 */
public abstract class Account {

    private WorldCoordinate worldCoordinate;

    public void setWorldCoordinate(WorldCoordinate worldCoordinate) {
        this.worldCoordinate = worldCoordinate;
    }

    public WorldCoordinate getWorldCoordinate() {
        return worldCoordinate;
    }
}
