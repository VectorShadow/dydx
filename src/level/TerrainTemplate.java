package level;

import attribute.Attribute;
import graph.Coordinate;
import gui.draw.Drawable;
import resources.DualityMode;
import resources.continuum.Pair;
import resources.glyph.Glyph;
import resources.glyph.GlyphBuilder;
import resources.glyph.ProtoGlyph;

import java.awt.*;
import java.util.ArrayList;

/**
 * Define the properties for a specific terrain type.
 * Includes a list of flags to specify custom properties, and a protoglyph to specify the default appearance.
 */
public class TerrainTemplate implements Drawable {
    Attribute[] attributes;
    final ProtoGlyph protoGlyph;

    TerrainTemplate(Attribute[] a, ProtoGlyph p) {
        attributes = a;
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

    @Override
    public ProtoGlyph getProtoGlyph() {
        return protoGlyph;
    }

    @Override
    public ArrayList<Pair<Color>> getAllTemporaryColors() {
        return new ArrayList<>(); //not for terrain
    }

    @Override
    public boolean isUltraFluorescent() {
        return false; //todo - maybe necessary
    }
}
