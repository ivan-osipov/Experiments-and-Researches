package pathfinding;

import java.util.*;

public class Graph<T> {

    private Map<T, Set<T>> edges;

    public Graph() {
        edges = new HashMap<>();
    }

    public void addEdges(T first, T second, T...tail) {
        List<T> neighbors = Arrays.asList(tail);
        neighbors.add(second);
        addEdges(first, neighbors);
        for (T neighbor : neighbors) {
            addEdges(neighbor, first);
        }
    }

    public void addEdges(T first, Collection<T> neighbors) {
        Set<T> existedNeighbors = edges.computeIfAbsent(first, (key) -> new HashSet<>());
        existedNeighbors.addAll(neighbors);
    }

    public void addEdges(T first, T other) {
        Set<T> existedNeighbors = edges.computeIfAbsent(first, (key) -> new HashSet<>());
        existedNeighbors.add(other);
    }

    public void optimizeSize() {
        edges = new HashMap<>(edges);
    }

    public Set<T> fetchNeighbors(T currentPoint) {
        return edges.get(currentPoint);
    }
}
