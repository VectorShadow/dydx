package mapgen;

import level.Level;

public interface DesignProfile {
    Level design(WorldCoordinate wc);
    void generateGraphs(Level l);
}
