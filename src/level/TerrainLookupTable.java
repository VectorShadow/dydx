package level;

public interface TerrainLookupTable {
    TerrainProperties lookup(int theme, byte terrainCode);
}
