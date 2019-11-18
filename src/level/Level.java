package level;

import actor.ActorExecutionQueue;
import engine.time.Time;
import level.terrain.TerrainSet;

public class Level {
    Time time;
    ActorExecutionQueue actors;
    TerrainSet terrainSet;
    byte[][] map;

    public Time getTime() {
        return time;
    }

    public ActorExecutionQueue getActors() {
        return actors;
    }
}
