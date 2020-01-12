package client;

public class ConnectionMonitor {
    private static ConnectionListener listener;
    private static boolean successfulConnection = false;

    public static void setListener(ConnectionListener cl) {
        listener = cl;
    }
    public static void connect(){
        listener.connect();
    }
    public static void reportKeyReceived() {
        listener.reportKeyReceived();
        successfulConnection = true;
    }
    public static boolean isSuccessfulConnection() {
        return successfulConnection;
    }
}
