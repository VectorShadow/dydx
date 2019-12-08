package level;

import resources.glyph.ProtoGlyph;

public class TerrainPropertiesBuilder {
    private TerrainProperties terrainProperties;

    private TerrainPropertiesBuilder(ProtoGlyph p) {
        terrainProperties = new TerrainProperties(p);
    }

    public static TerrainPropertiesBuilder setProtoGlyph(ProtoGlyph p) {
        return new TerrainPropertiesBuilder(p);
    }
    public TerrainPropertiesBuilder addFlag(TerrainFlag tf) {
        terrainProperties.addFlag(tf);
        return this;
    }
    public TerrainProperties build() {
        return terrainProperties;
    }
}
