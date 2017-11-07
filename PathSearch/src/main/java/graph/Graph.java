package graph;

import map.IntPoint;

import java.util.*;

public class Graph {

    private Map<IntPoint, Set<IntPoint>> edges;

    public Graph() {
        edges = new HashMap<>();
    }

    public void addEdges(IntPoint first, IntPoint second, IntPoint...tail) {
        List<IntPoint> neighbors = Arrays.asList(tail);
        neighbors.add(second);
        addEdges(first, neighbors);
        for (IntPoint neighbor : neighbors) {
            addEdges(neighbor, first);
        }
    }

    public void addEdges(IntPoint first, Collection<IntPoint> neighbors) {
        Set<IntPoint> existedNeighbors = edges.computeIfAbsent(first, (key) -> new HashSet<>());
        existedNeighbors.addAll(neighbors);
    }

    public void addEdges(IntPoint first, IntPoint other) {
        Set<IntPoint> existedNeighbors = edges.computeIfAbsent(first, (key) -> new HashSet<>());
        existedNeighbors.add(other);
    }

    public Set<IntPoint> fetchNeighbors(IntPoint currentPoint) {
        return edges.get(currentPoint);
    }
}
