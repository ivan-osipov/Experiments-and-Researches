package demo;

import map.Grid;
import map.IntPoint;

import java.util.HashSet;
import java.util.Set;

public class PathSearchDemo {
    protected static Grid makeMap() {
        Grid grid = new Grid(100, 50);

        Set<IntPoint> barriers = new HashSet<>();
        for (int i = 25; i < 30; i++) {
            for (int j = 0; j < 25; j++) {
                barriers.add(IntPoint.of(i, j));
            }

            for (int j = 27; j < 100; j++) {
                barriers.add(IntPoint.of(i, j));
            }
        }
        grid.addBarriers(barriers);

        for (int x = 40; x < 70; x++) {
            for (int y = 0; y < 45; y++) {
                grid.addPointCost(2, IntPoint.of(x, y));
            }
        }

        for (int x = 73; x < 76; x++) {
            for (int y = 10; y < 30; y++) {
                grid.addPointCost(4, IntPoint.of(x, y));
            }
        }
        return grid;
    }
}
