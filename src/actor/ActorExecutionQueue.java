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
    public ActionItem execute() {
        Actor a = executionQueue.remove();
        ActionItem ai = a.executeNextActionEvent();
        //todo - verify that this ActionItem is still valid, else replace it with a pause action
        //todo - set Actor's nextActionTime based on the final ActionItem
        executionQueue.add(a);
        return ai;
    }
}
