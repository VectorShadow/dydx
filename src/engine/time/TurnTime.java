package engine.time;

public class TurnTime extends Time {
    @Override
    public void setTime(long t) {
        if (t <= currentTime)
            throw new IllegalArgumentException("Attempted to set time backwards(" + currentTime + "->" + t + ".");
        currentTime = t;
    }
    @Override
    public void setGranularity(int g) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getGranularity() {
        throw new UnsupportedOperationException();
    }
}
