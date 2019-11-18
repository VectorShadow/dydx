package level.terrain;

/**
 * Utility class for TerrainProperties. Store up to 31 boolean flags to describe terrain properties.
 * For example - allow movement, allow light, reflect light, etc.
 * Ideally, this class should be extended and the super class should define each supported flag
 * as a set of constants with specific methods that call these methods, for example:
 *
 * static final int ALLOW_MOVEMENT = 1;
 *
 * public void setAllowMovement() {
 *     set(ALLOW_MOVEMENT);
 * }
 *
 * public boolean doesAllowMovement() {
 *     return isSet(ALLOW_MOVEMENT);
 * }
 *
 * etc.
 */
public abstract class TerrainFlags {
    protected static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int MAX_POSITION = 30;

    private int flag = ZERO;

    protected void set(int positionFromRightmost){
        if (positionFromRightmost < ZERO || positionFromRightmost > MAX_POSITION)
            throw new IllegalArgumentException("Invalid position: " + positionFromRightmost + ".");
        int mask = ONE << positionFromRightmost;
        flag |= mask;
    }
    protected boolean isSet(int positionFromRightmost) {
        if (positionFromRightmost < ZERO || positionFromRightmost > MAX_POSITION)
            throw new IllegalArgumentException("Invalid position: " + positionFromRightmost + ".");
        int mask = ONE << positionFromRightmost;
        return (flag & mask) == mask;
    }
}
