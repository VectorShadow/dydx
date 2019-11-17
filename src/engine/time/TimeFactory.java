package engine.time;

public class TimeFactory {
    public static Time manufacture(boolean realtime) {
        return realtime ? new RealTime() : new TurnTime();
    }
}
