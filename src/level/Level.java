package level;

import actor.ActorExecutionQueue;
import engine.time.Time;

public class Level {
    Time time;
    ActorExecutionQueue actors;
    byte[][] map;

    public Time getTime() {
        return time;
    }

    public ActorExecutionQueue getActors() {
        return actors;
    }
}
