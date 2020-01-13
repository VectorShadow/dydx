package gui.draw;

import actor.Actor;
import graph.DirectedCoordinate;
import graph.Direction;
import level.Level;
import level.TerrainProperties;
import resources.glyph.Glyph;
import resources.glyph.ascii.SimpleGlyph;

import java.util.ArrayList;

public class DrawTileMap {
    private final DrawTile[][] tiles;
    private final Level level;

    public DrawTileMap(Level l) {
        level = l;
        tiles = new DrawTile[level.getRows()][level.getCols()];
        for (int i = 0; i < level.getRows(); ++i) {
            for (int j = 0; j < level.getCols(); ++j) {
                tiles[i][j] = new DrawTile(l.ambientLight());
            }
        }
    }
    public void radiateLight(Light l, ArrayList<DirectedCoordinate> dcList) {
        for (DirectedCoordinate dc : dcList) tiles[dc.ROW][dc.COL].lightFrom(dc.DIR, l);
    }
    public void radiateSight(short s, ArrayList<DirectedCoordinate> dcList) {
        for (DirectedCoordinate dc : dcList) tiles[dc.ROW][dc.COL].seeFrom(dc.DIR, s);
    }
    private int actorPriority(int row, int col) {
        DrawTile dt = tiles[row][col];
        int maxPriority = 0;
        for (Direction d : Direction.values()) {
            //todo - lots here
        }
        return maxPriority;
    }
    private int terrainPriority(int row, int col) {
        DrawTile dt = tiles[row][col];
        int maxPriority = 0;
        for (Direction d : Direction.values()) {
            //todo - lots here
        }
        return maxPriority;

    }
    public Glyph drawFrom(int row, int col) {
        DrawTile dt = tiles[row][col];
        Actor a = level.getActorAt(row, col);
        int priority;
        if (a != null) {
            priority = actorPriority(row, col);
            if (priority > 0) {
                Glyph g = Aspect.get(priority).drawGlyph(a);
                if (g != SimpleGlyph.EMPTY_GLYPH) return g;
            }
        }
        TerrainProperties tp = level.propertiesAt(row, col);
        priority = terrainPriority(row, col);
        if (priority == 0 && !dt.remembered) return SimpleGlyph.EMPTY_GLYPH;
        return Aspect.get(priority).drawGlyph(tp);
    }
}
