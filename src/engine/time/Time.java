package engine.time;

public abstract class Time {
    public static final int BASE_GRANULARITY = 25;

    protected long currentTime = 0;

    public Time(){
        currentTime = 0;
    }
    public long getCurrentTime(){
        return currentTime;
    }
    public abstract void setTime(long t);
    public abstract void setGranularity(int g);
    public abstract int getGranularity();
}
