package engine.time;

import java.io.Serializable;

public abstract class Time implements Serializable {
    public static final int BASE_GRANULARITY = 32;
    public static final int BASE_ACTION_SCALE = 16;
    protected int granularity = Time.BASE_GRANULARITY;

    protected long currentTime = 0;

    public void initialize() {
        currentTime = 0;
    }
    public long getCurrentTime(){
        return currentTime;
    }
    public int getBaseActionTime(){return granularity * BASE_ACTION_SCALE;}
    public abstract void setTime(long t);
    public abstract void setGranularity(int g);
    public int getGranularity(){return granularity;}
}
