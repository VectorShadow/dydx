package level;

import attribute.Attribute;
import gui.draw.Light;

public interface ThemeLookupTable {
    Attribute[] defaultAttributes();
    TerrainProperties lookupTerrain(int theme, byte terrainCode);
    Light lookupLight(int theme);
}
