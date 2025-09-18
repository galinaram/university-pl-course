package org.unecon;

public class Circle implements Figure {
    private final double radius;

    public Circle(double radius) {
        if (radius <= 0 || !Double.isFinite(radius)) {
            throw new IllegalArgumentException("Radius must be positive");
        }
        this.radius = radius;
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public double square() {
        return Math.PI * radius * radius;
    }
}