package graph;

/**
 * An Extension of Subgraph which provides additional functionality not required by the standard
 * usage of the base class.
 * In particular, find and remove a Vertex corresponding to a desired coordinate, so that it can be
 * updated rather than replaced, and provide push and pop functions to mimic a true stack.
 */
public class VertexStack extends SubGraph {
    //remove return the vertex existing in this stack which corresponds to the provided coordinate
    Vertex removeVertex(Coordinate c) {
        for (Vertex vertex : vertices) {
            if (vertex.coordinate().equals(c)) {
                vertices.remove(vertex);
                return vertex;
            }
        }
        return null;
    }
    //pop the last vertex added to this stack
    Vertex pop() {
        return vertices.remove(vertices.size() - 1);
    }
    //mask addVertex as expected of a Stack
    void push(Vertex v) {
        addVertex(v);
    }
}
