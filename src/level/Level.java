package level;

import actor.ActionItem;
import actor.Actor;
import engine.ActorExecutionQueue;
import engine.time.RealTime;
import engine.time.Time;
import engine.time.TurnTime;
import graph.Graph;
import gui.draw.Light;
import mapgen.FloorDesigner;
import theme.ThemeLookupTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Level implements Serializable {

    public static final int MOVEMENT_GRAPH_INDEX = 0;
    public static final int LIGHT_GRAPH_INDEX = 1;

    public static final Random LEVEL_RNG = new Random();
    //todo - SOUND, other graphs such as AI stuff may be implementation specific?

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
    ArrayList<Graph> graphs;

    public Level(boolean realtime, int r, int c, int t){
        //TODO - this entire constructor is a hack. implement properly with MapGenerators
        time = realtime ? new RealTime() : new TurnTime();
        actors = new ActorExecutionQueue(time);
        theme = t;
        rows = r * c > MAX_SIZE ? MAX_DIM : r;
        cols = r * c > MAX_SIZE ? MAX_DIM : c;
        terrainMap = themeLookupTable.getMapGenerator(theme).generateTerrain(rows, cols);
        actorMap = new Actor[rows][cols];
        graphs = new ArrayList<>();
        FloorDesigner.graph(this);
        //todo - generate actors properly
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

    public Actor getActor(long actorID) {
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
        if (a != null) a.setMapCoordinate(row, col);
    }

    public void addGraph(Graph g) {
        graphs.add(g);
    }
    public Graph getGraph(int graphIndex) {
        return graphs.get(graphIndex);
    }

    public static void setThemeLookupTable(ThemeLookupTable tll) {
        themeLookupTable = tll;
    }
    public TerrainTemplate propertiesAt(int row, int col) {
        return themeLookupTable.lookupTerrain(theme, terrainMap[row][col]);
    }
    public Light ambientLight() {
        return themeLookupTable.lookupLight(theme);
    }
    public void placeActor(Actor a) {
        int r = a.getMapCoordinate().ROW;
        int c = a.getMapCoordinate().COL;
        while (r < 0 || c < 0 || !propertiesAt(r, c).hasProperty(themeLookupTable.allowMovementIndex()) && getActorAt(r, c) == null) {
            r = LEVEL_RNG.nextInt(rows);
            c = LEVEL_RNG.nextInt(cols);
        }
        a.synchronizeTime(time);
        actors.addActor(a);
        setActorAt(r, c, a);
    }
    public ActionItem execute() {
        Actor a = actors.poll();
        ActionItem ai = a.executeNextActionEvent();
        //todo - verify that this ActionItem is still valid, else replace it with a pause action
        a.setNextActionTime(time.getCurrentTime() +
                (long)(ai.getActionDurationInInstants() * a.getTimeMultiplier(ai.getAction()) * time.getGranularity()));
        actors.addActor(a);
        return ai;
    }

}
