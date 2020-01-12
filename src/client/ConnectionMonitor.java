package client;

public class ConnectionMonitor {
    private static ConnectionListener listener;

    public static void setListener(ConnectionListener cl) {
        listener = cl;
    }
    public static void connect(){
        listener.connect();
    }
    public static void reportKeyReceived() {
        listener.reportKeyReceived();
    }
    public  static void reportKeyAcknowledged(){
        listener.reportKeyAcknowledged();
    }
    public static boolean isSuccessfulConnection() {
        return listener.isConnected();
    }
}
