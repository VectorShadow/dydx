package level;

import java.util.ArrayList;

public class TerrainPermission {
    public static ArrayList<TerrainPermission> enumeration = new ArrayList<>();

    private static boolean initialized = false;

    public static void definePermission(String name){
        if (!initialized) {
            initialized = true;
            definePermission("Light/Sight");
            definePermission("Walk/Place_Item");
        }
        for (TerrainPermission tp : enumeration) {
            if (tp.name.equals(name)) throw new IllegalArgumentException("Permission " + name + " already defined.");
        }
        final TerrainPermission terrainPermission = new TerrainPermission(name);
        enumeration.add(terrainPermission);
    }
    public static TerrainPermission named(String t){
        for (final TerrainPermission terrainPermission : enumeration) {
            if (terrainPermission.name.equals(t)) return terrainPermission;
        }
        throw new IllegalArgumentException("Permission " + t + " has not been defined. " +
                "Call TerrainPermission.definePermission(" + t + ") to define this permission.");
    }
    public static int countDefinedPermissions() {
        return enumeration.size();
    }

    private final String name;

    private TerrainPermission(String n) {
        name = n;
    }
    /**
     * Enable switching.
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TerrainPermission && this == o;
    }
}
