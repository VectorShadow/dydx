package actor;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * The actor list for a game level.
 * It is sorted by the timestamp on the first queued ActionItem of each actor.
 */
public class ActorExecutionQueue {

    PriorityQueue<Actor> executionQueue = new PriorityQueue<>(
        new Comparator<Actor>() {
            @Override
            public int compare(Actor o1, Actor o2) {
                return Long.compare(o1.getNextActionTime(), o2.getNextActionTime());
            }
        }
    );
    public long nextTime() {
        return executionQueue.poll().getNextActionTime();
    }
}
