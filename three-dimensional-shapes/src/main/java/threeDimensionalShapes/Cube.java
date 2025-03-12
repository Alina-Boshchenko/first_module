package threeDimensionalShapes;

import java.util.Objects;

public class Cube extends ThreeShape {

    private long lengthSide;

    public Cube(long lengthSide) {
        this.lengthSide = lengthSide;
    }

    @Override
    public long calculateArea() {
        return (long) (6*Math.pow(lengthSide,2));
    }

    @Override
    public long calculateVolume() {
        return (long) Math.pow(lengthSide,3);
    }

    @Override
    public String toString() {
        return "Cube{" +
                "lengthSide=" + lengthSide +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cube cube = (Cube) o;
        return lengthSide == cube.lengthSide;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(lengthSide);
    }
}
