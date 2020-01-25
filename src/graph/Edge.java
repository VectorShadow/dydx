package graph;

import attribute.*;
import level.Level;

import java.io.Serializable;

/**
 * An edge between Vertices in a Graph.
 * Note that the destination is not a Vertex, but rather the Coordinate corresponding to a Vertex.
 * This is because Coordinates can be interpreted in the context of a Graph, but Vertices without
 * that context are meaningless.
 */
public class Edge implements Serializable {
    private final Vertex origin;
    final Coordinate destination;
    final Direction direction;

    Edge(Vertex from, Coordinate to){
        origin = from;
        destination = to;
        direction = Direction.derive(from, to);
    }

    double cost(Level level, int attributeIndex) {
        Attribute originAttribute = level.propertiesAt(origin.row(), origin.col()).getAttribute(attributeIndex);
        Attribute destinationAttribute =
                level.propertiesAt(destination.ROW, destination.COL).getAttribute(attributeIndex);
        //neither endpoint has the flag, no cost for that flag
        if (!originAttribute.isSet() && !destinationAttribute.isSet()) return 0.0;
        //at least one endpoint has the flag:
        //boolean attributes have no inherent cost, return 1.0 for orthogonal or 1.5 for diagonal
        //scaling attributes may modify this cost, so multiply by the average of both ends
        else if (originAttribute instanceof BooleanAttribute || originAttribute instanceof ScalingAttribute)
            return direction.multiplier() * (originAttribute.getScaling() + destinationAttribute.getScaling()) * 0.5;
        //constant flat attributes return half their flat value for each endpoint
        //dual attributes additionally apply the average scaling factor of both ends to the flat sum
        else if (originAttribute instanceof ConstantFlatAttribute || originAttribute instanceof DualAttribute)
            return ((originAttribute.getFlat() + destinationAttribute.getFlat()) * 0.5) *
                    (originAttribute.getScaling() + destinationAttribute.getScaling()) * 0.5;
        //variable flat attributes return half their average for each endpoint
        else if (originAttribute instanceof VariableFlatAttribute)
            return (originAttribute.getAverage() + destinationAttribute.getAverage()) * 0.5;
        else throw new IllegalStateException("flag is of unsupported class");
    }
}
