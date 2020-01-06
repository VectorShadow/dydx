package attribute;

public class AttributeFactory {
    public static Attribute manufacture(boolean set) {
        return new BooleanAttribute(set);
    }
    public static Attribute manufacture(int value) {
        return new ConstantFlatAttribute(value);
    }
    public static Attribute manufacture(int limit, int range) {
        return new VariableFlatAttribute(limit, range);
    }
    public static Attribute manufacture(double value) {
        return new ScalingAttribute(value);
    }
    public static Attribute manufacture(int flat, double scale) {
        return new DualAttribute(flat, scale);
    }
}
