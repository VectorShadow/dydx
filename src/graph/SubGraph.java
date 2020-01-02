package graph;

import java.util.ArrayList;

public class SubGraph {
    ArrayList<Vertex> vertices = new ArrayList<>();

    SubGraph(){}

    void addVertex(Vertex v) {
        vertices.add(v);
    }
    int countEdges() {
        int e = 0;
        for (Vertex vertex : vertices) e += vertex.countEdges();
        return e;
    }
    int countVertices() {
        return vertices.size();
    }
    //check whether this subgraph contains the specified vertex
    boolean contains(Vertex v) {
        for (Vertex vertex : vertices) {
            if (vertex.coordinate().equals(v.coordinate())) {
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        String s = "Subgraph: ";
        for (Vertex v : vertices) s += (v + " ");
        return s;
    }
}
