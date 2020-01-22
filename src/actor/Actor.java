package actor;

import engine.time.Time;
import gui.draw.Drawable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * An Actor is any object capable of changing the WorldState.
 * It maintains a queue of ActionEvents, which the Game Engine will access to initiate changes in due order.
 * This queue is updated by the AI or player input. If it is empty, it returns a wait action.
 *
 * Base class for all actor entities which may appear on a level.
 */
public class Actor implements Drawable, Serializable {
    public static final int SUPER_INDICES = 0; //todo - keep updated if we add fields

    private static long serial = 0;

    private long uID = serial++;
    public LinkedList<ActionItem> actionItemQueue;
    long nextActionTime;

    public Actor(Time levelTime) {
        synchronizeTime(levelTime);
    }
    public Actor(ArrayList<String> actorFile) {
        //todo - fields, if we add them
    }

    public void synchronizeTime(Time levelTime) {
        actionItemQueue = new LinkedList<>();
        nextActionTime = levelTime.getCurrentTime() + levelTime.getGranularity();
    }

    public long getUID() {
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

    public ArrayList<String> saveAsText() {
        ArrayList<String> save = new ArrayList<>();
        //todo - fields which aren't reset on re-loading? all existing fields(uID, aIQ, nAT) need not be saved
        return save;
    }
}
