package demo;

import graph.GraphBuilder;
import graph.WeighedGraph;
import map.Grid;
import map.IntPoint;
import pathfinding.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DejkstraSearchDemo extends PathSearchDemo {

    public static void main(String[] args) {
        Grid grid = makeMap();

        WeighedGraph<IntPoint> graph = new GraphBuilder(grid).buildWeighted();

        IntPoint startPoint = IntPoint.of(5, 15);
        IntPoint finishPoint = IntPoint.of(85, 15);

        long time = System.currentTimeMillis();
        List<IntPoint> bestPath = new DejkstraSearch().searchPath(graph, startPoint, finishPoint);
        System.out.println(System.currentTimeMillis() - time);
        Set<IntPoint> usedPoints = new HashSet<>(bestPath);
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
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
                } else if(grid.getCost(point) > 1) {
                    pointChar = Character.forDigit(grid.getCost(point), 10);
                }
                System.out.print(pointChar);
            }
            System.out.println();
        }
    }

}
