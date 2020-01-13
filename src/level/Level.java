package level;

import actor.Actor;
import actor.ActorExecutionQueue;
import engine.time.RealTime;
import engine.time.Time;
import engine.time.TurnTime;
import gui.draw.Light;
import mapgen.TestGenerator;

import java.util.Random;

public class Level {

    /**
     * The Graph algorithm which we rely on for raycasting and AI runs in linear time relative to the size of the level,
     * so we cap level size at a number which prevents graph construction from becoming prohibitive for the server.
     * It remains possible to have levels that are tall and narrow, or wide and short, so long as the
     * total number of tiles does not exceed a certain maximum.
     * Note that open levels are more prone to this than closed levels, since the number of edges generated
     * for common flags such as light and movement are much higher.
     */
    final int MAX_DIM = 512;
    final int MAX_SIZE = MAX_DIM * MAX_DIM;


    static ThemeLookupTable themeLookupTable;

    Time time;
    ActorExecutionQueue actors;
    int theme;
    int rows;
    int cols;
    byte[][] terrainMap;
    Actor[][] actorMap; //redundant access to actors by coordinates

    public Level(boolean realtime, int r, int c, int t){
        time = realtime ? new RealTime() : new TurnTime();
        actors = new ActorExecutionQueue();
        theme = t;
        rows = r * c > MAX_SIZE ? MAX_DIM : r;
        cols = r * c > MAX_SIZE ? MAX_DIM : c;
        //terrainMap = new byte[rows][cols];
        /* todo - the above, and a proper method for calling map generation */
        terrainMap = new TestGenerator().generateTerrain(rows, cols);
        actorMap = new Actor[rows][cols];
        //todo - generate actors properly: MEGAHACK - generate actors randomly
        for (int i = 0; i < 5;) {
            int ar = new Random().nextInt(rows);
            int ac = new Random().nextInt(cols);
            if (propertiesAt(ar, ac).hasProperty(BasicThemeLookupTable.PERMIT_MOVE)) {
                actorMap[ar][ac] = new Actor(time);
                ++i;
            }
        }
    }

    public int getRows(){
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Time getTime() {
        return time;
    }

    public ActorExecutionQueue getActors() {
        return actors;
    }

    public Actor getActor(int actorID) {
        return actors.getActor(actorID);
    }

    public byte getTerrainAt(int row, int col) {
        return terrainMap[row][col];
    }
    public void setTerrainAt(int row, int col, byte b) {
        terrainMap[row][col] = b;
        //todo - update graphs as necessary!
    }
    public Actor getActorAt(int row, int col) {
        return actorMap[row][col];
    }
    public void setActorAt(int row, int col, Actor a) {
        actorMap[row][col] = a;
    }

    public static void setThemeLookupTable(ThemeLookupTable tll) {
        themeLookupTable = tll;
    }
    public TerrainProperties propertiesAt(int row, int col) {
        return themeLookupTable.lookupTerrain(theme, terrainMap[row][col]);
    }
    public Light ambientLight() {
        return themeLookupTable.lookupLight(theme);
    }

}
