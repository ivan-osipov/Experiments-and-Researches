package pathfinding;

import java.util.*;
import java.util.function.Function;

public class WeighedGraph<T> {

    private final Function<Node, List<Edge>> EMPTY_EDGES_FUNC = (n) -> new ArrayList<>();

    private Map<T, Node> nodes = new HashMap<>();
    private Map<Node, List<Edge>> edges = new HashMap<>();

    public void addEdge(Node first, Node second, int weight) {
        nodes.put(first.content, first);
        nodes.put(second.content, second);

        Edge firstEdge = new Edge(weight, second);
        edges.computeIfAbsent(first, EMPTY_EDGES_FUNC);
        List<Edge> firstEdges = this.edges.get(first);
        firstEdges.add(firstEdge);

        Collections.sort(firstEdges);
    }

    public Node createNode(T content) {
        return new Node(content);
    }

    public List<Edge> fetchEdges(WeighedGraph<T>.Node currentPoint) {
        return edges.get(currentPoint);
    }

    public Node getNode(T start) {
        return nodes.get(start);
    }

    public class Node {
        private T content;
        private List<Edge> neighbors = new ArrayList<>();

        public Node(T content) {
            this.content = content;
        }

        public T getContent() {
            return content;
        }

        public List<Edge> getNeighbors() {
            return neighbors;
        }

        private boolean contains(T content) {
            return Objects.equals(this.content, content);
        }
    }

    public class Edge implements Comparable<Edge> {
        private int weight;
        private Node linkedNode;

        public Edge(int weight, Node linkedNode) {
            this.weight = weight;
            this.linkedNode = linkedNode;
        }

        public int getWeight() {
            return weight;
        }

        public Node getLinkedNode() {
            return linkedNode;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

}
