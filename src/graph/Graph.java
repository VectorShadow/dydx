package graph;

import theme.BasicThemeLookupTable;
import level.Level;

import java.util.ArrayList;

public class Graph {
    Level level;
    int constructionAttribute;
    boolean requiresDestinationFlag;
    Vertex[] allVertices;

    public Graph(Level l, int constructionAttribute, boolean requireDestinationFlag) {
        level = l;
        this.constructionAttribute = constructionAttribute;
        this.requiresDestinationFlag = requireDestinationFlag;
        allVertices = new Vertex[l.getRows() * l.getCols()];
        for (int i = 0; i < l.getRows(); ++i) {
            for (int j = 0; j < l.getCols(); ++j) {
                generateVertex(i, j);
            }
        }
    }
    private void generateVertex(int row, int col) {
        ArrayList<Coordinate> adjacentCoordinates;
        Vertex origin = new Vertex(row, col);
        if (!validCoordinate(origin.coordinate())) return;
        adjacentCoordinates = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            if (direction.ordinal() < Direction.SELF.ordinal()) {
                Coordinate adj = origin.adjacent(direction);
                if (inBounds(adj)) adjacentCoordinates.add(adj);
            }
        }
        for (Coordinate target : adjacentCoordinates) {
            if (validEdge(origin, target)) {
                origin.addEdge(new Edge(origin, target));
            }
        }
        setVertexAt(origin);
    }
    private int indexOf(Coordinate c) {
        return c.ROW * level.getCols() + c.COL;
    }
    private Vertex getVertexAt(Coordinate c) {
        return getVertexAt(c);
    }
    private void setVertexAt(Vertex v) {
        allVertices[indexOf(v.coordinate())] = v;
    }
    private boolean inBounds(Coordinate c) {
        return c.ROW >= 0 && c.COL >= 0 && c.ROW < level.getRows() && c.COL < level.getCols();
    }
    private boolean validCoordinate(Coordinate c) {
        return inBounds(c) && level.propertiesAt(c.ROW, c.COL).hasProperty(constructionAttribute);
    }
    private boolean validEdge(Vertex orig, Coordinate dest) {
        return validCoordinate(orig.coordinate()) && (
                (!requiresDestinationFlag && inBounds(dest)) || validCoordinate(dest)
        );
    }

    /**
     * Find a vertex by following an edge from the given vertex.
     *
     * @param origin the source vertex
     * @param edgeDirection the direction of the edge to follow
     * @return the vertex found along the given edge, or null if no edge exists
     */
    private Vertex followEdge(Vertex origin, Direction edgeDirection) {
        Edge e = origin.getEdge(edgeDirection);
        return e == null ? null : getVertexAt(e.destination);
    }
    int countEdges() {
        int count = 0;
        for (Vertex v : allVertices) count += v == null ? 0 : v.countEdges();
        return count;
    }
    int countVertices() {
        int count = 0;
        for (Vertex v : allVertices) {
            if (v != null) count++;
        }
        return count;
    }
    @Override
    public String toString() {
        return "[Graph consisting of " + countVertices() + " edged vertices and " + countEdges() + " edges.]";
    }

    /**
     * Re-generate all vertexes at and adjacent to the specified coordinate.
     * This must be called whenever map terrain is updated in any way that might change existing flags.
     */
    public void update(Coordinate c) {
        for (int i = -1; i < 2; ++i) {
            for (int j = -1; j < 2; ++j) {
                generateVertex(c.ROW + i, c.COL + j);
            }
        }
    }

    /**
     * return the cost of an edge in this graph
     **/
    private double cost(Edge e) {
        return cost(e, constructionAttribute);
    }

    /**
     * return the cost of an edge in this graph for some other attribute
     **/
    private double cost(Edge e, int attributeIndex) {
        return e.cost(level, attributeIndex);
    }

    /**
     * Propagation must be in the initial direction, or along one of the directions adjacent to it.
     */
    private boolean canPropagate(Direction d, Edge e) {
        return e.direction == d || e.direction == d.rotate(true) || e.direction == d.rotate(false);
    }
    /**
     * Check a coordinate for propagation. If it's in bounds, add it and deduct the edge cost to reach it.
     * Then if power remains, attempt to propagate further.
     */
    private void validatePropogation(ArrayList<DirectedCoordinate> dcl, Edge e, double power) {
        Coordinate c = e.destination;
        if (!inBounds(c)) return;
        DirectedCoordinate dc = new DirectedCoordinate(c, e.direction);
        dcl.add(dc);
        double remnant = power - cost(e);
        if (remnant > 0.0)
            propagate(dcl, dc, remnant);
    }
    /**
     *
     * @param dcl a list tracking reached coordinates
     * @param dc the coordinate and direction to propagate from and in
     * @param power the power to continue propagation with
     */
    private void propagate(ArrayList<DirectedCoordinate> dcl, DirectedCoordinate dc, double power) {
        Vertex v = getVertexAt(dc);
        if (v == null || !inBounds(dc)) return;
        dcl.add(dc);
        Edge[] ee = v.getEdges();
        for (Edge e : ee) {
            if (e != null && canPropagate(dc.DIR, e)) {
                validatePropogation(dcl, e, power);
            }
        }
    }

    public ArrayList<DirectedCoordinate> radiate(Coordinate center, double power){
        ArrayList<DirectedCoordinate> dcl = new ArrayList<>();
        Vertex v = getVertexAt(center);
        if (v != null && inBounds(v.coordinate())) {
            dcl.add(new DirectedCoordinate(center, Direction.SELF));
            for (Edge e : getVertexAt(center).getEdges()) {
                validatePropogation(dcl, e, power);
            }
        }
        return dcl;
    }
    public ArrayList<Coordinate> shortestPath(Coordinate origin, Coordinate destination){
        //todo - traverse the graph and return a list of coordinates corresponding to the shortest path from origin
        // to destination in terms of the cost specified by the graph's construction property on its edges.
        return null;
    }

    public static void speedTest() {
        long start, stop;
        Level l, m, n, o;
        Graph g;
        l = new Level(true, 16, 16, 0);
        start = System.currentTimeMillis();
        g = new Graph(l, BasicThemeLookupTable.PERMIT_LIGHT, false);
        stop = System.currentTimeMillis();
        System.out.println("Graph build speed @16x16: " + (stop - start));
        m = new Level(true, 256, 256, 0);
        start = System.currentTimeMillis();
        g = new Graph(m, BasicThemeLookupTable.PERMIT_LIGHT, false);
        stop = System.currentTimeMillis();
        System.out.println("Graph build speed @256x256: " + (stop - start));
        n = new Level(true, 512, 512, 0);
        start = System.currentTimeMillis();
        g = new Graph(n, BasicThemeLookupTable.PERMIT_LIGHT, false);
        stop = System.currentTimeMillis();
        System.out.println("Graph build speed @512x512: " + (stop - start));
        o = new Level(true, 1024, 1024, 0);
        start = System.currentTimeMillis();
        g = new Graph(o, BasicThemeLookupTable.PERMIT_LIGHT, false);
        stop = System.currentTimeMillis();
        System.out.println("Graph build speed @1024x1024(throttled to 512x512): " + (stop - start));
    }
}
