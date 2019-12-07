package level;

import effect.Effect;
import effect.EffectType;

/**
 * Define a builder pattern for creation of new TerrainProperties.
 */
public class TerrainPropertiesBuilder {
    private TerrainProperties terrainProperties;

    private TerrainPropertiesBuilder() {
        terrainProperties = new TerrainProperties();
    }

    public static TerrainPropertiesBuilder generateTerrainProperties() {
        return new TerrainPropertiesBuilder();
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
