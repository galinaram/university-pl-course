import org.junit.jupiter.api.Test;
import org.unecon.Square;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {

    @Test
    void testValidSquare() {
        Square square = new Square(4.0);
        assertEquals(16.0, square.perimeter(), 1e-10);
        assertEquals(16.0, square.square(), 1e-10);
    }

    @Test
    void testSquareWithSmallSide() {
        Square square = new Square(0.5);
        assertEquals(2.0, square.perimeter(), 1e-10);
        assertEquals(0.25, square.square(), 1e-10);
    }

    @Test
    void testSquareWithLargeSide() {
        Square square = new Square(100.0);
        assertEquals(400.0, square.perimeter(), 1e-10);
        assertEquals(10000.0, square.square(), 1e-10);
    }

    @Test
    void testSquareWithZeroSideThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Square(0.0));
    }

    @Test
    void testSquareWithNegativeSideThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Square(-2.0));
    }

    @Test
    void testSquareWithNaNSideThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Square(Double.NaN));
    }
}