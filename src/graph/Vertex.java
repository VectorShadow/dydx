package graph;

import java.util.ArrayList;

public class Vertex {
    final int ROW;
    final int COL;

    ArrayList<Edge> edges;


    Vertex(int row, int col) {
        ROW = row;
        COL = col;
        edges = new ArrayList<>();
    }
    void addEdge(Edge e) {
        edges.add(e);
    }
}
