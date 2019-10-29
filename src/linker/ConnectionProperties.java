package linker;

public class ConnectionProperties {
    private int port = 29387;
    private String hostname = "vps244728.vps.ovh.ca";

    public void setPort(int portNumber) {
        port = portNumber;
    }

    public int getPort() {
        return port;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getHostname() {
        return hostname;
    }
}
