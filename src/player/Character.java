package player;

import mapgen.WorldCoordinate;

public class Character {

    private WorldCoordinate worldCoordinate;

    public void setWorldCoordinate(WorldCoordinate worldCoordinate) {
        this.worldCoordinate = worldCoordinate;
    }

    public WorldCoordinate getWorldCoordinate() {
        return worldCoordinate;
    }
}
