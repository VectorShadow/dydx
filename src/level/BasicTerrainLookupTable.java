package level;

import flag.Flag;
import flag.FlagFactory;
import resources.glyph.ProtoGlyphBuilder;

import java.awt.*;

/**
 * Mock terrain lookup table for testing purposes.
 * Serves as an example for how to create implementation-specific lookup tables.
 */
public class BasicTerrainLookupTable implements TerrainLookupTable {
    /**
     * Flag definitions.
     */
    public static final int PERMIT_MOVEMENT = 0;
    public static final int PERMIT_LIGHT = 1;
    /**
     * Flag table.
     */
    public static final Flag[] DEFINED_FLAGS = {
            // 0 - Permit Movement
            FlagFactory.setName("Permit Movement").manufacture(),
            //1 - Permit Light
            FlagFactory.setName("Permit Light").manufacture(),
    };

    /**
     * Access flags by name.
     */
    public static Flag flag(int flagIndex) {
        return DEFINED_FLAGS[flagIndex];
    }

    /**
     * Properties tables.
     */
    private final TerrainProperties[] DEFAULT_THEME = {
            // 0 - Void
            TerrainPropertiesBuilder
                    .setProtoGlyph(ProtoGlyphBuilder.setDefaults(' ', Color.BLACK, Color.WHITE).build())
                    .build(),
            // 1 - Empty floor
            TerrainPropertiesBuilder
                    .setProtoGlyph(ProtoGlyphBuilder.setDefaults('.', Color.BLACK, Color.WHITE).build())
                    .addFlag(DEFINED_FLAGS[PERMIT_MOVEMENT])
                    .addFlag(DEFINED_FLAGS[PERMIT_LIGHT])
                    .build(),
            // 2 - Basic Wall
            TerrainPropertiesBuilder
                    .setProtoGlyph(ProtoGlyphBuilder.setDefaults('#', Color.BLACK, Color.WHITE).build())
                    .build(),
    };

    /**
     * Default constructor does nothing.
     */
    public BasicTerrainLookupTable() {

    }

    /**
     * Called by Levels to access the properties at a specific coordinate.
     * @param theme the terrainTheme of the calling Level
     * @param terrainCode the terrainCode at the desired coordinate
     * @return the desired TerrainProperties.
     */
    @Override
    public TerrainProperties lookup(int theme, byte terrainCode) {
        int index = terrainCode;
        switch (theme) {
            case 0:
                if (index >= DEFAULT_THEME.length)
                    throw new IllegalArgumentException("No properties specified for index " +
                            index + " in theme " + theme + ".");
                return DEFAULT_THEME[index];
            default:
                throw new IllegalArgumentException("Theme " + theme + " not implemented.");
        }
    }
}
