package geometryLibrary.base.twoDimensionalShapes;

import java.util.Objects;

public class Square extends TwoShape {

    private final long lengthSide;

    public Square(long lengthSide) {
        try {
            validate(lengthSide);
        } catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }
        this.lengthSide = lengthSide;
    }

    @Override
    public long calculateArea() {
        return (long) Math.pow(lengthSide, 2);
    }

    @Override
    public long calculatePerimeter() {
        return 4 * lengthSide;
    }

    private void validate(long side) {
        if (side <= 0) {
            throw new IllegalArgumentException("Длина стороны должна быть положительным числом");
        }
    }

    public long getLengthSide() {
        return lengthSide;
    }

    @Override
    public String toString() {
        return "Square{" +
                "lengthSide=" + lengthSide +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return lengthSide == square.lengthSide;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(lengthSide);
    }
}