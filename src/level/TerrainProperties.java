package level;

import effect.Effect;

import java.util.ArrayList;

public class TerrainProperties {

    /**
     * Whether this terrain permits movement of type n.
     */
    private ArrayList<Boolean> permissions = new ArrayList<>();
    /**
     * Effects applied on moving into this terrain with movement of type n.
     */
    private ArrayList<ArrayList<Effect>> movementEffects = new ArrayList<>();
    /**
     * Effects applied at intervals to actors and items occupying this terrain.
     */
    private ArrayList<Effect> stationaryEffects = new ArrayList<>();

    //todo - Builder pattern in package to call this, then add permissions accordingly
    //todo - to avoid build order issues, permissions should be defined in the same place as,
    //todo - but prior to, properties, so:
    /*
        TerrainPermission.definePermission(foo);
        TerrainPermission.definePermission(foo2);
        .
        .
        .
        TerrainProperties tp0 = TerrainPropertiesBuilder.newTerrainProperties.(...).build();
        .
        .
        .
     */
    TerrainProperties() {
        //initialize permissions and effects with a number of elements corresponding to defined permissions
        for (int i = 0; i < TerrainPermission.countDefinedPermissions(); ++i) {
            permissions.add(false);
            movementEffects.add(new ArrayList<>());
        }
    }
}
