package mapgen;

import level.Level;

public class FloorDesigner {

    private static DesignProfile designProfile = null;

    public static void setDesignProfile(DesignProfile implementationDesignProfile) {
        designProfile = implementationDesignProfile;
    }

    public static Level design(WorldCoordinate wc) {
        return designProfile.design(wc);
    }

    public static void graph(Level l) {
        designProfile.generateGraphs(l);
    }
    //todo - method to update graphs for a level and a coordinate change?

}
