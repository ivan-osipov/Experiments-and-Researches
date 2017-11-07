package pathfinding;

import graph.WeighedGraph;
import map.IntPoint;

public class PathComplexity implements Comparable<PathComplexity> {
    private WeighedGraph<IntPoint>.Node node;
    private int length;

    public PathComplexity(WeighedGraph<IntPoint>.Node node, int length) {
        this.node = node;
        this.length = length;
    }

    public WeighedGraph<IntPoint>.Node getNode() {
        return node;
    }

    public int getLength() {
        return length;
    }

    @Override
    public int compareTo(PathComplexity o) {
        return Integer.compare(length, o.length);
    }
}
