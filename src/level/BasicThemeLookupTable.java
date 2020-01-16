package level;

import attribute.Attribute;
import attribute.AttributeFactory;
import gui.draw.Light;
import resources.glyph.ProtoGlyphBuilder;

import java.awt.*;

/**
 * Mock terrain lookup table for testing purposes.
 * Serves as an example for how to create implementation-specific lookup tables.
 */
public class BasicThemeLookupTable implements ThemeLookupTable {
    /**
     * Attribute indices.
     */
    public static final int PERMIT_MOVE = 0;
    public static final int PERMIT_LIGHT = 1;

    /**
     * Default Attributes.
     */
    public static final Attribute[] DEFAULT_ATTRIBUTES = new Attribute[] {
            AttributeFactory.manufacture(false),
            AttributeFactory.manufacture(false),
    };

    /**
     * Properties tables.
     */
    private final TerrainProperties[] DEFAULT_THEME = {
            // 0 - Void
            TerrainPropertiesBuilder
                    .initialize(
                            defaultAttributes(),
                            ProtoGlyphBuilder.setDefaults(' ', Color.BLACK, Color.WHITE).build())
                    .build(),
            // 1 - Empty floor
            TerrainPropertiesBuilder
                    .initialize(defaultAttributes(),
                            ProtoGlyphBuilder.setDefaults('.', Color.BLACK, Color.WHITE).build())
                    .setAttribute(PERMIT_MOVE, AttributeFactory.manufacture(true))
                    .setAttribute(PERMIT_LIGHT, AttributeFactory.manufacture(true))
                    .build(),
            // 2 - Basic Wall
            TerrainPropertiesBuilder
                    .initialize(defaultAttributes(),
                            ProtoGlyphBuilder.setDefaults('#', Color.BLACK, Color.WHITE).build())
                    .build(),
    };

    /**
     * Default constructor does nothing.
     */
    public BasicThemeLookupTable() {

    }

    @Override
    public Attribute[] defaultAttributes() {
        return DEFAULT_ATTRIBUTES;
    }

    /**
     * Called by Levels to access the properties at a specific coordinate.
     * @param theme the terrainTheme of the calling Level
     * @param terrainCode the terrainCode at the desired coordinate
     * @return the desired TerrainProperties.
     */
    @Override
    public TerrainProperties lookupTerrain(int theme, byte terrainCode) {
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

    @Override
    public Light lookupLight(int theme) {
        return null; //fine for now - this method must work in an implementation table
    }
}
