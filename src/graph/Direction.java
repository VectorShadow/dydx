package graph;

public enum Direction {

    NORTH(-1, 0),
    NORTH_EAST(-1, 1),
    EAST(0, 1),
    SOUTH_EAST(1, 1),
    SOUTH(1, 0),
    SOUTH_WEST(1, -1),
    WEST(0, -1),
    NORTH_WEST(-1, -1),
    SELF(0,0),
    ERROR(Integer.MIN_VALUE, Integer.MIN_VALUE);

    final int rowChange;
    final int colChange;

    Direction(int dRow, int dCol) {
        rowChange = dRow;
        colChange = dCol;
    }
    int applyToRow(int row) {
        if (this == ERROR) throw new IllegalArgumentException("May not apply ERROR direction.");
        return row + rowChange;
    }
    int applyToColumn(int column) {
        if (this == ERROR) throw new IllegalArgumentException("May not apply ERROR direction.");
        return column + colChange;
    }
    public static Direction derive(Vertex from, Vertex to) {
        int dRow = to.ROW - from.ROW;
        int dCol = to.COL - from.COL;
        for (Direction direction : Direction.values()){
            if (direction.rowChange == dRow && direction.colChange == dCol) return direction;
        }
        return Direction.ERROR;
    }
}
