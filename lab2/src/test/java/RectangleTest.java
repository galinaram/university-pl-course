import org.junit.jupiter.api.Test;
import org.unecon.Rectangle;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {

    @Test
    void testValidRectangle() {
        Rectangle rectangle = new Rectangle(3.0, 4.0);
        assertEquals(14.0, rectangle.perimeter(), 1e-10);
        assertEquals(12.0, rectangle.square(), 1e-10);
    }

    @Test
    void testRectangleSquare() {
        Rectangle rectangle = new Rectangle(5.0, 5.0);
        assertEquals(20.0, rectangle.perimeter(), 1e-10);
        assertEquals(25.0, rectangle.square(), 1e-10);
    }

    @Test
    void testRectangleWithSmallSides() {
        Rectangle rectangle = new Rectangle(0.1, 0.2);
        assertEquals(0.6, rectangle.perimeter(), 1e-10);
        assertEquals(0.02, rectangle.square(), 1e-10);
    }

    @Test
    void testRectangleWithZeroWidthThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(0.0, 5.0));
    }

    @Test
    void testRectangleWithZeroHeightThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(5.0, 0.0));
    }

    @Test
    void testRectangleWithNegativeSidesThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(-3.0, 4.0));
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(3.0, -4.0));
    }

    @Test
    void testRectangleWithNaNSidesThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(Double.NaN, 4.0));
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(3.0, Double.NaN));
    }
}