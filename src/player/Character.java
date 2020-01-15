package player;

import mapgen.WorldCoordinate;

import java.io.Serializable;

public class Character implements Serializable {

    private WorldCoordinate worldCoordinate;

    public void setWorldCoordinate(WorldCoordinate worldCoordinate) {
        this.worldCoordinate = worldCoordinate;
    }

    public WorldCoordinate getWorldCoordinate() {
        return worldCoordinate;
    }
}
