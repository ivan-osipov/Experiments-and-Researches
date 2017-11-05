package pathfinding;

import java.util.*;
import java.util.function.Predicate;

public class BFSearch<T> implements PathSearch<T> {

    private final Predicate<T> defaultStopCondition = (ignore) -> false;

    @Override
    public Map<T, T> search(Graph<T> graph, T start, Predicate<T> stopCondition) {
        stopCondition = stopCondition == null ? defaultStopCondition : stopCondition;

        Map<T, T> roots = new HashMap<>();

        Queue<T> frontier = new LinkedList<>();
        frontier.add(start);
        Set<T> visited = new HashSet<>();
        visited.add(start);
        T currentPoint;

        while (!frontier.isEmpty()) {
            currentPoint = frontier.remove();

            if(stopCondition.test(currentPoint)) {
                return roots;
            }

            for (T nextPoint : graph.fetchNeighbors(currentPoint)) {
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
