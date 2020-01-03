package graph;

import flag.Flag;
import level.BasicTerrainLookupTable;
import level.Level;

import java.util.ArrayList;

public class Graph {
    ArrayList<SubGraph> subGraphs;
    Level level;
    Flag constructionProperty;
    boolean requiresDestinationFlag;

    /**
     * Level is the level used to generate this graph.
     * ConstructionProperty is the flag used to determine which vertices are included in this graph, and how to apply
     * the boolean condition.
     *
     * Specify whether we want edges to be drawn between vertices such that only the source, or both the source and
     * destination, must have the required construction property.
     *
     * For example, a light graph would draw edges from the a tile that allows light to all adjacent tiles, regardless
     * of whether they allow light, for the purpose of illuminating them, but light may not pass through or originate
     * from an opaque tile.
     * But a movement graph will only draw edges between pairs of tiles that both allow movement, since otherwise
     * movement is not valid along that edge.
     */
    public Graph(Level level, Flag constructionProperty, boolean requireDestinationFlag){
        subGraphs = new ArrayList<>();
        this.level = level;
        this.constructionProperty = constructionProperty;
        this.requiresDestinationFlag = requireDestinationFlag;
        SubGraph nextSubgraph; //the SubGraph we are currently building
        VertexStack pendingChecks; //a Stack to track vertices that remain to be checked and preserves edges
        ArrayList<Vertex> forwardVertices; //declare a list to hold forward vertices
        boolean validDestinationVertex; //flag indicating a valid edge exists
        //iterate through the level map
        for (int i = 0; i < level.getRows(); ++i) {
            for (int j = 0; j < level.getCols(); ++j) {
                Vertex v = new Vertex(i, j); //generate a vertex from the next tile
                //verify that the vertex has the construction property and is not in an existing subgraph
                if (!validVertex(v) || exists(v) != null) continue; //if either fails, move on
                nextSubgraph = new SubGraph(); //otherwise instantiate the next subgraph to build
                pendingChecks = new VertexStack(); //and a new pending check subgraph
                pendingChecks.push(v); //and prepare to check the current vertex
                do {
                    v = pendingChecks.pop(); //get the next vertex to be checked
                    //if we've already checked and added this vertex, or it's invalid, ignore and move on
                    if (nextSubgraph.contains(v) || !validVertex(v)) continue;
                    forwardVertices = new ArrayList<>(); //instantiate a new list of forward vertices
                    //add all forward edges (from v to another tile) which are still in bounds
                    for (Direction direction : Direction.values()) {
                        if (direction != Direction.ERROR) {
                            Vertex fv = new Vertex(v, direction);
                            if (vertexInBounds(fv)) forwardVertices.add(fv);
                        }
                    }
                    //iterate through forward vertices
                    for (Vertex destination : forwardVertices) {
                        //find out whether an existing vertex to be checked corresponds to the specified destination
                        Vertex existingVertex = pendingChecks.removeVertex(destination.coordinate());
                        //if so, use the existing vertex instead of the newly generated one to preserve edges
                        if (existingVertex != null) destination = existingVertex;
                        validDestinationVertex = false; //destination does not yet have a valid edge with v
                        if (validEdge(v, destination)) { //check the forward edge
                            v.addEdge(new Edge(v, destination)); //if valid, create the edge and add it to v
                            validDestinationVertex = true; //we found a valid edge
                        }
                        if (validEdge(destination, v)) { //check the backward edge
                            //if valid, create the edge and add it to destination
                            destination.addEdge(new Edge(destination, v));
                            validDestinationVertex = true; //we found a valid edge
                        }
                        //if we found a new valid edge:
                        if (validDestinationVertex) {
                            //if the edge is valid, but the destination is not, add the destination to the subgraph -
                            //since it will not pass its self check - if it's not already there
                            if (!validVertex(destination)) {
                                if (!nextSubgraph.contains(destination)) nextSubgraph.addVertex(destination);
                            }
                            //otherwise, add it to the list to be checked if it's not already there
                            else if (!pendingChecks.contains(destination)) {
                                pendingChecks.push(destination);
                            }

                        }
                        //otherwise, if we removed an existing vertex, put it back
                        else if (existingVertex != null) {
                            pendingChecks.push(existingVertex);
                        }
                    }
                    //if this vertex has any edges, add it to the subgraph we're building
                    if (v.edges.size() > 0 && !nextSubgraph.contains(v)) nextSubgraph.addVertex(v);
                    //continue until we've checked all vertices found to have valid edges in this subgraph
                } while(pendingChecks.countVertices() > 0);
                //this subgraph is now complete - if it has any vertices, add it to this graph
                if (nextSubgraph.countVertices() > 0) subGraphs.add(nextSubgraph);
            }
        }
    }
    public void update(Coordinate c) {
        //todo - update the graph based on a change at coordinate c.
        // this is necessary if we want to avoid rebuilding the entire graph when tile properties change at runtime.
    }

    /**
     * Check the subgraphs for a specific vertex.
     * If a vertex with the same row and column information is found, return the subgraph it was found in.
     * Otherwise return null.
     */
    private SubGraph exists(Vertex v) {
        for (SubGraph subGraph : subGraphs) {
            for (Vertex vertex : subGraph.vertices) {
                if (vertex.row() == v.row() && vertex.col() == v.col()) return subGraph;
            }
        }
        return null;
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
    int countEdges() {
        int e = 0;
        for (SubGraph s : subGraphs) {
            for (Vertex v : s.vertices) e += v.countEdges();
        }
        return e;
    }
    int countVertices() {
        int v = 0;
        for (SubGraph s : subGraphs) v += s.countVertices();
        return v;
    }
    int countSubgraphs() {
        return subGraphs.size();
    }
    @Override
    public String toString() {
        String s = "[Graph consisting of " + countSubgraphs() + " subgraphs, containing a total of " +
                countVertices() + " vertices and " + countEdges() + " edges.]";
        for (SubGraph subGraph : subGraphs) s += "\n" + subGraph;
        return s;
    }
    public static void speedTest() {
        long start, stop;
        Level l, m, n, o;
        Graph g;
        l = new Level(true, 32, 32, 0);
        start = System.currentTimeMillis();
        g = new Graph(l, BasicTerrainLookupTable.flag(BasicTerrainLookupTable.PERMIT_LIGHT), false);
        stop = System.currentTimeMillis();
        System.out.println("Graph build speed @32x32: " + (stop - start));
        m = new Level(true, 225, 68, 0);
        start = System.currentTimeMillis();
        g = new Graph(m, BasicTerrainLookupTable.flag(BasicTerrainLookupTable.PERMIT_LIGHT), false);
        stop = System.currentTimeMillis();
        System.out.println("Graph build speed @225x68: " + (stop - start));
        n = new Level(true, 19, 816, 0);
        start = System.currentTimeMillis();
        g = new Graph(n, BasicTerrainLookupTable.flag(BasicTerrainLookupTable.PERMIT_LIGHT), false);
        stop = System.currentTimeMillis();
        System.out.println("Graph build speed @19x816: " + (stop - start));
        o = new Level(true, 512, 512, 0);
        start = System.currentTimeMillis();
        g = new Graph(o, BasicTerrainLookupTable.flag(BasicTerrainLookupTable.PERMIT_LIGHT), false);
        stop = System.currentTimeMillis();
        System.out.println("Graph build speed @512x512(throttled to 128x128): " + (stop - start));
    }
}
