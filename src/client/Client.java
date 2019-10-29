package client;

import error.LogReadyTraceableException;
import linker.ConnectionProperties;
import linker.remote.ClientRemoteDataLink;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private ConnectionProperties connectionProperties = new ConnectionProperties();

    public ClientRemoteDataLink connect() throws IOException, LogReadyTraceableException {
        Socket socket = new Socket(connectionProperties.getHostname(), connectionProperties.getPort());
        ClientRemoteDataLink crdl = new ClientRemoteDataLink(socket);
        crdl.start();
        return crdl;
    }
}
