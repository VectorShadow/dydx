package level;

import attribute.Attribute;
import resources.glyph.ProtoGlyph;

public class TerrainPropertiesBuilder {
    private TerrainProperties terrainProperties;

    private TerrainPropertiesBuilder(ProtoGlyph p) {
        terrainProperties = new TerrainProperties(p);
    }

    public static TerrainPropertiesBuilder setProtoGlyph(ProtoGlyph p) {
        return new TerrainPropertiesBuilder(p);
    }
    public TerrainPropertiesBuilder setAttribute(int index, Attribute attribute) {
        terrainProperties.setAttribute(index, attribute);
        return this;
    }
    public TerrainProperties build() {
        return terrainProperties;
    }
}
