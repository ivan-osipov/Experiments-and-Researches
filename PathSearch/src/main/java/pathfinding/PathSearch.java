package pathfinding;

import map.IntPoint;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public interface PathSearch<GRAPH> {

    Map<IntPoint, IntPoint> search(GRAPH graph, IntPoint start, Predicate<IntPoint> stopCondition);

    default Map<IntPoint, IntPoint> search(GRAPH graph, IntPoint start, IntPoint finish) {
        return search(graph, start, (point) -> Objects.equals(point, finish));
    }

    default List<IntPoint> searchPath(GRAPH graph, IntPoint start, IntPoint finish) {
        Map<IntPoint, IntPoint> map = search(graph, start, finish);
        LinkedList<IntPoint> path = new LinkedList<>();
        IntPoint current = finish;
        do {
            path.addLast(current);
            current = map.get(current);
        } while (current != null);

        return path;
    }
}
