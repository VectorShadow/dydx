package item;

import effect.Effect;

/**
 * Base class for all non-actor entities which may appear on a level.
 * Implementations should define apply for each defined effect type.
 */
public abstract class Item {
    public abstract void apply(Effect e);
}
