package geometryLibrary.base.twoDimensionalShapes;

import java.util.Objects;

public class Rectangle extends TwoShape {

    private final long width;
    private final long length;

    public Rectangle(long width, long length) {
        try {
            validate(width, length);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        this.width = width;
        this.length = length;
    }

    @Override
    public long calculateArea() {
        return width * length;
    }

    @Override
    public long calculatePerimeter() {
        return (2 * (width + length));
    }

    private void validate(long width, long length) {
        if (width <= 0 || length <= 0) {
            throw new IllegalArgumentException("Все стороны должны быть положительными");
        }
    }

    public long getWidth() {
        return width;
    }

    public long getLength() {
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
        return width == rectangle.width && length == rectangle.length;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, length);
    }
}
