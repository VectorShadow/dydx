package level;

import attribute.Attribute;
import resources.glyph.ProtoGlyph;

public class TerrainTemplateBuilder {
    private TerrainTemplate terrainTemplate;

    private TerrainTemplateBuilder(Attribute[] a, ProtoGlyph p) {
        terrainTemplate = new TerrainTemplate(a, p);
    }

    public static TerrainTemplateBuilder initialize(Attribute[] a, ProtoGlyph p) {
        return new TerrainTemplateBuilder(a, p);
    }
    public TerrainTemplateBuilder setAttribute(int index, Attribute attribute) {
        terrainTemplate.setAttribute(index, attribute);
        return this;
    }
    public TerrainTemplate build() {
        return terrainTemplate;
    }
}
