package geometryLibrary.base.twoDimensionalShapes;

import java.util.Objects;

public class Triangle extends TwoShape {

    private final double lengthFirstSide;
    private final double lengthSecondSide;
    private final double lengthThirdSide;

    public Triangle(double lengthFirstSide, double lengthSecondSide, double lengthThirdSide) {
        try {
            validate(lengthFirstSide,lengthSecondSide,lengthThirdSide);
        } catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }
        this.lengthFirstSide = lengthFirstSide;
        this.lengthSecondSide = lengthSecondSide;
        this.lengthThirdSide = lengthThirdSide;
    }

    @Override
    public double calculateArea() {
        double semiPerimeter = calculatePerimeter() / 2;
        return Math.sqrt(semiPerimeter * (semiPerimeter - lengthFirstSide) * (semiPerimeter - lengthSecondSide) * (semiPerimeter - lengthThirdSide));
    }

    @Override
    public double calculatePerimeter() {
        return lengthFirstSide + lengthSecondSide + lengthThirdSide;
    }

    private void validate(double a, double b, double c) {
        if (a <= 0 || b <= 0 || c <= 0) {
            throw new IllegalArgumentException("Все стороны должны быть положительными");
        } else if (a + b <= c || a + c <= b || b + c <= a) {
            throw new IllegalArgumentException("Сумма любых двух сторон должна быть больше третьей");
        }
    }

    public double getLengthFirstSide() {
        return lengthFirstSide;
    }

    public double getLengthSecondSide() {
        return lengthSecondSide;
    }

    public double getLengthThirdSide() {
        return lengthThirdSide;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "lengthFirstSide=" + lengthFirstSide +
                ", lengthSecondSide=" + lengthSecondSide +
                ", lengthThirdSide=" + lengthThirdSide +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return Double.compare(lengthFirstSide, triangle.lengthFirstSide) == 0 && Double.compare(lengthSecondSide, triangle.lengthSecondSide) == 0 && Double.compare(lengthThirdSide, triangle.lengthThirdSide) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lengthFirstSide, lengthSecondSide, lengthThirdSide);
    }
}
