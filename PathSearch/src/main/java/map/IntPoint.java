package map;

public class IntPoint {

    private int x;
    private int y;

    public IntPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntPoint intPoint = (IntPoint) o;

        if (x != intPoint.x) return false;
        return y == intPoint.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public static IntPoint of(int x, int y) {
        return new IntPoint(x, y);
    }

    public double distanceTo(IntPoint point) {
        return Math.hypot(x - point.getX(), y - point.getY());
    }
}
