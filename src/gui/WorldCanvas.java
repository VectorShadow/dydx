package gui;

import contract.Gui;
import graph.Coordinate;
import gui.draw.DrawMap;
import gui.draw.Sight;
import level.Level;
import level.TerrainTemplate;
import mapgen.FloorDesigner;
import resources.glyph.Glyph;
import resources.glyph.GlyphBuilder;
import resources.glyph.ProtoGlyphBuilder;

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
        //find the size of the io.display area
        int rowCount = gui.countRows();
        int colCount = gui.countColumns();
        //calculate the correction from the screen coordinate to the level coordinate
        int rowCorrection = rowOffset - rowCount / 2;
        int colCorrection = colOffset - colCount / 2;
        //forward declarations
        int levelRow;
        int levelCol;
        Glyph g;
        DrawMap dm = new DrawMap(level);
        dm.radiateSight(Sight.BRIGHT_SIGHT, // todo - HACK - this should be the player's sight power
                level.getGraph(Level.LIGHT_GRAPH_INDEX).radiate(
                        new Coordinate(rowOffset, colOffset), //todo - this should track the camera, will update when we set it at start
                        11 //todo - HACK - this should be the player's sight radius
                ));
        for (int i = 0; i < rowCount; ++i) {
            for (int j = 0; j < colCount; ++j) {
                levelRow = i + rowCorrection;
                levelCol = j + colCorrection;
                if (levelRow <  0 || levelRow >= level.getRows() || levelCol < 0 || levelCol >= level.getCols()) {
                    g = Glyph.EMPTY_GLYPH;
                }
                else {
                    g = dm.drawFrom(levelRow, levelCol);
                    //below - legacy code, display all
//                    TerrainTemplate tp = level.propertiesAt(levelRow, levelCol);
//                    g = tp.render();
//                    //todo - properly render actors: MEGAHACK - force glyph if actor is here
//                    if (level.getActorAt(levelRow, levelCol) != null) {
//                        g = GlyphBuilder.build(ProtoGlyphBuilder.setDefaults('A', Color.BLACK, Color.YELLOW).build());
//                    }
                }
                gui.print(i, j, g);
            }
        }
    }
}
