package actor;

import graph.Direction;

import java.io.Serializable;

/**
 * An ActionItem packages all the information required for the Game Engine to build an Event.
 * This will include an Action type, and any information required by that action type, such as targets,
 * directions, or destinations.
 */
public class ActionItem implements Serializable {
    Action action;
    long actorID;
    Direction direction;

    public ActionItem(Action a, long aid) {
        this(a, aid, Direction.SELF);
    }

    public ActionItem(Action a, long aid, Direction d) {
        action = a;
        actorID = aid;
        direction = d;
    }

    public Action getAction() {
        return action;
    }

    public long getActorID() {
        return actorID;
    }

    public Direction getDirection() {
        return direction;
    }

    public double getActionDurationInInstants() {
        return action.getBaseTimeCostInInstants();
    }
    //todo - supporting information as necessary
    @Override
    public String toString() {
        return "<ActionItem>(Action:" + action + "/ActorID:" + actorID + "/Direction:" + direction + ")";
    }
}
