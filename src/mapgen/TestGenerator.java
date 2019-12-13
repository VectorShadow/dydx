package mapgen;

import java.util.Random;

public class TestGenerator implements MapGenerator {
    @Override
    public byte[][] generateTerrain(int rows, int columns) {
        byte[][] terrain = new byte[rows][columns];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                terrain[i][j] = (byte)(new Random().nextDouble() > 0.75 ? 2 : 1);
            }
        }
        return terrain;
    }
}
