package pathfinding;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public interface PathSearch<T> {

    Map<T, T> search(Graph<T> graph, T start, Predicate<T> stopCondition);

    default Map<T, T> search(Graph<T> graph, T start, T finish) {
        return search(graph, start, (point) -> Objects.equals(point, finish));
    }

    default List<T> searchPath(Graph<T> graph, T start, T finish) {
        Map<T, T> map = search(graph, start, finish);
        LinkedList<T> path = new LinkedList<>();
        T current = finish;
        do {
            path.addLast(current);
            current = map.get(current);
        } while (current != null);

        return path;
    }
}
