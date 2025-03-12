package geometryLibrary.base.twoDimensionalShapes;

import java.util.Objects;

public class Square extends TwoShape {

    private final double lengthSide;

    public Square(double lengthSide) {
        try {
            validate(lengthSide);
        } catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }
        this.lengthSide = lengthSide;
    }

    @Override
    public double calculateArea() {
        return Math.pow(lengthSide, 2);
    }

    @Override
    public double calculatePerimeter() {
        return 4 * lengthSide;
    }

    private void validate(double side) {
        if (side <= 0) {
            throw new IllegalArgumentException("Длина стороны должна быть положительным числом");
        }
    }

    public double getLengthSide() {
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
        return Double.compare(lengthSide, square.lengthSide) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(lengthSide);
    }
}