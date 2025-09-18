package org.unecon;

import java.util.Arrays;

public class Polygon implements Figure {
    private final Point[] vertices;

    public Polygon(Point[] vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("Vertices array cannot be null");
        }
        if (vertices.length < 3) {
            throw new IllegalArgumentException("Polygon must have at least 3 vertices");
        }

        for (Point vertex : vertices) {
            if (vertex == null) {
                throw new IllegalArgumentException("Vertices cannot contain null elements");
            }
        }

        this.vertices = Arrays.copyOf(vertices, vertices.length);

        // Проверка на дубликаты точек
        if (hasDuplicatePoints()) {
            throw new IllegalArgumentException("Polygon cannot have duplicate points");
        }

        // Проверка на выпуклость
        if (!isConvex()) {
            throw new IllegalArgumentException("Polygon must be convex");
        }

        // Проверка на вырожденность (нулевую площадь)
        if (Math.abs(calculateArea()) < 1e-12) {
            throw new IllegalArgumentException("Polygon has zero area (collinear points)");
        }
    }

    private boolean isConvex() {
        int n = vertices.length;
        if (n < 3) return false;

        int sign = 0;
        boolean hasNonZeroCrossProduct = false;

        for (int i = 0; i < n; i++) {
            Point a = vertices[i];
            Point b = vertices[(i + 1) % n];
            Point c = vertices[(i + 2) % n];

            double cross = (b.x() - a.x()) * (c.y() - b.y()) -
                    (b.y() - a.y()) * (c.x() - b.x());

            if (Math.abs(cross) > 1e-12) {
                hasNonZeroCrossProduct = true;
                int currentSign = (int) Math.signum(cross);
                if (sign == 0) {
                    sign = currentSign;
                } else if (sign != currentSign) {
                    return false;
                }
            }
        }

        return hasNonZeroCrossProduct;
    }

    private boolean hasDuplicatePoints() {
        for (int i = 0; i < vertices.length; i++) {
            for (int j = i + 1; j < vertices.length; j++) {
                if (Math.abs(vertices[i].x() - vertices[j].x()) < 1e-12 &&
                        Math.abs(vertices[i].y() - vertices[j].y()) < 1e-12) {
                    return true;
                }
            }
        }
        return false;
    }

    private double calculateArea() {
        double area = 0;
        int n = vertices.length;

        for (int i = 0; i < n; i++) {
            int j = (i + 1) % n;
            area += vertices[i].x() * vertices[j].y();
            area -= vertices[j].x() * vertices[i].y();
        }

        return Math.abs(area) / 2.0;
    }

    @Override
    public double perimeter() {
        double total = 0;
        int n = vertices.length;

        for (int i = 0; i < n; i++) {
            Point current = vertices[i];
            Point next = vertices[(i + 1) % n];
            total += current.distance(next);
        }

        return total;
    }

    @Override
    public double square() {
        return calculateArea();
    }
}