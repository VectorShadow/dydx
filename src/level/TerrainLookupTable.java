package level;

import attribute.Attribute;

public interface TerrainLookupTable {
    Attribute[] defaultAttributes();
    TerrainProperties lookup(int theme, byte terrainCode);
}
