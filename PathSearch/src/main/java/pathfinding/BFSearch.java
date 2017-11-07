package pathfinding;

import graph.Graph;
import map.IntPoint;

import java.util.*;
import java.util.function.Predicate;

public class BFSearch implements PathSearch<Graph> {

    private final Predicate<IntPoint> defaultStopCondition = (ignore) -> false;

    @Override
    public Map<IntPoint, IntPoint> search(Graph graph, IntPoint start, Predicate<IntPoint> stopCondition) {
        stopCondition = stopCondition == null ? defaultStopCondition : stopCondition;

        Map<IntPoint, IntPoint> roots = new HashMap<>();

        Queue<IntPoint> frontier = new LinkedList<>();
        frontier.add(start);
        Set<IntPoint> visited = new HashSet<>();
        visited.add(start);
        IntPoint currentPoint;

        while (!frontier.isEmpty()) {
            currentPoint = frontier.remove();

            if(stopCondition.test(currentPoint)) {
                return roots;
            }

            for (IntPoint nextPoint : graph.fetchNeighbors(currentPoint)) {
                if(!visited.contains(nextPoint)) {
                    frontier.add(nextPoint);
                    visited.add(nextPoint);
                    roots.put(nextPoint, currentPoint);
                }
            }
        }
        return roots;
    }

}
