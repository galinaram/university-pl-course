package org.unecon;

public class Square implements Figure {
    private final double side;

    public Square(double side) {
        if (side <= 0 || !Double.isFinite(side)) {
            throw new IllegalArgumentException("Side must be positive");
        }
        this.side = side;
    }

    @Override
    public double perimeter() {
        return 4 * side;
    }

    @Override
    public double square() {
        return side * side;
    }
}