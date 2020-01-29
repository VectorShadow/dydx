package actor;

import level.Level;

public class ActionResolutionManager {
    private static ActionResolver actionResolver;

    public static void setActionResolver(ActionResolver ar) {
        actionResolver = ar;
    }

    public static void resolve(ActionItem actionItem, Level level) {
        actionResolver.apply(actionItem, level);
    }
}
