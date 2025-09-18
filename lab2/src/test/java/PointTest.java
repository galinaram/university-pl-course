import org.junit.jupiter.api.Test;
import org.unecon.Point;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void testPointCreation() {
        Point point = new Point(3.0, 4.0);
        assertEquals(3.0, point.x(), 1e-10);
        assertEquals(4.0, point.y(), 1e-10);
    }

    @Test
    void testDistanceBetweenPoints() {
        Point p1 = new Point(0.0, 0.0);
        Point p2 = new Point(3.0, 4.0);
        assertEquals(5.0, p1.distance(p2), 1e-10);
        assertEquals(5.0, p2.distance(p1), 1e-10);
    }

    @Test
    void testDistanceToSamePoint() {
        Point point = new Point(5.0, 5.0);
        assertEquals(0.0, point.distance(point), 1e-10);
    }

    @Test
    void testPointWithNaNCoordinatesThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Point(Double.NaN, 1.0));
        assertThrows(IllegalArgumentException.class, () -> new Point(1.0, Double.NaN));
    }

    @Test
    void testPointWithInfiniteCoordinatesThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Point(Double.POSITIVE_INFINITY, 1.0));
        assertThrows(IllegalArgumentException.class, () -> new Point(1.0, Double.NEGATIVE_INFINITY));
    }
}