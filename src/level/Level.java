package level;

import actor.Actor;
import actor.ActorExecutionQueue;
import engine.time.RealTime;
import engine.time.Time;
import engine.time.TurnTime;
import mapgen.TestGenerator;

public class Level {

    static TerrainLookupTable terrainLookupTable;

    Time time;
    ActorExecutionQueue actors;
    int terrainTheme;
    int rows;
    int cols;
    byte[][] terrainMap;
    Actor[][] actorMap; //redundant access to actors by coordinates

    public Level(boolean realtime, int rows, int cols, int theme){
        time = realtime ? new RealTime() : new TurnTime();
        actors = new ActorExecutionQueue();
        terrainTheme = theme;
        this.rows = rows;
        this.cols = cols;
        //terrainMap = new byte[rows][cols];
        /* todo - the above, and a proper method for calling map generation */
        terrainMap = new TestGenerator().generateTerrain(rows, cols);
        actorMap = new Actor[rows][cols];
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

    public static void setTerrainLookupTable(TerrainLookupTable tll) {
        terrainLookupTable = tll;
    }
    public TerrainProperties propertiesAt(int row, int col) {
        return terrainLookupTable.lookup(terrainTheme, terrainMap[row][col]);
    }

}
