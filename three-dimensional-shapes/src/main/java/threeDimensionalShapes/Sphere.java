package threeDimensionalShapes;

import java.util.Objects;

public class Sphere extends ThreeShape {

    private long radius;

    public Sphere(long radius) {
        this.radius = radius;
    }

    @Override
    public long calculateArea() {
        return (long) (4*Math.PI*Math.pow(radius,2));
    }

    @Override
    public long calculateVolume() {
        return (long) ((4.0/3.0)*Math.PI*Math.pow(radius,3));
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "radius=" + radius +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Sphere sphere = (Sphere) o;
        return radius == sphere.radius;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(radius);
    }
}
