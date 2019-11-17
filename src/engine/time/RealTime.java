package engine.time;

public class RealTime extends Time {

    private long systemTime;
    private int granularity = Time.BASE_GRANULARITY;

    public RealTime() {
        super();
        systemTime = System.currentTimeMillis();
    }
    @Override
    public void setTime(long t) {
        int dt = (int)(t - systemTime)/granularity;
        if (dt == 1) systemTime += dt * granularity;
        else if (dt != 0)
            throw new IllegalStateException("Illegal time shift: " + dt + ". No more than 1 allowed per call.");
    }
    @Override
    public void setGranularity(int g) {
        if (g < Time.BASE_GRANULARITY) throw new IllegalArgumentException("Attempted to set time granularity below floor.");
        granularity = g;
    }
    @Override
    public int getGranularity(){
        return granularity;
    }
}
