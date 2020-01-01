package graph;

import java.util.ArrayList;

public class Vertex {

    private final Coordinate coordinate;
    ArrayList<Edge> edges;

    Vertex(Coordinate coord){
        coordinate = coord;
        edges = new ArrayList<>();
    }

    Vertex(int row, int col) {
        this(new Coordinate(row, col));
    }
    Vertex(Vertex origin, Direction direction) {
        this(origin.row() + direction.rowChange, origin.col() + direction.colChange);
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
    Coordinate coordinate(){
        return coordinate;
    }
    int countEdges() {
        return edges.size();
    }
    @Override
    public String toString() {
        return "V[" + coordinate + "]";
    }
}
