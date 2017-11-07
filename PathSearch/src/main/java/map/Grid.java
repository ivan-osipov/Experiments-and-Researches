package map;

import java.util.*;

public class Grid {

    private int width;
    private int height;
    private Set<IntPoint> barriers;
    private Map<IntPoint, Integer> movementCosts;
    private int defaultCost;

    public Grid(int width, int height, int defaultCost) {
        this.width = width;
        this.height = height;
        this.barriers = new HashSet<>();
        this.movementCosts = new HashMap<>();
        this.defaultCost = defaultCost;
    }

    public Grid(int width, int height) {
        this(width, height, 1);
    }

    public void addBarriers(IntPoint... barriers) {
        addBarriers(Arrays.asList(barriers));
    }

    public void addBarriers(Collection<IntPoint> barriers) {
        this.barriers.addAll(barriers);
    }

    public void addPointCosts(int cost, Collection<IntPoint> points) {
        for (IntPoint point : points) {
            this.movementCosts.put(point, cost);
        }
    }

    public void addPointCost(int cost, IntPoint point) {
        this.movementCosts.put(point, cost);
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
        List<IntPoint> neighbors = Arrays.asList(
                IntPoint.of(x + 1, y),
                IntPoint.of(x, y - 1),
                IntPoint.of(x - 1, y),
                IntPoint.of(x, y + 1),
                IntPoint.of(x + 1, y + 1),
                IntPoint.of(x - 1, y + 1),
                IntPoint.of(x - 1, y - 1),
                IntPoint.of(x + 1, y - 1));
        for (IntPoint potentialNeighbor : neighbors) {
            if(inBounds(potentialNeighbor) && isPassable(potentialNeighbor)) {
                results.add(potentialNeighbor);
            }
        }
        return results;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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


    public int getCost(IntPoint point) {
        return movementCosts.getOrDefault(point, defaultCost);
    }
}
