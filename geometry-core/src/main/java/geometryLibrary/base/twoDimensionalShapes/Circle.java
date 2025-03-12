package geometryLibrary.base.twoDimensionalShapes;

import java.util.Objects;

public class Circle extends TwoShape {

    private final double radius;

    public Circle(double radius) {
        try {
            validate(radius);
        } catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI*Math.pow(radius,2);
    }

    @Override
    public double calculatePerimeter() {
        return 2*Math.PI*radius;
    }

    private void validate(double radius){
        if (radius <= 0) {
            throw new IllegalArgumentException("Радиус должен быть положительным числом");
        }
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "radius=" + radius +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Circle circle = (Circle) o;
        return Double.compare(radius, circle.radius) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(radius);
    }
}
