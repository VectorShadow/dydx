package graph;

import flag.BasicFlag;
import flag.DoubleFlag;
import flag.Flag;
import flag.SingleFlag;

import java.util.ArrayList;

public class Edge {
    final Vertex origin;
    final Vertex destination;
    final Direction direction;
    ArrayList<Flag> flags;

    Edge(Vertex from, Vertex to){
        origin = from;
        destination = to;
        direction = Direction.derive(from, to);
        flags = new ArrayList<>();
    }

    void addFlag(Flag flag) {
        flags.add(flag);
    }

    double cost(Flag flag) {
        if (!flags.contains(flag)) return 0.0;
        if (flag instanceof BasicFlag) return 1.0;
        else if (flag instanceof SingleFlag) return flag.getPower();
        else if (flag instanceof DoubleFlag) return ((DoubleFlag) flag).average();
        else throw new IllegalStateException("flag is of unsupported class");
    }
}
