package graph;

import java.util.ArrayList;

public class SubGraph {
    ArrayList<Vertex> vertices = new ArrayList<>();
    ArrayList<Edge> edges = new ArrayList<>();

    SubGraph(){}

    void addVertex(Vertex v) {
        vertices.add(v);
    }
    void addEdge(Edge e) {
        edges.add(e);
    }
}
