package graph;

public class DirectedCoordinate extends Coordinate {
    public final Direction DIR;
    public DirectedCoordinate(int row, int col, Direction d) {
        super(row, col);
        DIR = d;
    }
}
