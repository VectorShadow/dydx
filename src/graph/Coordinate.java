package graph;

public class Coordinate {
    public final int ROW;
    public final int COL;

    public Coordinate(int row, int col) {
        ROW = row;
        COL = col;
    }
    Coordinate adjacent(Direction direction) {
        return new Coordinate(ROW + direction.rowChange, COL + direction.colChange);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Coordinate && ((Coordinate) o).ROW == ROW && ((Coordinate) o).COL == COL;
    }

    @Override
    public String toString() {
        return "(" + ROW + "," + COL + ")";
    }
}
