package level;

/**
 * Allow creation of implementation specific flags to describe terrain properties.
 */
public class TerrainFlag {
    final String name;
    final double power;

    public TerrainFlag(String name) {
        this(name, 0.0);
    }
    public TerrainFlag(String name, double power) {
        this.name = name;
        this.power = power;
    }
}
