package actor;

import java.io.Serializable;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * The actor list for a game level.
 * It is sorted by the timestamp on the first queued ActionItem of each actor.
 */
public class ActorExecutionQueue implements Serializable {

    transient PriorityQueue<Actor> executionQueue = new PriorityQueue<>(
            Comparator.comparingLong(Actor::getNextActionTime)
    );
    public void addActor(Actor a) {
        executionQueue.add(a);
    }
    public Actor getActor(int actorID) {
        for (Actor actor : executionQueue) {
            if (actor.getUID() == actorID) return actor;
        }
        throw new IllegalArgumentException("Actor " + actorID + "not found.");
    }
    public long nextTime() {
        return executionQueue.poll().getNextActionTime();
    }
    public ActionItem execute() {
        Actor a = executionQueue.remove();
        ActionItem ai = a.executeNextActionEvent();
        //todo - verify that this ActionItem is still valid, else replace it with a pause action
        //todo - set Actor's nextActionTime based on the final ActionItem
        executionQueue.add(a);
        return ai;
    }
}
