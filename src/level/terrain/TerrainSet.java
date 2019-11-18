package level.terrain;

/**
 * Enables a greater variety of Terrain combinations.
 * Each TerrainSet supports up to 8 floors and 32 walls.
 * Programs making use of this engine should extend this class and implement getProperties
 * as a switch on floor, then wall.
 */
public abstract class TerrainSet {
    public abstract TerrainProperties getProperties(byte b);
    public byte toByte(int floor, int wall) {
        return new TerrainByte(floor, wall).toByte();
    }
}
