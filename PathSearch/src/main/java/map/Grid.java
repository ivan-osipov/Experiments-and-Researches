package map;

import pathfinding.IntPoint;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Grid {

    private int width;
    private int height;
    private Set<IntPoint> barriers;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.barriers = new HashSet<>();
    }

    public void addBarriers(IntPoint... barriers) {
        addBarriers(Arrays.asList(barriers));
    }

    public void addBarriers(Collection<IntPoint> barriers) {
        this.barriers.addAll(barriers);
    }

    public boolean inBounds(IntPoint point) {
        int x = point.getX();
        int y = point.getY();
        return 0 <= x && x < width && 0 <= y && y < height;
    }

    public boolean isPassable(IntPoint point) {
        return !barriers.contains(point);
    }

    public Set<IntPoint> fetchNeighbors(IntPoint point) {
        int x = point.getX();
        int y = point.getY();
        Set<IntPoint> results = new HashSet<>();
        for (IntPoint potentialNeighbor : Arrays.asList(IntPoint.of(x + 1, y), IntPoint.of(x, y - 1), IntPoint.of(x - 1, y), IntPoint.of(x, y + 1))) {
            if(inBounds(potentialNeighbor) && isPassable(potentialNeighbor)) {
                results.add(potentialNeighbor);
            }
        }
        return results;
    }

    public Set<IntPoint> getPassablePoints() {
        Set<IntPoint> passablePoints = new HashSet<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                IntPoint point = IntPoint.of(x, y);
                if(isPassable(point)) {
                    passablePoints.add(point);
                }
            }
        }

        return passablePoints;
    }


}
