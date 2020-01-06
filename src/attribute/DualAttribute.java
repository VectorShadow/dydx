package attribute;

public class DualAttribute extends AbstractAttribute {
    private final int flat;
    private final double scaling;

    public DualAttribute(int f, double s) {
        flat = f;
        scaling = s;
    }

    @Override
    public boolean isSet() {
        return flat != DEFAULT_FLAT && scaling != DEFAULT_SCALE;
    }

    /**
     * Unnecessary, but explicitly makes clear the intention of this method here.
     */
    @Override
    public double getAverage() {
        return super.getAverage();
    }
    @Override
    public int getFlat() {
        return flat;
    }
    @Override
    public double getScaling() {
        return scaling;
    }
}
