package geometryUtils.base;

public enum MetricSystem {

    MILLIMETER(MetricSystem.MILLI_METER),
    CENTIMETERS(MetricSystem.CENTI_METERS),
    DECIMETERS(MetricSystem.DECI_METERS),
    METERS(MetricSystem.ME_TERS),
    KILOMETERS(MetricSystem.KILO_METERS);


    private final long basicUnit;

    private static final long MILLI_METER = 1L;
    private static final long CENTI_METERS = 10 * MILLI_METER;
    private static final long DECI_METERS = 10 * CENTI_METERS;
    private static final long ME_TERS = 10 * DECI_METERS;
    private static final long KILO_METERS = 1000 * ME_TERS;

    MetricSystem(long basicUnit) {
        this.basicUnit = basicUnit;
    }
    private long convert(long value, MetricSystem targetMetric){
        return (value * this.basicUnit) / targetMetric.basicUnit;
    }

    public long toMillimeters(long value) {
        return convert(value, MILLIMETER);
    }

    public long toCentimeters(long value) {
        return convert(value, CENTIMETERS);
    }

    public long toDecimeters(long value){
        return convert(value, DECIMETERS);
    }

    public long toMeters(long value) {
        return convert(value, METERS);
    }

    public long toKilometers(long value) {
        return convert(value, KILOMETERS);
    }

}
