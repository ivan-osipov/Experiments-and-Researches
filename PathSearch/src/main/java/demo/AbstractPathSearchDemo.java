package demo;

import map.Grid;
import pathfinding.Graph;
import pathfinding.IntPoint;
import pathfinding.PathSearch;
import pathfinding.SquareGraphBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AbstractPathSearchDemo {
    public static void runDemoAlgorithm(PathSearch<IntPoint> algorithm) {
        Grid grid = makeGraph();
        Graph<IntPoint> graph = new SquareGraphBuilder(grid).build();

        IntPoint startPoint = IntPoint.of(5, 15);
        IntPoint finishPoint = IntPoint.of(85, 15);

        List<IntPoint> bestPath = algorithm.searchPath(graph, startPoint, finishPoint);
        Set<IntPoint> usedPoints = new HashSet<>(bestPath);
        for (int y = 0; y < 100; y++) {
            for (int x = 0; x < 100; x++) {
                IntPoint point = IntPoint.of(x, y);
                char pointChar = 'x';
                if(!grid.isPassable(point)) {
                    pointChar = '#';
                } else if(point.equals(startPoint)) {
                    pointChar = 'S';
                } else if(point.equals(finishPoint)) {
                    pointChar = 'E';
                } else if(usedPoints.contains(point)) {
                    pointChar = '-';
                }
                System.out.print(pointChar);
            }
            System.out.println();
        }
    }

    private static Grid makeGraph() {
        Grid grid = new Grid(100, 100);

        Set<IntPoint> barriers = new HashSet<>();
        for (int i = 25; i < 30; i++) {
            for (int j = 10; j < 25; j++) {
                barriers.add(IntPoint.of(i, j));
            }
        }
        grid.addBarriers(barriers);
        return grid;
    }
}
