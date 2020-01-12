package client;

import linker.ConnectionProperties;
import linker.remote.ClientRemoteDataLink;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private ConnectionProperties connectionProperties = new ConnectionProperties();

    public ClientRemoteDataLink connect() {
        Socket socket;
        try {
            socket = new Socket(connectionProperties.getHostname(), connectionProperties.getPort());
        } catch (IOException e) {
            return null;
        }
        ClientRemoteDataLink crdl = new ClientRemoteDataLink(socket);
        crdl.start();
        return crdl;
    }
}
