package graph;

import flag.Flag;
import level.Level;

import java.util.ArrayList;
import java.util.Stack;

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
        this.level = level;
        this.constructionProperty = constructionProperty;
        this.requiresDestinationFlag = requireDestinationFlag;
        SubGraph s; //declare a subgraph
        Stack<Vertex> vertexStack;
        ArrayList<Vertex> forwardVertices;
        boolean validDestinationVertex;
        //iterate through the level map
        for (int i = 0; i < level.getRows(); ++i) {
            for (int j = 0; j < level.getCols(); ++j) {
                Vertex v = new Vertex(i, j); //generate a vertex from the next tile
                //verify that the vertex has the construction property and is not in an existing subgraph
                if (!validVertex(v) || exists(v) != null) continue; //if either fails, move on
                s = new SubGraph(); //otherwise instantiate a new subgraph
                s.addVertex(v); //and add this vertex to it
                vertexStack = new Stack<>(); //instantiate a new stack
                vertexStack.push(v); //and push this vertex onto it
                do {
                    v = vertexStack.pop(); //get the top Vertex from the stack
                    if (!validVertex(v)) continue; //if invalid, ignore and move on
                    forwardVertices = new ArrayList<>(); //instantiate a new list of forward vertices
                    //add all forward edges(which occur later in the level array)
                    forwardVertices.add(new Vertex(v, Direction.EAST));
                    forwardVertices.add(new Vertex(v, Direction.SOUTH_WEST));
                    forwardVertices.add(new Vertex(v, Direction.SOUTH));
                    forwardVertices.add(new Vertex(v, Direction.SOUTH_EAST));
                    //iterate through forward vertices
                    for (Vertex destination : forwardVertices) {
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
                        if (validDestinationVertex) { //if we found a valid edge:
                            //add the destination to the stack(so we can check its forward edges and include it in this
                            //subgraph
                            vertexStack.push(destination);
                            s.addVertex(destination); //add the destination vertex to this subgraph
                        }
                    }
                    //continue until we've checked all vertices found to have valid edges in this subgraph
                } while(!vertexStack.empty());
                subGraphs.add(s); //this subgraph is now complete, add it to this graph
            }
        }
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
    private boolean validVertex(Vertex v) {
        return level.propertiesAt(v.row(), v.col()).hasProperty(constructionProperty);
    }
    private boolean validEdge(Vertex orig, Vertex dest) {
        return validVertex(orig) && (!requiresDestinationFlag || validVertex(dest));
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
