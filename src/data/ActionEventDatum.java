package data;

import actor.ActionItem;

public class ActionEventDatum extends EventDatum {

    private final ActionItem actionItem;

    public ActionEventDatum(ActionItem ai) {
        actionItem = ai;
    }

    public ActionItem getActionItem() {
        return actionItem;
    }
}
