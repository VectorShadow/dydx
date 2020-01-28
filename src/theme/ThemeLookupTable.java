package theme;

import attribute.Attribute;
import gui.draw.Light;
import level.TerrainTemplate;
import mapgen.MapGenerator;

public interface ThemeLookupTable {
    Attribute[] defaultAspectAttributes();
    Attribute[] defaultActorAttributes();
    Attribute[] defaultItemAttributes();
    Attribute[] defaultTerrainAttributes();
    //todo - lookup actor template
    //todo - lookup item template
    TerrainTemplate lookupTerrain(int theme, byte terrainCode);
    Light lookupLight(int theme);
    MapGenerator getMapGenerator(int theme);
    int allowMovementIndex();
    int allowLightIndex();

    static Attribute[] prependAspectAttributes(ThemeLookupTable tlt, Attribute[] a) {
        Attribute[] aspects = tlt.defaultAspectAttributes();
        Attribute[] prepended = new Attribute[aspects.length + a.length];
        int idx = 0;
        while (idx < aspects.length) {
            prepended[idx] = aspects[idx];
            ++idx;
        }
        while (idx < prepended.length) {
            prepended[idx] = a[idx - aspects.length];
            ++idx;
        }
        return prepended;
    }
}
