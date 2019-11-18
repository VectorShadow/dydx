package level.terrain;

/**
 * Provides a set of methods for storing terrain data as a single byte.
 * Each byte supports up to 8 floors(0-7) and 32 walls(0-31).
 */
public class TerrainByte {

    private static final byte EMPTY = 0b0000_0000;
    private static final byte FLOOR_MASK = (byte)0b1110_0000;
    private static final byte WALL_MASK = 0b0001_1111;

    private static final int MAX_FLOOR = 0b0000_0111;
    private static final int MAX_WALL = 0b0001_1111;

    private byte data = EMPTY;

    public TerrainByte(int floor, int wall) {
        setFloor(floor);
        setWall(wall);
    }
    private TerrainByte(byte b) {
        data = b;
    }
    private void setFloor(int floor){
        if (floor < 0 || floor > MAX_FLOOR) throw new IllegalArgumentException("Floor out of range: " + floor);
        floor <<= 5;
        data = (byte)((data & WALL_MASK) | (floor & FLOOR_MASK));
    }
    private void setWall(int wall) {
        if (wall < 0 || wall > MAX_WALL) throw new IllegalArgumentException("Wall out of range: " + wall);
        data = (byte)((data & FLOOR_MASK) | (wall & WALL_MASK));
    }
    private int getFloor() {
        return ((data & FLOOR_MASK) >> 5) & MAX_FLOOR;
    }
    private int getWall() {
        return (data & WALL_MASK);
    }
    public byte toByte() {
        return data;
    }
    public static int getFloor(byte b) {
        return new TerrainByte(b).getFloor();
    }
    public static int getWall(byte b) {
        return new TerrainByte(b).getWall();
    }
    public static void test(){
        for (int f = 0; f <= MAX_FLOOR; ++f){
            for (int w = 0; w <= MAX_WALL; ++w) {
                TerrainByte tb = new TerrainByte(f, w);
                if (tb.getFloor() != f || tb.getWall() != w)
                    throw new IllegalStateException("Test Failure: Floor was " + tb.getFloor() + "; expected: " + f +
                            ". Wall was " + tb.getWall() + "; expected: " + w + ".");
            }
        }
        System.out.println("Test passed.");
    }
}
