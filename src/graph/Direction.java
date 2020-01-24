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
    public static Direction derive(Vertex from, Coordinate to) {
        int dRow = to.ROW - from.row();
        int dCol = to.COL - from.col();
        for (Direction direction : Direction.values()){
            if (direction.rowChange == dRow && direction.colChange == dCol) return direction;
        }
        return Direction.ERROR;
    }
    private Direction clockTransform(boolean clockwise) {
        switch (this) {
            case NORTH: return clockwise ? NORTH_EAST : NORTH_WEST;
            case NORTH_EAST: return clockwise ? EAST : NORTH;
            case EAST: return clockwise ? SOUTH_EAST : NORTH_EAST;
            case SOUTH_EAST: return clockwise ? SOUTH : EAST;
            case SOUTH: return clockwise ? SOUTH_WEST : SOUTH_EAST;
            case SOUTH_WEST: return clockwise ? WEST : SOUTH;
            case WEST: return clockwise ? NORTH_WEST : SOUTH_WEST;
            case NORTH_WEST: return clockwise ? NORTH : WEST;
            default: return ERROR;
        }
    }
    public Direction rotate(boolean clockwise) {
        return rotate(clockwise, 1);
    }
    public Direction rotate(boolean clockwise, int iterations) {
        if (iterations <= 0) throw new IllegalArgumentException("Iterations must be > 0.");
        Direction result = this;
        while (iterations > 0) {
            result = clockTransform(clockwise);
            --iterations;
        }
        return result;
    }
    public double multiplier(){
        switch (this){
            case NORTH: case EAST: case SOUTH: case WEST:
                return 1.0;
            case NORTH_EAST: case SOUTH_EAST: case SOUTH_WEST: case NORTH_WEST:
                return 1.5;
            case SELF:
                return 0.0;
            default:
                throw new IllegalStateException("May not call multiplier on ERROR direction.");
        }
    }
}
