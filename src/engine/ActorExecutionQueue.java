package engine;

import actor.Actor;
import engine.time.Time;

import java.io.Serializable;
import java.util.PriorityQueue;

/**
 * The actor list for a game level.
 * It is sorted by the timestamp on the first queued ActionItem of each actor.
 */
public class ActorExecutionQueue implements Serializable {
    private static class ActorPriorityQueue<Comparable> extends PriorityQueue<Comparable> implements Serializable {

    }
    private final Time time;

    ActorPriorityQueue<Actor> executionQueue = new ActorPriorityQueue<>();
    public ActorExecutionQueue(Time t) {
        time = t;
    }
    public void addActor(Actor a) {
        executionQueue.add(a);
    }
    public Actor getActor(long actorID) {
        for (Actor actor : executionQueue) {
            if (actor.getUID() == actorID) return actor;
        }
        throw new IllegalArgumentException("Actor " + actorID + "not found.");
    }
    public long nextTime() {
        return executionQueue.isEmpty() ? Long.MAX_VALUE : executionQueue.peek().getNextActionTime();
    }
    public Actor poll() {
        return executionQueue.poll();
    }
}
