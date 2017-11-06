package pathfinding;

import java.util.*;
import java.util.function.Predicate;

public class DejkstraSearch implements PathSearch<WeighedGraph<IntPoint>> {

    private final Predicate<IntPoint> defaultStopCondition = (ignore) -> false;

    public Map<IntPoint, IntPoint> search(WeighedGraph<IntPoint> graph, IntPoint start, Predicate<IntPoint> stopCondition) {
        stopCondition = stopCondition == null ? defaultStopCondition : stopCondition;

        Map<IntPoint, IntPoint> roots = new HashMap<>();

        Queue<PathComplexity> frontier = new PriorityQueue<>();
        frontier.add(new PathComplexity(graph.getNode(start), 0));
        Set<IntPoint> visited = new HashSet<>();
        visited.add(start);
        PathComplexity currentPoint;

        while (!frontier.isEmpty()) {
            currentPoint = frontier.remove();

            if(stopCondition.test(currentPoint.getNode().getContent())) {
                return roots;
            }

            for (WeighedGraph<IntPoint>.Edge edge : graph.fetchEdges(currentPoint.getNode())) {
                IntPoint point = edge.getLinkedNode().getContent();
                if(!visited.contains(point)) {
                    frontier.add(new PathComplexity(edge.getLinkedNode(), currentPoint.getLength() + edge.getWeight()));
                    visited.add(edge.getLinkedNode().getContent());
                    roots.put(edge.getLinkedNode().getContent(), currentPoint.getNode().getContent());
                }
            }
        }
        return roots;
    }
}
