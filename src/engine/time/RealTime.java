package engine.time;

public class RealTime extends Time {

    @Override
    public void initialize() {
        currentTime = System.currentTimeMillis();
    }
    @Override
    public void setTime(long t) {
        int dt = (int)(t - currentTime)/granularity;
        if (dt == 1) currentTime = t;
//        else if (dt != 0)
//            throw new IllegalStateException("Illegal time shift: Attempted to update time from " + currentTime + " to " + t + ". Change was " + dt + "x granularity of " + getGranularity() + ". No more than 1 allowed per call.");
    }
    @Override
    public void setGranularity(int g) {
        if (g < Time.BASE_GRANULARITY) throw new IllegalArgumentException("Attempted to set time granularity below floor.");
        granularity = g;
    }
}
