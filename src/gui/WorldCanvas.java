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

    private static Level level = null;
    private static DrawMap drawMap = null;

    private static void setLevel(Level l) {
        if (l == level) drawMap.resetLightSight();
        else {
            level = l;
            drawMap = new DrawMap(level);
        }
    }

    public static void paint(Level level, Gui gui) {
        setLevel(level);
        int rowOffset = Camera.row();
        int colOffset = Camera.col();
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
        drawMap.radiateSight(Sight.BRIGHT_SIGHT, // todo - HACK - this should be the player's sight power
                level.getGraph(Level.LIGHT_GRAPH_INDEX).radiate(
                        new Coordinate(rowOffset, colOffset),
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
                    g = drawMap.drawFrom(levelRow, levelCol);
                }
                gui.print(i, j, g);
            }
        }
    }
}
