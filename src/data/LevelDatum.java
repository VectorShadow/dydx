package data;

import level.Level;

public class LevelDatum extends AbstractDatum {
    private final Level level;

    public LevelDatum(Level l) {
        level = l;
    }

    public Level getLevel() {
        return level;
    }
}
