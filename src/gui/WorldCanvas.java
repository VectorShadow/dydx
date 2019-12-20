package gui;

import contract.Gui;
import level.Level;
import level.TerrainProperties;
import resources.DualityMode;
import resources.continuum.Pair;
import resources.glyph.Glyph;
import resources.glyph.GlyphBuilder;
import resources.glyph.ProtoGlyphBuilder;
import resources.render.OutputMode;

import java.awt.*;

/**
 * A class designed to translate the state of the world as represented by a Level
 * into the data required to draw to the GUI.
 */
public class WorldCanvas {
    public static void paint(Level level, Gui gui) {
        //todo - hack: use the center of the level as the point of focus. instead we should use the Camera.
        int rowOffset = level.getRows()/2;
        int colOffset = level.getCols()/2;
        //find the size of the display area
        int rowCount = gui.countRows();
        int colCount = gui.countColumns();
        //calculate the correction from the screen coordinate to the level coordinate
        int rowCorrection = rowOffset - rowCount / 2;
        int colCorrection = colOffset - colCount / 2;
        //forward declarations
        int levelRow;
        int levelCol;
        Glyph g;
        for (int i = 0; i < rowCount; ++i) {
            for (int j = 0; j < colCount; ++j) {
                levelRow = i + rowCorrection;
                levelCol = j + colCorrection;
                if (levelRow <  0 || levelRow >= level.getRows() || levelCol < 0 || levelCol >= level.getCols()) {
                    g = Glyph.EMPTY_GLYPH;
                }
                else {
                    TerrainProperties tp = level.propertiesAt(levelRow, levelCol);
                    g = tp.render();
                    //todo - properly render actors: MEGAHACK - force glyph if actor is here
                    if (level.getActorAt(levelRow, levelCol) != null) {
                        g = GlyphBuilder.build(ProtoGlyphBuilder.setDefaults('A', Color.BLACK, Color.YELLOW).build());
                    }
                }
                gui.print(i, j, g);
            }
        }
    }
}
