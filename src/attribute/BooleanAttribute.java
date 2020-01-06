package attribute;

public class BooleanAttribute extends AbstractAttribute {
    private final boolean set;

    public BooleanAttribute(boolean b) {
        set = b;
    }
    @Override
    public boolean isSet(){
        return set;
    }
}
