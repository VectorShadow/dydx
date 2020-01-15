package client;

public class SocketMonitor {
    private static SocketListener listener;

    public static void setListener(SocketListener cl) {
        listener = cl;
    }
    public static SocketListener getListener() {
        return listener;
    }
    public static void connect(){
        listener.connect();
    }
    public static void reportAcknowledgement(int ackIndex) {
        if (listener == null) return; //some local applications will not set a listener
        listener.reportAcknowledgement(ackIndex);
    }
    public static boolean isSuccessfullyComplete() {
        return listener.isSuccessfullyComplete();
    }
}
