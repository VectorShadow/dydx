package gui.draw;

public class Sight {
    public static final short NO_SIGHT = 0b0000_0000;
    public static final short DARK_SIGHT = 0b0000_0001;
    public static final short DIM_SIGHT = 0b0000_0010;
    public static final short BRIGHT_SIGHT = 0b0000_0100;
    public static final short AMPLIFY_LIGHT = 0b0000_1000;
    public static final short INFRA_VISION = 0b0001_0000;
    public static final short ULTRA_VISION = 0b0010_0000;

    public static boolean hasProperty(short sight, short property) {
        return (sight & property) == property;
    }
}
