package level;

import attribute.Attribute;
import resources.glyph.ProtoGlyph;

public class TerrainPropertiesBuilder {
    private TerrainProperties terrainProperties;

    private TerrainPropertiesBuilder(Attribute[] a, ProtoGlyph p) {
        terrainProperties = new TerrainProperties(a, p);
    }

    public static TerrainPropertiesBuilder initialize(Attribute[] a, ProtoGlyph p) {
        return new TerrainPropertiesBuilder(a, p);
    }
    public TerrainPropertiesBuilder setAttribute(int index, Attribute attribute) {
        terrainProperties.setAttribute(index, attribute);
        return this;
    }
    public TerrainProperties build() {
        return terrainProperties;
    }
}
