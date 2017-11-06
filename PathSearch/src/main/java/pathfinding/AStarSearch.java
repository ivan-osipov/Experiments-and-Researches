package pathfinding;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class AStarSearch implements PathSearch<WeighedGraph<IntPoint>> {

    private final Predicate<IntPoint> defaultStopCondition = (ignore) -> false;
    private Function<IntPoint, Double> heuristic;

    public AStarSearch(Function<IntPoint, Double> heuristic) {
        this.heuristic = heuristic;
    }

    public Map<IntPoint, IntPoint> search(WeighedGraph<IntPoint> graph, IntPoint start, Predicate<IntPoint> stopCondition) {
        stopCondition = stopCondition == null ? defaultStopCondition : stopCondition;

        Map<IntPoint, IntPoint> roots = new HashMap<>();

        Queue<PathComplexity> frontier = new PriorityQueue<>();
        frontier.add(new PathComplexity(graph.getNode(start), heuristic.apply(start)));
        Set<IntPoint> visited = new HashSet<>();
        visited.add(start);
        PathComplexity currentPoint;

        while (!frontier.isEmpty()) {
            currentPoint = frontier.remove();

            if(stopCondition.test(currentPoint.node.getContent())) {
                return roots;
            }

            for (WeighedGraph<IntPoint>.Edge edge : graph.fetchEdges(currentPoint.node)) {
                IntPoint point = edge.getLinkedNode().getContent();
                if(!visited.contains(point)) {
                    frontier.add(new PathComplexity(edge.getLinkedNode(), currentPoint.length + edge.getWeight() + heuristic.apply(point)));
                    visited.add(edge.getLinkedNode().getContent());
                    roots.put(edge.getLinkedNode().getContent(), currentPoint.node.getContent());
                }
            }
        }
        return roots;
    }

    private static class PathComplexity implements Comparable<PathComplexity> {
        private WeighedGraph<IntPoint>.Node node;
        private double length;

        public PathComplexity(WeighedGraph<IntPoint>.Node node, double length) {
            this.node = node;
            this.length = length;
        }

        @Override
        public int compareTo(PathComplexity o) {
            return Double.compare(length, o.length);
        }
    }
}
