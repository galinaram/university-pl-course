
import org.junit.jupiter.api.Test;
import org.unecon.Point;
import org.unecon.Polygon;

import static org.junit.jupiter.api.Assertions.*;

class PolygonTest {

    @Test
    void testPolygonWithAlmostCollinearPoints() {
        Point[] almostCollinearPoints = {
                new Point(0, 0),
                new Point(1, 0.001),
                new Point(2, 0),
                new Point(1, -0.001)
        };

        // Этот четырехугольник должен быть выпуклым
        assertDoesNotThrow(() -> new Polygon(almostCollinearPoints));

        Polygon polygon = new Polygon(almostCollinearPoints);
        assertTrue(polygon.square() > 1e-10);
    }

    @Test
    void testPolygonWithRepeatedPoints() {
        Point[] repeatedPoints = {
                new Point(0, 0),
                new Point(1, 0),
                new Point(1, 0), // Точная повторяющаяся точка
                new Point(0, 1)
        };
        assertThrows(IllegalArgumentException.class, () -> new Polygon(repeatedPoints));
    }

    @Test
    void testPolygonWithVeryClosePoints() {
        Point[] closePoints = {
                new Point(0, 0),
                new Point(1, 0),
                new Point(1 + 1e-13, 1e-13), // Практически та же точка
                new Point(0, 1)
        };
        assertThrows(IllegalArgumentException.class, () -> new Polygon(closePoints));
    }

    @Test
    void testPolygonWithCollinearPointsThrowsException() {
        Point[] collinearPoints = {
                new Point(0, 0),
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3)
        };
        assertThrows(IllegalArgumentException.class, () -> new Polygon(collinearPoints));
    }

    @Test
    void testPolygonWithDegenerateTriangle() {
        Point[] degenerateTriangle = {
                new Point(0, 0),
                new Point(1, 1),
                new Point(2, 2)
        };
        assertThrows(IllegalArgumentException.class, () -> new Polygon(degenerateTriangle));
    }

    @Test
    void testValidPolygonWithSmallArea() {
        Point[] smallAreaPoints = {
                new Point(0, 0),
                new Point(1, 0.0001), // Малая, но ненулевая площадь
                new Point(0, 0.0001)
        };
        assertDoesNotThrow(() -> new Polygon(smallAreaPoints));

        Polygon polygon = new Polygon(smallAreaPoints);
        assertTrue(polygon.square() > 1e-10); // Площадь очень мала, но не нуль
    }

    // Остальные существующие тесты остаются без изменений
    @Test
    void testTriangle() {
        Point[] points = {
                new Point(0, 0),
                new Point(4, 0),
                new Point(0, 3)
        };
        Polygon triangle = new Polygon(points);

        assertEquals(12.0, triangle.perimeter(), 1e-10);
        assertEquals(6.0, triangle.square(), 1e-10);
    }

    @Test
    void testSquarePolygon() {
        Point[] points = {
                new Point(0, 0),
                new Point(4, 0),
                new Point(4, 4),
                new Point(0, 4)
        };
        Polygon square = new Polygon(points);

        assertEquals(16.0, square.perimeter(), 1e-10);
        assertEquals(16.0, square.square(), 1e-10);
    }
}