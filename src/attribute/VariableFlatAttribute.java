package attribute;

import java.util.Random;

public class VariableFlatAttribute extends AbstractAttribute{
    private static final Random RANDOM = new Random();

    private final int limit;
    private final int range;

    public VariableFlatAttribute(int l, int r) {
        limit = l;
        range = r;
    }
    @Override
    public boolean isSet() {
        return limit >= range;
    }
    @Override
    public double getAverage() {
        return isSet() ? limit - ((double)range / 2.0) : DEFAULT_FLAT;
    }
    @Override
    public int getFlat() {
        return limit - RANDOM.nextInt(range);
    }
}
