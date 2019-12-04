package level;

import actor.Actor;
import actor.ActorExecutionQueue;
import engine.time.RealTime;
import engine.time.Time;
import engine.time.TurnTime;

public class Level {
    Time time;
    ActorExecutionQueue actors;
    byte[][] terrainMap;
    Actor[][] actorMap; //redundant access to actors by coordinates

    public Level(boolean realtime, int rows, int cols){
        time = realtime ? new RealTime() : new TurnTime();
        actors = new ActorExecutionQueue();
        terrainMap = new byte[rows][cols];
        actorMap = new Actor[rows][cols];
    }

    public Time getTime() {
        return time;
    }

    public ActorExecutionQueue getActors() {
        return actors;
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

}
