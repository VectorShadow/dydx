package attribute;

public class ConstantFlatAttribute extends AbstractAttribute {
    private final int value;

    public ConstantFlatAttribute(int v) {
        value = v;
    }

    @Override
    public boolean isSet() {
        return value != DEFAULT_FLAT;
    }
    @Override
    public double getAverage(){
        return getFlat();
    }
    @Override
    public int getFlat() {
        return value;
    }
}
