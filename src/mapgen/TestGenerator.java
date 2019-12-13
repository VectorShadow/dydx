package mapgen;

import java.util.Random;

public class TestGenerator implements MapGenerator {
    @Override
    public byte[][] generateTerrain(int rows, int columns) {
        byte[][] terrain = new byte[rows][columns];
        for (byte[] row : terrain) for (byte b : row) b = (byte)(new Random().nextDouble() > 0.75 ? 2 : 1);
        return terrain;
    }
}
