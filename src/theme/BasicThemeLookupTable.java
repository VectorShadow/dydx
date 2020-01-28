package theme;

import attribute.Attribute;
import attribute.AttributeFactory;
import gui.draw.Light;
import level.TerrainTemplate;
import level.TerrainTemplateBuilder;
import mapgen.MapGenerator;
import mapgen.TestGenerator;
import resources.chroma.Chroma;
import resources.continuum.Pair;
import resources.glyph.ProtoGlyphBuilder;

/**
 * Mock terrain lookup table for testing purposes.
 * Serves as an example for how to create implementation-specific lookup tables.
 */
public class BasicThemeLookupTable implements ThemeLookupTable {
    /**
     * Aspect Attribute indices.
     */

    public static final int ASPECT_ATTRIBUTE_COUNT = 0;

    /**
     * Actor Attribute indices (start from ASPECT_ATTRIBUTE_COUNT).
     */

    /**
     * Item Attribute indices (start from ASPECT_ATTRIBUTE_COUNT).
     */

    /**
     * Terrain Attribute indices (start from ASPECT_ATTRIBUTE_COUNT).
     */
    public static final int PERMIT_MOVE = ASPECT_ATTRIBUTE_COUNT;
    public static final int PERMIT_LIGHT = PERMIT_MOVE + 1;

    /**
     * Default Aspect Attributes.
     */
    public static final Attribute[] DEFAULT_ASPECT_ATTRIBUTES = new Attribute[] {
    };

    /**
     * Default Actor Attributes.
     */
    public static final Attribute[] DEFAULT_ACTOR_ATTRIBUTES = new Attribute[] {
    };

    /**
     * Default Item Attributes.
     */
    public static final Attribute[] DEFAULT_ITEM_ATTRIBUTES = new Attribute[] {
    };

    /**
     * Default Terrain Attributes.
     */
    public static final Attribute[] DEFAULT_TERRAIN_ATTRIBUTES = new Attribute[] {
            AttributeFactory.manufacture(false),
            AttributeFactory.manufacture(false),
    };

    /**
     * Terrain Properties tables.
     */
    private final TerrainTemplate[] DEFAULT_THEME = {
            // 0 - Void
            TerrainTemplateBuilder
                    .initialize(
                            defaultTerrainAttributes(),
                            ProtoGlyphBuilder.setDefaults(Chroma.BLACK, Chroma.WHITE,' ').build()
                    )
                    .build(),
            // 1 - Empty floor
            TerrainTemplateBuilder
                    .initialize(defaultTerrainAttributes(),
                            ProtoGlyphBuilder.setDefaults(Chroma.BLACK, Chroma.WHITE,'.').build()
                    )
                    .setAttribute(PERMIT_MOVE, AttributeFactory.manufacture(true))
                    .setAttribute(PERMIT_LIGHT, AttributeFactory.manufacture(true))
                    .build(),
            // 2 - Basic Wall
            TerrainTemplateBuilder
                    .initialize(defaultTerrainAttributes(),
                            ProtoGlyphBuilder.setDefaults(Chroma.BLACK, Chroma.WHITE,'#').build()
                    )
                    .build(),
    };

    /**
     * Default constructor does nothing.
     */
    public BasicThemeLookupTable() {

    }

    @Override
    public Attribute[] defaultAspectAttributes() {
        return DEFAULT_ASPECT_ATTRIBUTES;
    }

    @Override
    public Attribute[] defaultActorAttributes() {
        return ThemeLookupTable.prependAspectAttributes(this, DEFAULT_ACTOR_ATTRIBUTES);
    }

    @Override
    public Attribute[] defaultItemAttributes() {
        return ThemeLookupTable.prependAspectAttributes(this, DEFAULT_ITEM_ATTRIBUTES);
    }

    @Override
    public Attribute[] defaultTerrainAttributes() {
        return ThemeLookupTable.prependAspectAttributes(this, DEFAULT_TERRAIN_ATTRIBUTES);
    }

    /**
     * Called by Levels to access the properties at a specific coordinate.
     * @param theme the terrainTheme of the calling Level
     * @param terrainCode the terrainCode at the desired coordinate
     * @return the desired TerrainProperties.
     */
    @Override
    public TerrainTemplate lookupTerrain(int theme, byte terrainCode) {
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

    @Override
    public MapGenerator getMapGenerator(int theme) {
        return new TestGenerator();
    }

    @Override
    public int allowMovementIndex() {
        return PERMIT_MOVE;
    }

    @Override
    public int allowLightIndex() {
        return PERMIT_LIGHT;
    }
}
