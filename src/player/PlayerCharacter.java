package player;

import mapgen.WorldCoordinate;

import java.io.Serializable;

public abstract class PlayerCharacter implements Serializable {

    private WorldCoordinate worldCoordinate;

    public void setWorldCoordinate(WorldCoordinate worldCoordinate) {
        this.worldCoordinate = worldCoordinate;
    }

    public WorldCoordinate getWorldCoordinate() {
        return worldCoordinate;
    }

    public abstract String[] writeSaveFile(); //define a save file write operation - we must also define a read op
}
