package graph;

import java.util.ArrayList;

public class Vertex {

    final Coordinate coordinate;
    ArrayList<Edge> edges;

    Vertex(Coordinate coord){
        coordinate = coord;
        edges = new ArrayList<>();
    }

    Vertex(int row, int col) {
        this(new Coordinate(row, col));
    }
    void addEdge(Edge e) {
        edges.add(e);
    }
    int col(){
        return coordinate.COL;
    }
    int row(){
        return coordinate.ROW;
    }
}
