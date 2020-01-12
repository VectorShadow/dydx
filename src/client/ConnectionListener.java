package client;

public interface ConnectionListener {
    //time to wait for keyReception before declaring failure.
    long waitTolerance = 5000;
    //implementation specific connection instructions
    void connect();
    //loop within connect until either key is received or waitTolerance is reached
    //this should be done by extending Thread and calling this.start() in connect().
    void waitForKeyTransmission();
    //implementation specific key reception instructions
    void reportKeyReceived();
    //implementation specific key acknowledgement instructions
    void reportKeyAcknowledged();
    //implementation specific completion report
    boolean isConnected();
}
