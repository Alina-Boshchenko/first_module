package geometryLibrary.base.twoDimensionalShapes;

import java.util.Objects;

public class Circle extends TwoShape {

    private final long radius;

    public Circle(long radius) {
        try {
            validate(radius);
        } catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }
        this.radius = radius;
    }

    @Override
    public long calculateArea() {
        return (long) (Math.PI*Math.pow(radius,2));
    }

    @Override
    public long calculatePerimeter() {
        return (long) (2*Math.PI*radius);
    }

    private void validate(long radius){
        if (radius <= 0) {
            throw new IllegalArgumentException("Радиус должен быть положительным числом");
        }
    }

    public long getRadius() {
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
        return radius == circle.radius;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(radius);
    }
}
