package level;

import effect.Effect;
import effect.EffectType;
import resources.glyph.ProtoGlyph;

/**
 * Define a builder pattern for creation of new TerrainProperties.
 */
public class TerrainPropertiesBuilder {
    private TerrainProperties terrainProperties;

    private TerrainPropertiesBuilder(ProtoGlyph protoGlyph) {
        terrainProperties = new TerrainProperties(protoGlyph);
    }

    public static TerrainPropertiesBuilder generateTerrainProperties(ProtoGlyph protoGlyph) {
        return new TerrainPropertiesBuilder(protoGlyph);
    }

    public TerrainPropertiesBuilder permit(String permissionName) {
        return permit(TerrainPermission.named(permissionName).indexOf());
    }
    private TerrainPropertiesBuilder permit(int index) {
        terrainProperties.permissions.set(index, true);
        return this;
    }
    public TerrainPropertiesBuilder addMovementEffect(String permissionName, String effectTypeName, double effectPower) {
        return addMovementEffect(TerrainPermission.named(permissionName).indexOf(), effectTypeName, effectPower);
    }
    private TerrainPropertiesBuilder addMovementEffect(int index, String effectTypeName, double effectPower) {
        terrainProperties.movementEffects.get(index).add(new Effect(EffectType.named(effectTypeName), effectPower));
        return this;
    }
    public TerrainPropertiesBuilder addStationaryEffect(String effectTypeName, double effectPower) {
        terrainProperties.stationaryEffects.add(new Effect(EffectType.named(effectTypeName), effectPower));
        return this;
    }

    public TerrainProperties build() {
        return terrainProperties;
    }
}
