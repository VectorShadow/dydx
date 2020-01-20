package mapgen;

import engine.CoreProcesses;
import level.Level;
import mapgen.WorldCoordinate;

public class FloorDesigner {

    public static Level design(WorldCoordinate wc) {
        //todo - MEGAHACK - use the theme lookup table to find a theme from the world coordinate.
        // Then use this theme to pick a MapGenerator and build the level from it.
        // Note that Level constructor needs to be completely refactored before this can be done properly.
        return new Level(CoreProcesses.isRealTime(), 36, 36, 1);
    }

}
