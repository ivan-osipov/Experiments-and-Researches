package pathfinding;

import map.Grid;

import java.util.Set;

public class SquareGraphBuilder {
    
    private Grid grid;
    
    public SquareGraphBuilder(int width, int height) {
        grid = new Grid(width, height);
    }

    public SquareGraphBuilder(Grid grid) {
        this.grid = grid;
    }

    public Graph<IntPoint> build() {
        Graph<IntPoint> graph = new Graph<>();
        Set<IntPoint> passablePoints = grid.getPassablePoints();
        for (IntPoint passablePoint : passablePoints) {
            Set<IntPoint> neighbors = grid.fetchNeighbors(passablePoint);
            graph.addEdges(passablePoint, neighbors);
        }

        return graph;
    }
    
}
