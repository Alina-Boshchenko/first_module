package geometryLibrary.base.twoDimensionalShapes;

import java.util.Objects;


public class Triangle extends TwoShape {

    private final long lengthFirstSide;
    private final long lengthSecondSide;
    private final long lengthThirdSide;

    public Triangle(long lengthFirstSide, long lengthSecondSide, long lengthThirdSide) {
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
    public long calculateArea() {
        double semiPerimeter = calculatePerimeter() / 2;
        return (long) Math.sqrt(semiPerimeter * (semiPerimeter - lengthFirstSide) * (semiPerimeter - lengthSecondSide) * (semiPerimeter - lengthThirdSide));
    }

    @Override
    public long calculatePerimeter() {
        return lengthFirstSide + lengthSecondSide + lengthThirdSide;
    }

    private void validate(long a, long b, long c) {
        if (a <= 0 || b <= 0 || c <= 0) {
            throw new IllegalArgumentException("Все стороны должны быть положительными");
        } else if (a + b <= c || a + c <= b || b + c <= a) {
            throw new IllegalArgumentException("Сумма любых двух сторон должна быть больше третьей");
        }
    }

    public long getLengthFirstSide() {
        return lengthFirstSide;
    }

    public long getLengthSecondSide() {
        return lengthSecondSide;
    }

    public long getLengthThirdSide() {
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
        return lengthFirstSide == triangle.lengthFirstSide && lengthSecondSide == triangle.lengthSecondSide && lengthThirdSide == triangle.lengthThirdSide;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lengthFirstSide, lengthSecondSide, lengthThirdSide);
    }
}
