package actor;

/**
 * An ActionItem packages all the information required for the Game Engine to build an Event.
 * This will include an Action type, and any information required by that action type, such as targets,
 * directions, or destinations.
 */
public class ActionItem {
    Action action;

    public Action getAction() {
        return action;
    }
    //todo - supporting information as necessary
}
