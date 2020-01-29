package gui.draw;

import graph.DirectedCoordinate;
import graph.Direction;
import level.Level;
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
        if (l == null) return;
        for (DirectedCoordinate dc : dcList) tiles[dc.ROW][dc.COL].lightFrom(dc.DIR, l);
    }
    public void radiateSight(short s, ArrayList<DirectedCoordinate> dcList) {
        if (s <= 0) return;
        for (DirectedCoordinate dc : dcList) tiles[dc.ROW][dc.COL].seeFrom(dc.DIR, s);
    }
    private DrawData getMaxDirectionalPriority(Drawable d, DrawTile t) {
        DrawData dd = new DrawData();
        Light l;
        short s;
        int b;
        for (Direction dir : Direction.values()) {
            if (dir == Direction.ERROR) continue;
            l = t.lightFrom(dir);
            if (l == null) continue;
            s = t.sightFrom(dir);
            b = l.getBrightness();
            if (b < Light.LIGHT_BRIGHT && Sight.hasProperty(s, Sight.AMPLIFY_LIGHT)) b += 1;
            if (b == Light.LIGHT_BRIGHT && Sight.hasProperty(s, Sight.BRIGHT_SIGHT)) {
                dd.setValues(Aspect.BRIGHT.ordinal(), b, l);
                return dd; //we are done - no priority can exceed this
            }
            //todo - if (maxPriority < BRIGHT && Sight.hasProperty(s, Sight.ULTRA_VISION) [&& d.hasAttribute(ULTRA_ASPECT)]) maxPriority = ULTRA;
            if (dd.getAspectRank() < Aspect.ULTRA.ordinal() && b == Light.LIGHT_DIM && Sight.hasProperty(s, Sight.DIM_SIGHT))
                dd.setValues(Aspect.DIM.ordinal(), b, l);
            //todo - heat! if (maxPriority < DIM && Sight.hasProperty(s, Sight.INFRA_VISION)) maxPriority = INFRA;
            if (dd.getAspectRank() < Aspect.INFRA.ordinal() && b == Light.LIGHT_DARK && Sight.hasProperty(s, Sight.DARK_SIGHT))
                dd.setValues(Aspect.DARK.ordinal(), b, l);
            if (dd.getAspectRank() < Aspect.DARK.ordinal() && b > Light.LIGHT_BLACK && s > Sight.NO_SIGHT){
                dd.setValues(Aspect.VAGUE.ordinal(), b, l);
            }
            //todo - sound! if (maxPriority < VAGUE && d.getCurrentSound() != null) maxPriority = SOUND;
        }
        //todo - memory! if (maxPriority == SOUND && !Player.canHear(d.getCurrentSound())) maxPriority = MEMORY;
        if (dd.getAspectRank() == Aspect.MEMORY.ordinal() && !t.remembered)
            dd.setValues(Aspect.NONE.ordinal(), 0, null);
        return dd;
    }
    private DrawData actorPriority(int row, int col) {
        return getMaxDirectionalPriority(level.getActorAt(row, col), tiles[row][col]);
    }
    private DrawData terrainPriority(int row, int col) {
        return getMaxDirectionalPriority(level.propertiesAt(row, col), tiles[row][col]);
    }
    public Glyph drawFrom(int row, int col) {
        DrawTile dt = tiles[row][col];
        Drawable d = level.getActorAt(row, col);
        Glyph g;
        DrawData dd;
        if (d != null) {
            dd = actorPriority(row, col);
            if (dd.getAspectRank() > Aspect.MEMORY.ordinal()) {
                return Aspect.get(dd.getAspectRank()).drawGlyph(d, dd);
            }
        }
        d = level.propertiesAt(row, col);
        dd = terrainPriority(row, col);
        g = Aspect.get(dd.getAspectRank()).drawGlyph(d, dd);
        return g;
    }
}
