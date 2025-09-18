package org.unecon;

public class Rectangle implements Figure {
    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        if (width <= 0 || height <= 0 || !Double.isFinite(width) || !Double.isFinite(height)) {
            throw new IllegalArgumentException("Sides must be positive");
        }
        this.width = width;
        this.height = height;
    }

    @Override
    public double perimeter() {
        return 2 * (width + height);
    }

    @Override
    public double square() {
        return width * height;
    }
}