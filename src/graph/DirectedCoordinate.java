package graph;

public class DirectedCoordinate extends Coordinate {
    public final Direction DIR;
    public DirectedCoordinate(Coordinate c, Direction d) {
        super(c.ROW, c.COL);
        DIR = d;
    }
}
