package level;

import attribute.Attribute;
import resources.DualityMode;
import resources.glyph.Glyph;
import resources.glyph.GlyphBuilder;
import resources.glyph.ProtoGlyph;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Define the properties for a specific terrain type.
 * Includes a list of flags to specify custom properties, and a protoglyph to specify the default appearance.
 */
public class TerrainProperties {
    Attribute[] attributes;
    final ProtoGlyph protoGlyph;

    TerrainProperties(ProtoGlyph p) {
        attributes = Level.terrainLookupTable.defaultAttributes();
        protoGlyph = p;
    }

    void setAttribute(int index, Attribute attribute) {
        attributes[index] = attribute;
    }
    public Attribute getAttribute(int index) {
        return attributes[index];
    }

    public boolean hasProperty(int index) {
        return attributes[index].isSet();
    }

    public Glyph render() {
        //todo - HACK for now
        //todo - render method, which will interpret flags and protoglyph and apply light and sight and memory information to generate a true glyph
        return GlyphBuilder.build(protoGlyph, DualityMode.TILE);
    }
}
