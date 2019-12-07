package level;

import effect.Effect;
import resources.glyph.ProtoGlyph;

import java.util.ArrayList;

public class TerrainProperties {

    /**
     * Whether this terrain permits movement of type n.
     */
    ArrayList<Boolean> permissions = new ArrayList<>();
    /**
     * Effects applied on moving into this terrain with movement of type n.
     */
    ArrayList<ArrayList<Effect>> movementEffects = new ArrayList<>();
    /**
     * Effects applied at intervals to actors and items occupying this terrain.
     */
    ArrayList<Effect> stationaryEffects = new ArrayList<>();

    final ProtoGlyph protoGlyph;

    //todo - to avoid build order issues, additional permissions should be defined in the same place as,
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
    //todo - if no new permissions need be defined, than this precaution is unnecessary.
    TerrainProperties(ProtoGlyph protoGlyph) {
        //initialize permissions and effects with a number of elements corresponding to defined permissions
        for (int i = 0; i < TerrainPermission.countDefinedPermissions(); ++i) {
            permissions.add(false);
            movementEffects.add(new ArrayList<>());
        }
        this.protoGlyph = protoGlyph;
    }
}
