package graph;

import flag.BasicFlag;
import flag.DoubleFlag;
import flag.Flag;
import flag.SingleFlag;
import level.Level;

/**
 * An edge between Vertices in a Graph.
 * Note that the destination is not a Vertex, but rather the Coordinate corresponding to a Vertex.
 * This is because Coordinates can be interpreted in the context of a Graph, but Vertices without
 * that context are meaningless.
 */
public class Edge {
    private final Vertex origin;
    final Coordinate destination;
    final Direction direction;

    Edge(Vertex from, Coordinate to){
        origin = from;
        destination = to;
        direction = Direction.derive(from, to);
    }

    double cost(Level level, Flag flag) {
        boolean originHasFlag = level.propertiesAt(origin.row(), origin.col()).hasProperty(flag);
        boolean destinationHasFlag = level.propertiesAt(destination.ROW, destination.COL).hasProperty(flag);
        //neither endpoint has the flag, no cost for that flag
        if (!originHasFlag && !destinationHasFlag) return 0.0;
        //at least one endpoint has the flag:
        //basic flags have no inherent cost, return 1.0 for orthogonal or 1.5 for diagonal
        else if (flag instanceof BasicFlag) return direction.multiplier();
        //single flags return their inherent cost, halved if only one endpoint has the flag
        else if (flag instanceof SingleFlag)
            return flag.getPower() * (originHasFlag && destinationHasFlag ? 1.0 : 0.5);
        //double flags return their average cost, halved if only one endpoint has the flag
        else if (flag instanceof DoubleFlag)
            return ((DoubleFlag) flag).average() * (originHasFlag && destinationHasFlag ? 1.0 : 0.5);
        else throw new IllegalStateException("flag is of unsupported class");
    }
}
