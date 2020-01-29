package actor;

import level.Level;

public abstract class ActionResolver {
    protected abstract void apply(ActionItem ai, Level l) ;
}
