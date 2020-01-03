package graph;

import flag.Flag;
import level.BasicTerrainLookupTable;
import level.Level;

import java.util.ArrayList;

public class Graph {
    Level level;
    Flag constructionProperty;
    boolean requiresDestinationFlag;
    Vertex[] allVertices;

    public Graph(Level l, Flag constructionProperty, boolean requireDestinationFlag) {
        level = l;
        this.constructionProperty = constructionProperty;
        this.requiresDestinationFlag = requireDestinationFlag;
        allVertices = new Vertex[l.getRows() * l.getCols()];
        for (int i = 0; i < l.getRows(); ++i) {
            for (int j = 0; j < l.getCols(); ++j) {
                generateVertex(i, j);
            }
        }
    }
    private void generateVertex(int row, int col) {
        ArrayList<Vertex> adjacentVertices;
        Vertex origin = new Vertex(row, col);
        if (!validVertex(origin)) return;
        adjacentVertices = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            if (direction.ordinal() < Direction.SELF.ordinal()) {
                Vertex adj = new Vertex(origin, direction);
                if (vertexInBounds(adj)) adjacentVertices.add(adj);
            }
        }
        for (Vertex target : adjacentVertices) {
            if (validEdge(origin, target)) {
                origin.addEdge(new Edge(origin, target));
            }
        }
        allVertices[indexOf(origin)] = origin;
    }
    private int indexOf(Vertex v) {
        return v.row() * level.getCols() + v.col();
    }
    private boolean vertexInBounds(Vertex v) {
        return v.row() >= 0 && v.col() >= 0 && v.row() < level.getRows() && v.col() < level.getCols();
    }
    private boolean validVertex(Vertex v) {
        return vertexInBounds(v) && level.propertiesAt(v.row(), v.col()).hasProperty(constructionProperty);
    }
    private boolean validEdge(Vertex orig, Vertex dest) {
        return validVertex(orig) && ((!requiresDestinationFlag && vertexInBounds(dest)) || validVertex(dest));
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

    public ArrayList<Coordinate> radiate(Coordinate center, double power){
        //todo - traverse the graph and return a list of coordinates corresponding to light/sight with the specified
        // power
        return null;
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
        g = new Graph(l, BasicTerrainLookupTable.flag(BasicTerrainLookupTable.PERMIT_LIGHT), false);
        stop = System.currentTimeMillis();
        System.out.println("Graph build speed @16x16: " + (stop - start));
        m = new Level(true, 256, 256, 0);
        start = System.currentTimeMillis();
        g = new Graph(m, BasicTerrainLookupTable.flag(BasicTerrainLookupTable.PERMIT_LIGHT), false);
        stop = System.currentTimeMillis();
        System.out.println("Graph build speed @256x256: " + (stop - start));
        n = new Level(true, 512, 512, 0);
        start = System.currentTimeMillis();
        g = new Graph(n, BasicTerrainLookupTable.flag(BasicTerrainLookupTable.PERMIT_LIGHT), false);
        stop = System.currentTimeMillis();
        System.out.println("Graph build speed @512x512: " + (stop - start));
        o = new Level(true, 1024, 1024, 0);
        start = System.currentTimeMillis();
        g = new Graph(o, BasicTerrainLookupTable.flag(BasicTerrainLookupTable.PERMIT_LIGHT), false);
        stop = System.currentTimeMillis();
        System.out.println("Graph build speed @1024x1024(throttled to 512x512): " + (stop - start));
    }
}
