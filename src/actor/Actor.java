package actor;

import engine.time.Time;
import gui.draw.Drawable;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * An Actor is any object capable of changing the WorldState.
 * It maintains a queue of ActionEvents, which the Game Engine will access to initiate changes in due order.
 * This queue is updated by the AI or player input. If it is empty, it returns a wait action.
 *
 * Base class for all actor entities which may appear on a level.
 */
public class Actor implements Drawable, Serializable {

    private static int serial = 0;

    private int uID;
    public LinkedList<ActionItem> actionItemQueue;
    long nextActionTime;

    public Actor(Time levelTime) {
        uID = serial++; //increment and assign a unique serial ID - used for associating across links
        actionItemQueue = new LinkedList<>();
        nextActionTime = levelTime.getCurrentTime() + levelTime.getGranularity();
    }

    public int getUID() {
        return uID;
    }

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
