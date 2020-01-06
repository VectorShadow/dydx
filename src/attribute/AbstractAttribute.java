package attribute;

/**
 * Define the default behavior of an Attribute.
 */
public abstract class AbstractAttribute implements Attribute {
    @Override
    public boolean isSet() {
        return false;
    }

    @Override
    public double getAverage() {
        return getFlat();
    }
    @Override
    public int getFlat() {
        return DEFAULT_FLAT;
    }

    @Override
    public double getScaling() {
        return DEFAULT_SCALE;
    }
}
