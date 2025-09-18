import org.junit.jupiter.api.Test;
import org.unecon.Circle;

import static org.junit.jupiter.api.Assertions.*;

class CircleTest {

    @Test
    void testValidCircle() {
        Circle circle = new Circle(5.0);
        assertEquals(2 * Math.PI * 5.0, circle.perimeter(), 1e-10);
        assertEquals(Math.PI * 25.0, circle.square(), 1e-10);
    }

    @Test
    void testCircleWithSmallRadius() {
        Circle circle = new Circle(0.1);
        assertEquals(2 * Math.PI * 0.1, circle.perimeter(), 1e-10);
        assertEquals(Math.PI * 0.01, circle.square(), 1e-10);
    }

    @Test
    void testCircleWithLargeRadius() {
        Circle circle = new Circle(1000.0);
        assertEquals(2 * Math.PI * 1000.0, circle.perimeter(), 1e-10);
        assertEquals(Math.PI * 1000000.0, circle.square(), 1e-10);
    }

    @Test
    void testCircleWithZeroRadiusThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Circle(0.0));
    }

    @Test
    void testCircleWithNegativeRadiusThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Circle(-5.0));
    }

    @Test
    void testCircleWithNaNRadiusThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Circle(Double.NaN));
    }

    @Test
    void testCircleWithInfiniteRadiusThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Circle(Double.POSITIVE_INFINITY));
    }
}