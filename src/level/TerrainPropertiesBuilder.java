package level;

import flag.Flag;
import resources.glyph.ProtoGlyph;

public class TerrainPropertiesBuilder {
    private TerrainProperties terrainProperties;

    private TerrainPropertiesBuilder(ProtoGlyph p) {
        terrainProperties = new TerrainProperties(p);
    }

    public static TerrainPropertiesBuilder setProtoGlyph(ProtoGlyph p) {
        return new TerrainPropertiesBuilder(p);
    }
    public TerrainPropertiesBuilder addFlag(Flag f) {
        terrainProperties.addFlag(f);
        return this;
    }
    public TerrainProperties build() {
        return terrainProperties;
    }
}
