package actor;

import graph.Coordinate;

/**
 * An action describes a specific type of change in the World State.
 */
public enum Action {
    PAUSE(3), // 1/10 second
    MOVE(16), // 1/2 second
    //todo - enum members
    ;
    //todo - details

    int baseTimeCostInInstants;
    Coordinate target;

    Action(int baseTimeCost) {
        baseTimeCostInInstants = baseTimeCost;
    }

    public int getBaseTimeCostInInstants() {
        return baseTimeCostInInstants;
    }
}
