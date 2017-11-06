package pathfinding;

import map.Grid;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GraphBuilder {
    
    private Grid grid;
    
    public GraphBuilder(Grid grid) {
        this.grid = grid;
    }

    public Graph buildSimple() {
        Graph graph = new Graph();
        Set<IntPoint> passablePoints = grid.getPassablePoints();
        for (IntPoint passablePoint : passablePoints) {
            Set<IntPoint> neighbors = grid.fetchNeighbors(passablePoint);
            graph.addEdges(passablePoint, neighbors);
        }

        return graph;
    }

    public WeighedGraph<IntPoint> buildWeighted() {
        WeighedGraph<IntPoint> graph = new WeighedGraph<>();
        Map<IntPoint, WeighedGraph<IntPoint>.Node> nodes = new HashMap<>();
        for (IntPoint passablePoint : grid.getPassablePoints()) {
            Set<IntPoint> neighbors = grid.fetchNeighbors(passablePoint);
            WeighedGraph<IntPoint>.Node node = nodes.computeIfAbsent(passablePoint, graph::createNode);
            for (IntPoint neighbor : neighbors) {
                WeighedGraph<IntPoint>.Node neighborNode = nodes.computeIfAbsent(neighbor, graph::createNode);
                boolean sameTimeChangeTwoCoordinates = node.getContent().getX() != neighborNode.getContent().getX() && node.getContent().getY() != neighborNode.getContent().getY();
                graph.addEdge(node, neighborNode, (int)((grid.getCost(neighbor) * 10) * (sameTimeChangeTwoCoordinates ? 1.5 : 0.0)));
            }
        }
        return graph;
    }
    
}
