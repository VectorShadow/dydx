package graph;

import flag.Flag;
import level.Level;

import java.util.ArrayList;
import java.util.Queue;

public class Graph {
    ArrayList<SubGraph> subGraphs;

    /**
     * Level is the level used to generate this graph.
     * ConstructionProperty is the flag used to determine which vertices are included in this graph, and how to apply
     * the boolean condition.
     *
     * Specify whether we want edges to be drawn between vertices such that only one, or both, have the construction
     * property.
     *
     * For example, a light graph would draw edges from the a tile that allows light to all adjacent tiles, regardless
     * of whether they allow light, for the purpose of illuminating them, or from an opaque tile which is itself a light
     * source to adjacent transparent tiles, but not between two opaque tiles.
     * But a movement graph will only draw edges between pairs of tiles that both allow movement, since otherwise
     * movement is not valid along that edge.
     */
    public Graph(Level level, Flag constructionProperty, boolean requireBothEndpoints){
        //iterate through all tiles in the level map, attempting to construct a vertex from each.
        //if that vertex has the construction property, or if requireBothEndpoints is false and any neighbors
        //deeper in the graph(higher row value, or equal row and higher column) have the property, create a new
        //subgraph. Then find all valid edges and add their endpoints to a stack, then construct the edges and
        //add them to the vertex. While the stack is not empty, get the next vertex and repeat the process.
        //once the stack is empty, continue from the next tile in the level map, first checking that none of the
        //existing subgraphs contain it. Once a valid candidate is found, create a new subgraph and repeat the process
        //until the end of the level map is reached.
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
                if (vertex.ROW == v.ROW && vertex.COL == v.COL) return true;
            }
        }
        return false;
    }
}
