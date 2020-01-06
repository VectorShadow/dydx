package attribute;

public class ScalingAttribute extends AbstractAttribute {
    private final double value;

    public ScalingAttribute(double v) {
        value = v;
    }

    @Override
    public boolean isSet(){
        return value != 1.0;
    }
    @Override
    public double getScaling() {
        return value;
    }
}
