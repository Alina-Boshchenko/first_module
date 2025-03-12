package geometryLibrary.base.twoDimensionalShapes;

import java.util.Objects;

public class Rectangle extends TwoShape {

    private final double width;
    private final double length;

    public Rectangle(double width, double length) {
        try {
            validate(width, length);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        this.width = width;
        this.length = length;
    }

    @Override
    public double calculateArea() {
        return width * length;
    }

    @Override
    public double calculatePerimeter() {
        return (2 * (width + length));
    }

    private void validate(double width, double length) {
        if (width <= 0 || length <= 0) {
            throw new IllegalArgumentException("Все стороны должны быть положительными");
        }
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "width=" + width +
                ", length=" + length +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return Double.compare(width, rectangle.width) == 0 && Double.compare(length, rectangle.length) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, length);
    }
}
