package actor;

import java.util.LinkedList;

/**
 * An Actor is any object capable of changing the WorldState.
 * It maintains a queue of ActionEvents, which the Game Engine will access to initiate changes in due order.
 * This queue is updated by the AI or player input. If it is empty, it returns a wait action.
 */
public class Actor {
    public LinkedList<ActionItem> actionItemQueue;
    long nextActionTime;

    public ActionItem checkNextActionEvent(){
        //todo - handle null case by replacing with wait action
        return actionItemQueue.pollFirst();
    }
    public ActionItem executeNextActionEvent(){
        //todo - as above
        return actionItemQueue.removeFirst();
    }
    public long getNextActionTime() {
        return nextActionTime;
    }
}
