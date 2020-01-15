package client;

public interface SocketListener {
    //time to wait for keyReception before declaring failure.
    long waitTolerance = 5000;
    //implementation specific connection instructions
    void connect();
    //loop within connect until either key is received or waitTolerance is reached
    //this should be done by extending Thread and calling this.start() in connect().
    void waitForServerResponse();
    //implementation specific acknowledgement instruction
    void reportAcknowledgement(int ackIndex);
    //implementation specific completion report
    boolean isSuccessfullyComplete();
}
