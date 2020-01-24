package graph;

public class Vertex {

    private final Coordinate coordinate;
    private Edge[] edges;
    private int count = 0;

    Vertex(Coordinate c){
        coordinate = c;
        edges = new Edge[8];
    }

    Vertex(int row, int col) {
        this(new Coordinate(row, col));
    }
    Coordinate adjacent(Direction direction) {
        return coordinate.adjacent(direction);
    }
    void addEdge(Edge e) {
        edges[e.direction.ordinal()] = e;
        ++count;
    }
    Edge getEdge(Direction dir) {
        return edges[dir.ordinal()];
    }
    Edge[] getEdges() {
        return edges;
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
        return count;
    }
    @Override
    public String toString() {
        return "V[" + coordinate + "]";
    }
}
