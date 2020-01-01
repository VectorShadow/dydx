package graph;

import flag.Flag;
import level.Level;

import java.util.ArrayList;

public class Graph {
    ArrayList<SubGraph> subGraphs;

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
        //iterate through all tiles in the level map, attempting to construct a vertex from each.
        //if that vertex has the construction property, create a new subgraph. Then find all valid edges and add their
        //endpoints to a stack, then construct the edges and add them to the vertex. While the stack is not empty, get
        //the next vertex and repeat the process. Once the stack is empty, continue from the next tile in the level map,
        //first checking that none of the existing subgraphs contain it. Once a valid candidate is found, create a new
        //subgraph and repeat the process until the end of the level map is reached.
        for (int i = 0; i < level.getRows(); ++i) {
            for (int j = 0; j < level.getCols(); ++j) {
                Vertex v = new Vertex(i, j);
                if (exists(v)) continue;
                //todo - continue as described above
            }
        }
    }

    /**
     * Check the subgraphs for a specific vertex.
     * If a vertex with the same row and column information is found, return true.
     * Otherwise return false.
     */
    private boolean exists(Vertex v) {
        for (SubGraph subGraph : subGraphs) {
            for (Vertex vertex : subGraph.vertices) {
                if (vertex.row() == v.row() && vertex.col() == v.col()) return true;
            }
        }
        return false;
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
}
