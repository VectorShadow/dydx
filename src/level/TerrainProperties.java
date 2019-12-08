package level;

import resources.glyph.ProtoGlyph;

import java.util.ArrayList;

/**
 * Define the properties for a specific terrain type.
 * Includes a list of flags to specify custom properties, and a protoglyph to specify the default appearance.
 */
public class TerrainProperties {
    ArrayList<TerrainFlag> flags;
    final ProtoGlyph protoGlyph;

    TerrainProperties(ProtoGlyph p) {
        flags = new ArrayList<>();
        protoGlyph = p;
    }

    void addFlag(TerrainFlag tf) {
        flags.add(tf);
    }

    public ArrayList<TerrainFlag> getFlags() {
        return flags;
    }
    //todo - render method, which will interpret flags and protoglyph and apply light and sight and memory information to generate a true glyph
}
