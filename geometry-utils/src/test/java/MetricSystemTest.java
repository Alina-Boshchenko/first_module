import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import geometryUtils.base.MetricSystem;

public class MetricSystemTest {

    @Test
    public void testToMillimeters() {
        assertEquals(1000, MetricSystem.METERS.toMillimeters(1));
        assertEquals(100, MetricSystem.CENTIMETERS.toMillimeters(10));
        assertEquals(1, MetricSystem.MILLIMETER.toMillimeters(1));
    }

    @Test
    public void testToCentimeters() {
        assertEquals(100, MetricSystem.METERS.toCentimeters(1));
        assertEquals(10, MetricSystem.DECIMETERS.toCentimeters(1));
        assertEquals(1, MetricSystem.CENTIMETERS.toCentimeters(1));
    }

    @Test
    public void testToDecimeters() {
        assertEquals(10, MetricSystem.METERS.toDecimeters(1));
        assertEquals(1, MetricSystem.DECIMETERS.toDecimeters(1));
        assertEquals(0, MetricSystem.CENTIMETERS.toDecimeters(5));
    }

    @Test
    public void testToMeters() {
        assertEquals(1, MetricSystem.METERS.toMeters(1));
        assertEquals(1000, MetricSystem.KILOMETERS.toMeters(1));
        assertEquals(0, MetricSystem.CENTIMETERS.toMeters(50));
    }

    @Test
    public void testToKilometers() {
        assertEquals(1, MetricSystem.KILOMETERS.toKilometers(1));
        assertEquals(1, MetricSystem.METERS.toKilometers(1000));
        assertEquals(0, MetricSystem.METERS.toKilometers(500));
    }
}