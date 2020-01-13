package gui.draw;

import actor.Actor;
import graph.DirectedCoordinate;
import graph.Direction;
import level.Level;
import level.TerrainProperties;
import resources.glyph.Glyph;
import resources.glyph.ascii.SimpleGlyph;

import java.util.ArrayList;

public class DrawMap {
    private final DrawTile[][] tiles;
    private final Level level;

    public DrawMap(Level l) {
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
    private int getMaxDirectionalPriority(Drawable d, DrawTile t) {
        int maxPriority = 0;
        Light l;
        short s;
        int b;
        for (Direction dir : Direction.values()) {
            l = t.lightFrom(dir);
            s = t.sightFrom(dir);
            b = l.getBrightness();
            if (b < Light.LIGHT_BRIGHT && Sight.hasProperty(s, Sight.AMPLIFY_LIGHT)) b += 1;
            if (Sight.hasProperty(s, Sight.BRIGHT_SIGHT)) return 7; //we are done - no priority can exceed this
            //todo - if (maxPriority < 7 && Sight.hasProperty(s, Sight.ULTRA_VISION) [&& d.hasAttribute(ULTRA_ASPECT)]) maxPriority = 6;
            if (maxPriority < 6 && b == Light.LIGHT_DIM && Sight.hasProperty(s, Sight.DIM_SIGHT)) maxPriority = 5;
            //todo - heat! if (maxPriority < 5 && Sight.hasProperty(s, Sight.INFRA_VISION)) maxPriority = 4;
            if (maxPriority < 4 && b == Light.LIGHT_DARK && Sight.hasProperty(s, Sight.DARK_SIGHT)) maxPriority = 3;
            if (maxPriority < 3 && b > Light.LIGHT_BLACK && s > Sight.NO_SIGHT) maxPriority = 2;
            //todo - sound! if (maxPriority < 2 && d.getCurrentSound() != null) maxPriority = 1;
        }
        //todo - sound! if (maxPriority == 1 && !Player.canHear(d.getCurrentSound())) maxPriority = 0;
        return maxPriority;
    }
    private int actorPriority(int row, int col) {
        return getMaxDirectionalPriority(level.getActorAt(row, col), tiles[row][col]);
    }
    private int terrainPriority(int row, int col) {
        return getMaxDirectionalPriority(level.propertiesAt(row, col), tiles[row][col]);
    }
    public Glyph drawFrom(int row, int col) {
        DrawTile dt = tiles[row][col];
        Drawable d = level.getActorAt(row, col);
        Glyph g;
        int priority;
        if (d != null) {
            priority = actorPriority(row, col);
            if (priority > 0) {
                g = Aspect.get(priority).drawGlyph(d);
                if (g != SimpleGlyph.EMPTY_GLYPH) return g; //this can occur of priority was 1 and no sound reached the player
            }
        }
        d = level.propertiesAt(row, col);
        priority = terrainPriority(row, col);
        g = Aspect.get(priority).drawGlyph(d);
        //Don't draw from a memory aspect if the player doesn't remember this tile!
        if (priority == 0 && !dt.remembered) g = SimpleGlyph.EMPTY_GLYPH;
        return g;
    }
}
