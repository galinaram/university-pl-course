package org.unecon;

public class Task2 {
    public static void main(String[] args) {
        // Демонстрация работы всех фигур

        System.out.println("=== Демонстрация геометрических фигур ===\n");

        // Круг
        Circle circle = new Circle(5.0);
        System.out.println("Круг с радиусом 5.0:");
        System.out.printf("Длина окружности: %.2f%n", circle.perimeter());
        System.out.printf("Площадь: %.2f%n", circle.square());
        System.out.println();

        // Квадрат
        Square square = new Square(4.0);
        System.out.println("Квадрат со стороной 4.0:");
        System.out.printf("Периметр: %.2f%n", square.perimeter());
        System.out.printf("Площадь: %.2f%n", square.square());
        System.out.println();

        // Прямоугольник
        Rectangle rectangle = new Rectangle(3.0, 6.0);
        System.out.println("Прямоугольник 3.0 x 6.0:");
        System.out.printf("Периметр: %.2f%n", rectangle.perimeter());
        System.out.printf("Площадь: %.2f%n", rectangle.square());
        System.out.println();

        // Треугольник
        Point[] trianglePoints = {
                new Point(0, 0),
                new Point(4, 0),
                new Point(0, 3)
        };
        Polygon triangle = new Polygon(trianglePoints);
        System.out.println("Треугольник с точками (0,0), (4,0), (0,3):");
        System.out.printf("Периметр: %.2f%n", triangle.perimeter());
        System.out.printf("Площадь: %.2f%n", triangle.square());
        System.out.println();

        // Квадрат как многоугольник
        Point[] squarePoints = {
                new Point(0, 0),
                new Point(4, 0),
                new Point(4, 4),
                new Point(0, 4)
        };
        Polygon squarePolygon = new Polygon(squarePoints);
        System.out.println("Квадрат (как многоугольник) со стороной 4:");
        System.out.printf("Периметр: %.2f%n", squarePolygon.perimeter());
        System.out.printf("Площадь: %.2f%n", squarePolygon.square());
        System.out.println();

        // Пятиугольник
        Point[] pentagonPoints = {
                new Point(0, 0),
                new Point(2, 1),
                new Point(1, 3),
                new Point(-1, 3),
                new Point(-2, 1)
        };
        Polygon pentagon = new Polygon(pentagonPoints);
        System.out.println("Пятиугольник с вершинами:");
        printPoints(pentagonPoints);
        System.out.printf("Периметр: %.2f%n", pentagon.perimeter());
        System.out.printf("Площадь: %.2f%n", pentagon.square());
        System.out.println();

        // Демонстрация полиморфизма
        System.out.println("=== Демонстрация полиморфизма ===");
        Figure[] figures = {
                new Circle(2.0),
                new Square(3.0),
                new Rectangle(4.0, 5.0),
                new Polygon(new Point[]{
                        new Point(0, 0),
                        new Point(6, 0),
                        new Point(0, 8)
                })
        };

        for (int i = 0; i < figures.length; i++) {
            System.out.printf("Фигура %d: Периметр = %.2f, Площадь = %.2f%n",
                    i + 1, figures[i].perimeter(), figures[i].square());
        }

        System.out.println("\n=== Программа завершена успешно ===");
    }

    private static void printPoints(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            System.out.printf("  Точка %d: (%.1f, %.1f)%n",
                    i + 1, points[i].x(), points[i].y());
        }
    }
}